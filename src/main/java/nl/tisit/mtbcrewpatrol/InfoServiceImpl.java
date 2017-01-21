package nl.tisit.mtbcrewpatrol;

import java.awt.geom.Path2D;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.LatLng;

import nl.tisit.mtbcrewpatrol.model.RestrictedArea;
import nl.tisit.mtbcrewpatrol.model.SegmentStatus;
import nl.tisit.mtbcrewpatrol.model.SegmentType;
import nl.tisit.mtbcrewpatrol.model.Track;
import nl.tisit.mtbcrewpatrol.model.TrackType;
import nl.tisit.mtbcrewpatrol.model.WildType;
import nl.tisit.mtbcrewpatrol.repository.ActivityStatusRepository;
import nl.tisit.mtbcrewpatrol.repository.RestrictedAreaRepository;
import nl.tisit.mtbcrewpatrol.repository.SegmentStatusRepository;
import nl.tisit.mtbcrewpatrol.repository.TrackRepository;
import nl.tisit.mtbcrewpatrol.repository.WildRideRepository;
import nl.tisit.strava.model.Segment;
import nl.tisit.strava.repository.ActivityRepository;
import nl.tisit.strava.repository.SegmentRepository;
import nl.tisit.util.Geometry;
import nl.tisit.util.Polyline;

@Service
public class InfoServiceImpl implements InfoService {
	@Autowired
	protected SegmentRepository segmentRepository;
	@Autowired
	protected SegmentStatusRepository segmentStatusRepository;
	@Autowired
	protected TrackRepository trackRepository;
	@Autowired
	protected RestrictedAreaRepository restrictedAreaRepository;
	@Autowired
	protected ActivityStatusRepository activityStatusRepository;
	@Autowired
	protected ActivityRepository activityRepository;
	@Autowired
	protected  WildRideRepository wildRideRepository;

	private Log log = LogFactory.getLog(StravaCrawlerServiceImpl.class);

	@Override
	public void segmentOnTrack(int maxDistance) {
		for (SegmentStatus segmentStatus : segmentStatusRepository
				.findBySegmentTypeAndLastCheckedNotNull(SegmentType.UNKNOWN)) {
			Segment segment = segmentRepository.findOne(segmentStatus.getId());

			LatLng startLatLng = new LatLng(segment.getStartLatlng().getLat(), segment.getStartLatlng().getLng());
			LatLng endLatLng = new LatLng(segment.getEndLatlng().getLat(), segment.getEndLatlng().getLng());

			for (Track track : trackRepository.findAll()) {
				Boolean startOnTrack = false;
				Boolean endOnTrack = false;
				List<LatLng> trackLatLngs = Polyline.polyline2latLngsInterpolate(track.getPolyline(), maxDistance);

				for (LatLng latLng : trackLatLngs) {
					if (!startOnTrack && Geometry.distance(startLatLng, latLng) < maxDistance) {
						startOnTrack = true;
					}
					if (!endOnTrack && Geometry.distance(endLatLng, latLng) < maxDistance) {
						endOnTrack = true;
					}

					if (startOnTrack && endOnTrack) {
						if (track.getTrackType() == TrackType.CONNECTION) {
							segmentStatus.setSegmentType(SegmentType.RACE);
							segmentStatus.setWild(WildType.NOT_WILD);
						} else {
							segmentStatus.setSegmentType(SegmentType.MTB);
							segmentStatus.setTrack(track);
							segmentStatus.setWild(WildType.NOT_WILD);
						}
						segmentStatusRepository.save(segmentStatus);
						break;
					}
				}

				if (startOnTrack && endOnTrack) {
					log.debug(segment.getId().toString() + ": " + segment.getName() + " - " + track.getId() + ": "
							+ track.getName());
					break;
				}
			}
		}
	}

	@Override
	public void segmentWild() {
		for (SegmentStatus segmentStatus : segmentStatusRepository
				.findBySegmentTypeAndLastCheckedNotNull(SegmentType.UNKNOWN)) {
			Segment segment = segmentRepository.findOne(segmentStatus.getId());

			Boolean wild = false;

			LatLng startLatLng = new LatLng(segment.getStartLatlng().getLat(), segment.getStartLatlng().getLng());
			LatLng endLatLng = new LatLng(segment.getEndLatlng().getLat(), segment.getEndLatlng().getLng());

			for (RestrictedArea restrictedArea : restrictedAreaRepository.findAll()) {
				Path2D.Double restrictedAreaPath2D = Polyline.polyline2path(restrictedArea.getPolyline());

				if (restrictedAreaPath2D.contains(startLatLng.lng, startLatLng.lat)) {
					log.debug(segment.getId().toString() + ": " + segment.getName() + " - " + restrictedArea.getId()
							+ ": " + restrictedArea.getName());
					wild = true;
					break;
				}
				if (restrictedAreaPath2D.contains(endLatLng.lng, endLatLng.lat)) {
					log.debug(segment.getId().toString() + ": " + segment.getName() + " - " + restrictedArea.getId()
							+ ": " + restrictedArea.getName());
					wild = true;
					break;
				}
			}

			if (wild) {
				segmentStatus.setSegmentType(SegmentType.MTB);
				segmentStatus.setWild(WildType.WILD);
				segmentStatusRepository.save(segmentStatus);
			}
		}
	}

	@Override
	public void segmentNotOnTrack(int maxDistance) {
		// Segment segment = segmentRepository.findOne(11340148);
		for (SegmentStatus segmentStatus : segmentStatusRepository
				.findBySegmentTypeAndLastCheckedNotNull(SegmentType.UNKNOWN)) {
			Segment segment = segmentRepository.findOne(segmentStatus.getId());

			LatLng startLatLng = new LatLng(segment.getStartLatlng().getLat(), segment.getStartLatlng().getLng());
			LatLng endLatLng = new LatLng(segment.getEndLatlng().getLat(), segment.getEndLatlng().getLng());

			Boolean startOnTrack = false;
			Boolean endOnTrack = false;

			for (Track track : trackRepository.findAll()) {
				List<LatLng> trackLatLngs = Polyline.polyline2latLngsInterpolate(track.getPolyline(), maxDistance);

				for (LatLng latLng : trackLatLngs) {
					if (!startOnTrack && Geometry.distance(startLatLng, latLng) < maxDistance) {
						startOnTrack = true;
					}
					if (!endOnTrack && Geometry.distance(endLatLng, latLng) < maxDistance) {
						endOnTrack = true;
					}

					if (startOnTrack || endOnTrack) {
						break;
					}
				}

				if (startOnTrack || endOnTrack) {
					break;
				}
			}

			if (!(startOnTrack || endOnTrack)) {
				segmentStatus.setSegmentType(SegmentType.RACE);
				segmentStatus.setWild(WildType.NOT_WILD);
				segmentStatusRepository.save(segmentStatus);
			}
		}
	}
	
}
