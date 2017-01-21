package nl.tisit.strava.service;

import java.time.LocalDateTime;
import java.util.List;

import nl.tisit.mtbcrewpatrol.model.CallCounterException;
import nl.tisit.strava.model.Activity;
import nl.tisit.strava.model.Athlete;
import nl.tisit.strava.model.DateRange;
import nl.tisit.strava.model.LatLng;
import nl.tisit.strava.model.LeaderBoard;
import nl.tisit.strava.model.Segment;
import nl.tisit.strava.model.SegmentEffort;

public interface StravaService {
	public Athlete findAthlete(Integer id) throws CallCounterException;
	public Activity findActivity(Integer id) throws CallCounterException;
	public Segment findSegment(Integer segmentId) throws CallCounterException;
	public LeaderBoard findSegmentLeaderBoard(Integer segmentId) throws CallCounterException;
	public LeaderBoard findSegmentLeaderBoard(Integer segmentId, DateRange dateRange) throws CallCounterException;
	public List<SegmentEffort> findSegmentEfforts(Integer segmentId, LocalDateTime startDate, Integer perPage, Integer page) throws CallCounterException;
	public List<Segment> findSegments(LatLng sw, LatLng ne) throws CallCounterException;
	public List<Activity> findAthleteActivities(LocalDateTime startDate, Integer perPage) throws CallCounterException;
}
