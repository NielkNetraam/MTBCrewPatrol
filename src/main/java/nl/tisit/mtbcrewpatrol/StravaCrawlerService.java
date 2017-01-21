package nl.tisit.mtbcrewpatrol;

import java.awt.geom.Path2D;

import nl.tisit.mtbcrewpatrol.model.CallCounter;
import nl.tisit.mtbcrewpatrol.model.CrawlStatus;

public interface StravaCrawlerService {
	public CrawlStatus collectMyActivities();
	public CrawlStatus collectDetailedActivities();
	public CrawlStatus collectEffortsFromSegment(Integer segmentId, CallCounter pages);
	public CrawlStatus collectEffortsFromSegments(Integer maxCount, Path2D.Double path);
	public CrawlStatus collectSummaryActivities();
	public CrawlStatus collectSummaryAthletes();
	public CrawlStatus collectSegments(Path2D.Double path);
	
	public Integer collectAthletesFromSegment(Integer segmentId);
	public Integer collectEffortsFromActivity(Integer activityId);
//	public Integer collectActivitiesFromAthlete(Integer athleteId);
}
