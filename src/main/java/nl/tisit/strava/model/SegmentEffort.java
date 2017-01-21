package nl.tisit.strava.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "segment_efforts")
public class SegmentEffort {
	@Id
	private Long id;
	
	@JsonProperty("resource_state")
	private Integer resourceState;
	
	private String name;
	
	@ManyToOne
	private Activity activity; 
	
	@ManyToOne
	private Athlete athlete;
	
	@JsonProperty("elapsed_time")
	private Integer elapsedTime; 
	@JsonProperty("moving_time")
	private Integer movingTime; 
	@JsonProperty("start_date")
	private ZonedDateTime startDate;
	@JsonProperty("start_date_local")
	@Transient
	private ZonedDateTime startDateLocalApi;

	@JsonIgnore
	@Type(type = "org.hibernate.type.LocalDateTimeType")
	private LocalDateTime startDateLocal;
	private Float distance; 
	@JsonProperty("start_index")
	private Integer startIndex; 
	@JsonProperty("end_index")
	private Integer endIndex; 
	@JsonProperty("average_cadence")
	private Float averageCadence; 
	@JsonProperty("average_watts")
	private Float averageWatts; 
	@JsonProperty("device_watts")
	private Boolean deviceWatts; 
	@JsonProperty("average_heartrate")
	private Integer averageHeartrate; 
	@JsonProperty("max_heartrate")
	private Integer maxHeartrate; 

	@ManyToOne
	private Segment segment;
	
	@JsonProperty("kom_rank")
	private Integer komRrank; 
	@JsonProperty("pr_rank")
	private Integer prRank; 
	private Boolean hidden;
	
	public void setZoned2Local() {
		startDateLocal = startDateLocalApi.toLocalDateTime();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getResourceState() {
		return resourceState;
	}
	public void setResourceState(Integer resourceState) {
		this.resourceState = resourceState;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public Athlete getAthlete() {
		return athlete;
	}
	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}
	public Integer getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public Integer getMovingTime() {
		return movingTime;
	}
	public void setMovingTime(Integer movingTime) {
		this.movingTime = movingTime;
	}
	public ZonedDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getStartDateLocal() {
		return startDateLocal;
	}
	public void setStartDateLocal(LocalDateTime startDateLocal) {
		this.startDateLocal = startDateLocal;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}
	public Float getAverageCadence() {
		return averageCadence;
	}
	public void setAverageCadence(Float averageCadence) {
		this.averageCadence = averageCadence;
	}
	public Float getAverageWatts() {
		return averageWatts;
	}
	public void setAverageWatts(Float averageWatts) {
		this.averageWatts = averageWatts;
	}
	public Boolean getDeviceWatts() {
		return deviceWatts;
	}
	public void setDeviceWatts(Boolean deviceWatts) {
		this.deviceWatts = deviceWatts;
	}
	public Integer getAverageHeartrate() {
		return averageHeartrate;
	}
	public void setAverageHeartrate(Integer averageHeartrate) {
		this.averageHeartrate = averageHeartrate;
	}
	public Integer getMaxHeartrate() {
		return maxHeartrate;
	}
	public void setMaxHeartrate(Integer maxHeartrate) {
		this.maxHeartrate = maxHeartrate;
	}
	public Segment getSegment() {
		return segment;
	}
	public void setSegment(Segment segment) {
		this.segment = segment;
	}
	public Integer getKomRrank() {
		return komRrank;
	}
	public void setKomRrank(Integer komRrank) {
		this.komRrank = komRrank;
	}
	public Integer getPrRank() {
		return prRank;
	}
	public void setPrRank(Integer prRank) {
		this.prRank = prRank;
	}
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	@Override
	public String toString() {
		return "SegmentEfforts [id=" + id + ", resourceState=" + resourceState + ", name=" + name + ", activity="
				+ activity + ", athlete=" + athlete + ", elapsedTime=" + elapsedTime + ", movingTime=" + movingTime
				+ ", startDate=" + startDate + ", startDateLocal=" + startDateLocal + ", distance=" + distance
				+ ", startIndex=" + startIndex + ", endIndex=" + endIndex + ", averageCadence=" + averageCadence
				+ ", averageWatts=" + averageWatts + ", deviceWatts=" + deviceWatts + ", averageHeartrate="
				+ averageHeartrate + ", maxHeartrate=" + maxHeartrate + ", segment=" + segment + ", komRrank="
				+ komRrank + ", prRank=" + prRank + ", hidden=" + hidden + "]";
	}
}
