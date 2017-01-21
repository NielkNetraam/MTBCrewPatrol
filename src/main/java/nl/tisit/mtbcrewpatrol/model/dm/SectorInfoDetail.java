package nl.tisit.mtbcrewpatrol.model.dm;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import nl.tisit.mtbcrewpatrol.model.ActivityInfoDetail;
import nl.tisit.mtbcrewpatrol.model.Sector;
import nl.tisit.mtbcrewpatrol.model.TrackSector;
import nl.tisit.strava.model.Activity;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SectorInfoDetail   {
	@JsonIgnore
	private Log log = LogFactory.getLog(SectorInfoDetail.class);
	
	@JsonIgnore
	private Sector sector;
	
	private Integer activityCount;
	private Integer effortCount;
	private Integer athleteCount;
	
	private Map<Integer, Integer> roundCount;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH")
	private Map<LocalDateTime, Integer> hourCount;
	
	private Map<Integer, Integer> activityEfforts;

	@JsonIgnore
	private Map<Integer, Integer> athleteEfforts;
	
	public SectorInfoDetail() {
		super();
	}

	public SectorInfoDetail(Sector sector) {
		super();
		this.sector = sector;
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
//			Integer h = dt.getHour();
			LocalDateTime d = dt.truncatedTo(ChronoUnit.HOURS);
			Integer h = d.getHour();

			if ((activity.getId() == 657744473 || activity.getId() == 716087424 || activity.getId()== 665714863) && (h < 5 || h > 22)) log.debug("A:"+ activity.getId() + ", hour:"+ h+ ", dt:" +dt+ ", d:" +d);

			Integer count = hourCount.get(d);
			hourCount.put(d, (count == null ? 1 : count+1));
		}
			
		Integer count = roundCount.get(activityInfoDetail.getCount());
		roundCount.put(activityInfoDetail.getCount(), (count == null ? 1 : count+1));				

		count = athleteEfforts.get(activity.getAthlete().getId());
		athleteEfforts.put(activity.getAthlete().getId(), (count == null ? 1 : count+activityInfoDetail.getCount()));
		
		athleteCount = athleteEfforts.size();

		for(TrackSector ts : sector.getTracks()) 
			if (activityInfoDetail.getCount() >= ts.getTrack().getEffortThreshold()) {
				activityEfforts.put(activity.getId(), activityInfoDetail.getCount());
				break;
			}	
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
