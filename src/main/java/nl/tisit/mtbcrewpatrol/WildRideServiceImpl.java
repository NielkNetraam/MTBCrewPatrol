package nl.tisit.mtbcrewpatrol;

import java.awt.geom.Path2D;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.tisit.mtbcrewpatrol.model.ActivityInfoDetail;
import nl.tisit.mtbcrewpatrol.model.ActivityStatus;
import nl.tisit.mtbcrewpatrol.model.RestrictedArea;
import nl.tisit.mtbcrewpatrol.model.RideType;
import nl.tisit.mtbcrewpatrol.model.Road;
import nl.tisit.mtbcrewpatrol.model.Sector;
import nl.tisit.mtbcrewpatrol.model.Track;
import nl.tisit.mtbcrewpatrol.model.TrackType;
import nl.tisit.mtbcrewpatrol.model.WildRide;
import nl.tisit.mtbcrewpatrol.model.dm.SectorInfo;
import nl.tisit.mtbcrewpatrol.model.dm.TrackInfo;
import nl.tisit.mtbcrewpatrol.repository.ActivityStatusRepository;
import nl.tisit.mtbcrewpatrol.repository.RestrictedAreaRepository;
import nl.tisit.mtbcrewpatrol.repository.RoadRepository;
import nl.tisit.mtbcrewpatrol.repository.SectorInfoRepository;
import nl.tisit.mtbcrewpatrol.repository.SectorRepository;
import nl.tisit.mtbcrewpatrol.repository.TrackInfoRepository;
import nl.tisit.mtbcrewpatrol.repository.TrackRepository;
import nl.tisit.mtbcrewpatrol.repository.WildRideRepository;
import nl.tisit.mtbcrewpatrol.repository.WildRideSegmentRepository;
import nl.tisit.strava.model.Activity;
import nl.tisit.strava.repository.ActivityRepository;
import nl.tisit.util.Polyline;
import nl.tisit.util.PolylineDetail;

@Service
public class WildRideServiceImpl implements WildRideService {
	@Autowired
	protected WildRideActivityService wildRideActivityService;
	@Autowired
	protected RestrictedAreaRepository restrictedAreaRepository;
	@Autowired
	protected ActivityStatusRepository activityStatusRepository;
	@Autowired
	protected ActivityRepository activityRepository;
	@Autowired
	protected WildRideRepository wildRideRepository;
	@Autowired
	protected WildRideSegmentRepository wildRideSegmentRepository;
	@Autowired
	protected TrackRepository trackRepository;
	@Autowired
	protected RoadRepository roadRepository;
	@Autowired
	protected SectorRepository sectorRepository;
	@Autowired
	protected SectorInfoRepository sectorInfoRepository;
	@Autowired
	protected TrackInfoRepository trackInfoRepository;
	
	private Log log = LogFactory.getLog(StravaCrawlerServiceImpl.class);

	private void checkRestrictedArea(RestrictedArea restrictedArea) {
		Path2D.Double restrictedAreaPath2D = Polyline.polyline2path(restrictedArea.getPolyline());

		Integer count = 0;
		List<ActivityStatus> activities = activityStatusRepository.findByRestrictedArea(restrictedArea.getId());

		if (activities != null && activities.size() > 0)
			for (ActivityStatus activityStatus : activities) {
				WildRide wildride = wildRideActivityService.checkActivity(activityStatus, restrictedArea, restrictedAreaPath2D);
				if (wildride.getWild()) count++;
			}

		log.info("RestrictedArea: " + restrictedArea.getName() + " - activities checked = "+ activities.size() + ", wild = "+ count);				
	}



	@Override
	public void checkWildRides() {
		List<RestrictedArea> restrictedAreas = (List<RestrictedArea>)restrictedAreaRepository.findAll();
			
		if (restrictedAreas != null && restrictedAreas.size() > 0)
			for (RestrictedArea restrictedArea : restrictedAreas) {		
				checkRestrictedArea(restrictedArea);			
			}
	}

	@Override
	public void classifyActivities() {
		int maxDistance = 50;
		
		List<PolylineDetail> trackPolylineDetailList = new ArrayList<PolylineDetail>();
		List<Track> tracks = (List<Track>)trackRepository.findAll(); 

		for (Track track : tracks)
			if (track.getTrackType() != TrackType.CONNECTION) {
				trackPolylineDetailList.add(new PolylineDetail(track.getPolyline(), maxDistance, false, null, null));
			}

		List<PolylineDetail> roadPolylineDetailList = new ArrayList<PolylineDetail>();
		for (Road road : roadRepository.findAll()) 
			roadPolylineDetailList.add(new PolylineDetail(road.getPolyline(), maxDistance, false, road.getStartDate(), road.getEndDate()));

		List<RestrictedArea> restrictedAreas = (List<RestrictedArea>)restrictedAreaRepository.findAll();

		for (ActivityStatus activityStatus : activityStatusRepository.findByRideTypeAndInfoIsNull(RideType.UNKNOWN)) 
			wildRideActivityService.classifyActivity(activityStatus, trackPolylineDetailList, roadPolylineDetailList, restrictedAreas, tracks, maxDistance);
	}
	
	@Override
	@Transactional
	public LocalDate aggregateActivity() {
		LocalDate aggregatedTill = LocalDate.of(2016, Month.JANUARY, 1);
		
		sectorInfoRepository.deleteAll();
		trackInfoRepository.deleteAll();
		
		Map<Integer, SectorInfo> sectorInfoMap = new HashMap<Integer, SectorInfo>();
		for (Sector sector : sectorRepository.findAll()) {
			sectorInfoMap.put(sector.getId(), new SectorInfo(sector));
		}
		
		Map<Integer, TrackInfo> trackInfoMap = new HashMap<Integer, TrackInfo>();
		for (Track track : trackRepository.findAll()) {
			trackInfoMap.put(track.getId(), new TrackInfo(track));
		}
		
		log.debug("aantal:"+activityStatusRepository.findByRideType(RideType.MTB).size());
		
//		int i = 1;
		for (ActivityStatus activityStatus : activityStatusRepository.findByRideType(RideType.MTB)) {
			Activity activity = activityRepository.findOne(activityStatus.getId());
	
			for (Map.Entry<Integer, ActivityInfoDetail> ad : activityStatus.getInfo().getSectorDetail().entrySet()) {
				sectorInfoMap.get(ad.getKey()).addActivity(activity, ad.getValue());
			}
			for (Map.Entry<Integer, ActivityInfoDetail> ad : activityStatus.getInfo().getTrackDetail().entrySet()) {
				trackInfoMap.get(ad.getKey()).addActivity(activity, ad.getValue());
			}
			
			if (activity.getStartDateLocal().toLocalDate().isAfter(aggregatedTill)) aggregatedTill = activity.getStartDateLocal().toLocalDate();
		}
		
		for (SectorInfo sectorInfo : sectorInfoMap.values()) {
			log.debug("sectorInfo: " + sectorInfo);
			sectorInfo.persist();
		}
		
		sectorInfoRepository.save(sectorInfoMap.values());

		for (TrackInfo trackInfo : trackInfoMap.values()) {
			log.debug("trackInfo: " + trackInfo);
			trackInfo.persist();
		}
			
		trackInfoRepository.save(trackInfoMap.values());
		
		return aggregatedTill;
	}

}
