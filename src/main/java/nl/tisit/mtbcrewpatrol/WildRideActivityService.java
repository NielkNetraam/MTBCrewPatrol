package nl.tisit.mtbcrewpatrol;

import java.awt.geom.Path2D;
import java.util.List;

import nl.tisit.mtbcrewpatrol.model.ActivityStatus;
import nl.tisit.mtbcrewpatrol.model.RestrictedArea;
import nl.tisit.mtbcrewpatrol.model.Track;
import nl.tisit.mtbcrewpatrol.model.WildRide;
import nl.tisit.util.PolylineDetail;

public interface WildRideActivityService {
	public WildRide checkActivity(ActivityStatus activityStatus, RestrictedArea restrictedArea);
	public WildRide checkActivity(ActivityStatus activityStatus, RestrictedArea restrictedArea, Path2D.Double restrictedAreaPath2D);
	public void classifyActivity(ActivityStatus activityStatus, List<PolylineDetail> trackPolylineDetailList, List<PolylineDetail> roadPolylineDetailList, List<RestrictedArea> restrictedAreas, List<Track> tracks, Integer maxDistance);
}
