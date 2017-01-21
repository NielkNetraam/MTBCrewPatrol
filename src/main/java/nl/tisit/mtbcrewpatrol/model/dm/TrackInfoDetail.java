package nl.tisit.mtbcrewpatrol.model.dm;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import nl.tisit.mtbcrewpatrol.model.ActivityInfoDetail;
import nl.tisit.mtbcrewpatrol.model.Track;
import nl.tisit.strava.model.Activity;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class TrackInfoDetail   {
	@JsonIgnore
	private Track track;
	
	private Integer activityCount;
	private Integer effortCount;
	private Integer athleteCount;
	
	private Map<Integer, Integer> roundCount;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH")
	private Map<LocalDateTime, Integer> hourCount;

	private Map<Integer, Integer> activityEfforts;

	@JsonIgnore
	private Map<Integer, Integer> athleteEfforts;
	

	public TrackInfoDetail(Track track) {
		super();
		this.track = track;
		activityCount = 0;
		effortCount = 0;
		athleteCount = 0;
		hourCount = new HashMap<LocalDateTime, Integer>();
		roundCount = new HashMap<Integer, Integer>();
		athleteEfforts = new HashMap<Integer, Integer>();
		activityEfforts = new HashMap<Integer, Integer>();
	}	
	
	public void addActivity(Activity activity, ActivityInfoDetail activityInfoDetail) {
		activityCount++;
		effortCount += activityInfoDetail.getCount();
		
		for (LocalDateTime dt : activityInfoDetail.getStarts()) {
//			Date d = Date.from(dt.atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.HOURS).toInstant());
//			Date d = Date.from(ZonedDateTime.ofInstant(dt.toInstant(), ZoneId.systemDefault()).truncatedTo(ChronoUnit.HOURS).toInstant());
			LocalDateTime d = dt.truncatedTo(ChronoUnit.HOURS);
			Integer count = hourCount.get(d);
			hourCount.put(d, (count == null ? 1 : count+1));
		}
			
		Integer count = roundCount.get(activityInfoDetail.getCount());
		roundCount.put(activityInfoDetail.getCount(), (count == null ? 1 : count+1));				

		count = athleteEfforts.get(activity.getAthlete().getId());
		athleteEfforts.put(activity.getAthlete().getId(), (count == null ? 1 : count+activityInfoDetail.getCount()));
		
		athleteCount = athleteEfforts.size();
		
		if (activityInfoDetail.getCount() >= track.getEffortThreshold())
			activityEfforts.put(activity.getId(), activityInfoDetail.getCount());
			
}

	public Integer getActivityCount() {
		return activityCount;
	}

	public Map<LocalDateTime, Integer> getHourCount() {
		return hourCount;
	}

	public Integer getEffortCount() {
		return effortCount;
	}

	public Integer getAthleteCount() {
		return athleteCount;
	}

	public Map<Integer, Integer> getRoundCount() {
		return roundCount;
	}

	public Map<Integer, Integer> getActivityEfforts() {
		return activityEfforts;
	}

}
