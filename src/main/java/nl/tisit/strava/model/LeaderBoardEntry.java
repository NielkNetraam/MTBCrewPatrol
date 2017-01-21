package nl.tisit.strava.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaderBoardEntry {
	@JsonProperty("athlete_name")
	private String athleteName;
	@JsonProperty("athlete_id")
	private Integer athleteId;
	@JsonProperty("athlete_gender")
	private Gender athleteGender;

	@JsonProperty("average_hr")
	private Float averageHeartrate; 
	@JsonProperty("average_watts")
	private Float averageWatts;
	private Float distance;
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

	@JsonProperty("activity_id")
	private Integer activityId;
	@JsonProperty("effort_id")
	private Long effortId;
	private Integer rank;
	@JsonProperty("athlete_profile")
	private String athleteProfile;
	
	public void setZoned2Local() {
		startDateLocal = startDateLocalApi.toLocalDateTime();
	}
	
	public String getAthleteName() {
		return athleteName;
	}
	public void setAthleteName(String athleteName) {
		this.athleteName = athleteName;
	}
	public Integer getAthleteId() {
		return athleteId;
	}
	public void setAthleteId(Integer athleteId) {
		this.athleteId = athleteId;
	}
	public Gender getAthleteGender() {
		return athleteGender;
	}
	public void setAthleteGender(Gender athleteGender) {
		this.athleteGender = athleteGender;
	}
	public Float getAverageHeartrate() {
		return averageHeartrate;
	}
	public void setAverageHeartrate(Float averageHeartrate) {
		this.averageHeartrate = averageHeartrate;
	}
	public Float getAverageWatts() {
		return averageWatts;
	}
	public void setAverageWatts(Float averageWatts) {
		this.averageWatts = averageWatts;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
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
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Long getEffortId() {
		return effortId;
	}
	public void setEffortId(Long effortId) {
		this.effortId = effortId;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getAthleteProfile() {
		return athleteProfile;
	}
	public void setAthleteProfile(String athleteProfile) {
		this.athleteProfile = athleteProfile;
	}
	@Override
	public String toString() {
		return "LeaderBoardEntry [athleteName=" + athleteName + ", athleteId=" + athleteId + ", athleteGender="
				+ athleteGender + ", averageHeartrate=" + averageHeartrate + ", averageWatts=" + averageWatts
				+ ", distance=" + distance + ", elapsedTime=" + elapsedTime + ", movingTime=" + movingTime
				+ ", startDate=" + startDate + ", startDateLocal=" + startDateLocal + ", activityId=" + activityId
				+ ", effortId=" + effortId + ", rank=" + rank + ", athleteProfile=" + athleteProfile + "]";
	}
}
