package nl.tisit.mtbcrewpatrol;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.mtbcrewpatrol.model.ActivityStatus;
import nl.tisit.mtbcrewpatrol.model.RestrictedArea;
import nl.tisit.mtbcrewpatrol.model.Road;
import nl.tisit.mtbcrewpatrol.model.Track;
import nl.tisit.mtbcrewpatrol.model.TrackType;
import nl.tisit.mtbcrewpatrol.repository.ActivityStatusRepository;
import nl.tisit.mtbcrewpatrol.repository.RestrictedAreaRepository;
import nl.tisit.mtbcrewpatrol.repository.RoadRepository;
import nl.tisit.mtbcrewpatrol.repository.TrackRepository;
import nl.tisit.util.PolylineDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class WildRideActivityServiceTests {
	@Autowired
	protected WildRideActivityService wildRideActivityService;
	@Autowired
	protected TrackRepository trackRepository;
	@Autowired
	protected ActivityStatusRepository activityStatusRepository;
	@Autowired
	protected  RestrictedAreaRepository restrictedAreaRepository;
	@Autowired
	protected RoadRepository roadRepository;

	@Test
//	@Transactional
	public void shouldClassifyActivity() {
		assertThat(this.activityStatusRepository).isNotNull();
		assertThat(this.trackRepository).isNotNull();
		assertThat(this.roadRepository).isNotNull();
		assertThat(this.wildRideActivityService).isNotNull();

		int maxDistance = 50;

		List<PolylineDetail> trackPolylineDetailList = new ArrayList<PolylineDetail>();
		List<Track> tracks = (List<Track>)trackRepository.findAll(); //new ArrayList<Sector>();

		for (Track track : tracks)
			if (track.getTrackType() != TrackType.CONNECTION) {
				trackPolylineDetailList.add(new PolylineDetail(track.getPolyline(), maxDistance, false, null, null));
			}

		List<PolylineDetail> roadPolylineDetailList = new ArrayList<PolylineDetail>();
		for (Road road : roadRepository.findAll()) 
			roadPolylineDetailList.add(new PolylineDetail(road.getPolyline(), maxDistance, false, road.getStartDate(), road.getEndDate()));

		List<RestrictedArea> restrictedAreas = (List<RestrictedArea>)restrictedAreaRepository.findAll();
//		List<PolylineDetail> restrictedAreaPolylineDetailList = new ArrayList<PolylineDetail>();
//		for (RestrictedArea restrictedArea : restrictedAreaRepository.findAll()) {	
//			restrictedAreaPolylineDetailList.add(new PolylineDetail(restrictedArea.getPolyline(), 0, true));
//		}

		ActivityStatus activityStatus = activityStatusRepository.findOne(716087424);//465690551

		this.wildRideActivityService.classifyActivity(activityStatus, trackPolylineDetailList, roadPolylineDetailList, restrictedAreas, tracks, 50);
	}

}