package nl.tisit.strava.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "activity")
public class Activity {
	@Id
	private Integer id;
	@JsonProperty("resource_state")
	private Integer resourceState;
	@JsonProperty("external_id")
	private String externalId;
	@JsonProperty("upload_id")
	private Integer uploadId;

	@ManyToOne
	private Athlete athlete;

	private String name;
	private String description;
	private Float distance;
	@JsonProperty("moving_time")
	private Integer movingTime;
	@JsonProperty("elapsed_time")
	private Integer elapsedTime;
	@JsonProperty("total_elevation_gain")
	private Float totalElevationGain;
	@JsonProperty("elev_high")
	private Float elevHigh;
	@JsonProperty("elev_low")
	private Float elevLow;
	@JsonProperty("type")
	private String activityType;
	@JsonProperty("start_date")
	@Type(type = "org.hibernate.type.ZonedDateTimeType")
	private ZonedDateTime startDate;

	@JsonProperty("start_date_local")
	@Transient
	private ZonedDateTime startDateLocalApi;

	@JsonIgnore
	@Type(type = "org.hibernate.type.LocalDateTimeType")
	private LocalDateTime startDateLocal;

	private String timezone;

	@JsonProperty("start_latlng")
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "lat", column = @Column(name = "start_lat")),
			@AttributeOverride(name = "lng", column = @Column(name = "start_lng")) })
	private LatLng startLatlng;

	@JsonProperty("end_latlng")
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "lat", column = @Column(name = "end_lat")),
			@AttributeOverride(name = "lng", column = @Column(name = "end_lng")) })
	private LatLng endLatlng;

	@JsonProperty("achievement_count")
	private Integer achievementCount;
	@JsonProperty("kudos_count")
	private Integer kudos_count;
	@JsonProperty("comment_count")
	private Integer commentCount;
	@JsonProperty("athlete_count")
	private Integer athleteCount;
	@JsonProperty("photo_count")
	private Integer photoCount;
	@JsonProperty("total_photo_count")
	private Integer totalPhotoCount;
	/* photos: object photos summary */

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "map_id")),
			@AttributeOverride(name = "resourceState", column = @Column(name = "map_resource_state")),
			@AttributeOverride(name = "polyline", column = @Column(name = "map_polyline")),
			@AttributeOverride(name = "summaryPolyline", column = @Column(name = "map_summary_polyline")) })
	private Map map;

	private Boolean trainer;
	private Boolean commute;
	private Boolean manual;
	@JsonProperty("private")
	private Boolean privateActivity;
	@JsonProperty("device_name")
	private String deviceName;
	@JsonProperty("embed_token")
	private String embedToken;
	private Boolean flagged;
	@JsonProperty("workout_type")
	private WorkOutType workoutType;

	@JsonProperty("gear_id")
	@Column(name = "athlete_gear_id")
	private String gearId;

	@ManyToOne
	private Gear gear;

	@JsonProperty("average_speed")
	private Float averageSpeed;
	@JsonProperty("max_speed")
	private Float maxSpeed;
	@JsonProperty("average_cadence")
	private Float averageCadence;
	@JsonProperty("average_temp")
	private Float averageTemp;
	@JsonProperty("average_watts")
	private Float averageWatts;
	@JsonProperty("max_watts")
	private Integer maxWatts;
	@JsonProperty("weighted_average_watts")
	private Integer weightedAverageWatts;
	private Float kilojoules;
	@JsonProperty("device_watts")
	private Boolean deviceWatts;
	@JsonProperty("has_heartrate")
	private Boolean hasHeartrate;
	@JsonProperty("average_heartrate")
	private Float averageHeartrate;
	@JsonProperty("max_heartrate")
	private Integer maxHeartrate;
	private Float calories;
	@JsonProperty("suffer_score")
	private Integer sufferScore;
	@JsonProperty("has_kudoed")
	private Boolean has_kudoed;

	@OneToMany(mappedBy = "activity")
	@JsonProperty("segment_efforts")
	private List<SegmentEffort> segmentEfforts;

	/*
	 * @JsonProperty("splits_metric") splits_metric: array of metric split
	 * summaries
	 * 
	 * @JsonProperty("splits_standard") splits_standard: array of standard split
	 * summaries running activities only
	 * 
	 * @JsonProperty("best_efforts") best_efforts: array of best effort
	 * summaries running activities only
	 */

	/* Local date in API is a Zoned Data, results in false local date */ 
	public void setZoned2Local() {
		startDateLocal = startDateLocalApi.toLocalDateTime();
	}
	
	public Athlete getAthlete() {
		return athlete;
	}

	public List<SegmentEffort> getSegmentEfforts() {
		return segmentEfforts;
	}

	public void setSegmentEfforts(List<SegmentEffort> segmentEfforts) {
		this.segmentEfforts = segmentEfforts;
	}

	public WorkOutType getWorkoutType() {
		return workoutType;
	}

	public void setWorkoutType(WorkOutType workoutType) {
		this.workoutType = workoutType;
	}

	public LatLng getStartLatlng() {
		return startLatlng;
	}

	public void setStartLatlng(LatLng startLatlng) {
		this.startLatlng = startLatlng;
	}

	public LatLng getEndLatlng() {
		return endLatlng;
	}

	public void setEndLatlng(LatLng endLatlng) {
		this.endLatlng = endLatlng;
	}

	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Integer getUploadId() {
		return uploadId;
	}

	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public Integer getMovingTime() {
		return movingTime;
	}

	public void setMovingTime(Integer movingTime) {
		this.movingTime = movingTime;
	}

	public Integer getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public Float getTotalElevationGain() {
		return totalElevationGain;
	}

	public void setTotalElevationGain(Float totalElevationGain) {
		this.totalElevationGain = totalElevationGain;
	}

	public Float getElevHigh() {
		return elevHigh;
	}

	public void setElevHigh(Float elevHigh) {
		this.elevHigh = elevHigh;
	}

	public Float getElevLow() {
		return elevLow;
	}

	public void setElevLow(Float elevLow) {
		this.elevLow = elevLow;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
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

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Integer getAchievementCount() {
		return achievementCount;
	}

	public void setAchievementCount(Integer achievementCount) {
		this.achievementCount = achievementCount;
	}

	public Integer getKudos_count() {
		return kudos_count;
	}

	public void setKudos_count(Integer kudos_count) {
		this.kudos_count = kudos_count;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getAthleteCount() {
		return athleteCount;
	}

	public void setAthleteCount(Integer athleteCount) {
		this.athleteCount = athleteCount;
	}

	public Integer getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(Integer photoCount) {
		this.photoCount = photoCount;
	}

	public Integer getTotalPhotoCount() {
		return totalPhotoCount;
	}

	public void setTotalPhotoCount(Integer totalPhotoCount) {
		this.totalPhotoCount = totalPhotoCount;
	}

	public Boolean getTrainer() {
		return trainer;
	}

	public void setTrainer(Boolean trainer) {
		this.trainer = trainer;
	}

	public Boolean getCommute() {
		return commute;
	}

	public void setCommute(Boolean commute) {
		this.commute = commute;
	}

	public Boolean getManual() {
		return manual;
	}

	public void setManual(Boolean manual) {
		this.manual = manual;
	}

	public Boolean getPrivateActivity() {
		return privateActivity;
	}

	public void setPrivateActivity(Boolean privateActivity) {
		this.privateActivity = privateActivity;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getEmbedToken() {
		return embedToken;
	}

	public void setEmbedToken(String embedToken) {
		this.embedToken = embedToken;
	}

	public Boolean getFlagged() {
		return flagged;
	}

	public void setFlagged(Boolean flagged) {
		this.flagged = flagged;
	}

	public String getGearId() {
		return gearId;
	}

	public void setGearId(String gearId) {
		this.gearId = gearId;
	}

	public Gear getGear() {
		return gear;
	}

	public void setGear(Gear gear) {
		this.gear = gear;
	}

	public Float getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(Float averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public Float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Float getAverageCadence() {
		return averageCadence;
	}

	public void setAverageCadence(Float averageCadence) {
		this.averageCadence = averageCadence;
	}

	public Float getAverageTemp() {
		return averageTemp;
	}

	public void setAverageTemp(Float averageTemp) {
		this.averageTemp = averageTemp;
	}

	public Float getAverageWatts() {
		return averageWatts;
	}

	public void setAverageWatts(Float averageWatts) {
		this.averageWatts = averageWatts;
	}

	public Integer getMaxWatts() {
		return maxWatts;
	}

	public void setMaxWatts(Integer maxWatts) {
		this.maxWatts = maxWatts;
	}

	public Integer getWeightedAverageWatts() {
		return weightedAverageWatts;
	}

	public void setWeightedAverageWatts(Integer weightedAverageWatts) {
		this.weightedAverageWatts = weightedAverageWatts;
	}

	public Float getKilojoules() {
		return kilojoules;
	}

	public void setKilojoules(Float kilojoules) {
		this.kilojoules = kilojoules;
	}

	public Boolean getDeviceWatts() {
		return deviceWatts;
	}

	public void setDeviceWatts(Boolean deviceWatts) {
		this.deviceWatts = deviceWatts;
	}

	public Boolean getHasHeartrate() {
		return hasHeartrate;
	}

	public void setHasHeartrate(Boolean hasHeartrate) {
		this.hasHeartrate = hasHeartrate;
	}

	public Float getAverageHeartrate() {
		return averageHeartrate;
	}

	public void setAverageHeartrate(Float averageHeartrate) {
		this.averageHeartrate = averageHeartrate;
	}

	public Integer getMaxHeartrate() {
		return maxHeartrate;
	}

	public void setMaxHeartrate(Integer maxHeartrate) {
		this.maxHeartrate = maxHeartrate;
	}

	public Float getCalories() {
		return calories;
	}

	public void setCalories(Float calories) {
		this.calories = calories;
	}

	public Integer getSufferScore() {
		return sufferScore;
	}

	public void setSufferScore(Integer sufferScore) {
		this.sufferScore = sufferScore;
	}

	public Boolean getHas_kudoed() {
		return has_kudoed;
	}

	public void setHas_kudoed(Boolean has_kudoed) {
		this.has_kudoed = has_kudoed;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", resourceState=" + resourceState + ", externalId=" + externalId + ", uploadId="
				+ uploadId + ", name=" + name + ", description=" + description + ", distance=" + distance
				+ ", movingTime=" + movingTime + ", elapsedTime=" + elapsedTime + ", totalElevationGain="
				+ totalElevationGain + ", elevHigh=" + elevHigh + ", elevLow=" + elevLow + ", activityType="
				+ activityType + ", startDate=" + startDate + ", startDateLocal=" + startDateLocal + ", timezone="
				+ timezone + ", achievementCount=" + achievementCount + ", kudos_count=" + kudos_count
				+ ", commentCount=" + commentCount + ", athleteCount=" + athleteCount + ", photoCount=" + photoCount
				+ ", totalPhotoCount=" + totalPhotoCount + ", map=" + map + ", trainer=" + trainer + ", commute="
				+ commute + ", manual=" + manual + ", privateActivity=" + privateActivity + ", deviceName=" + deviceName
				+ ", embedToken=" + embedToken + ", flagged=" + flagged + ", gearId=" + gearId + ", gear=" + gear
				+ ", averageSpeed=" + averageSpeed + ", maxSpeed=" + maxSpeed + ", averageCadence=" + averageCadence
				+ ", averageTemp=" + averageTemp + ", averageWatts=" + averageWatts + ", maxWatts=" + maxWatts
				+ ", weightedAverageWatts=" + weightedAverageWatts + ", kilojoules=" + kilojoules + ", deviceWatts="
				+ deviceWatts + ", hasHeartrate=" + hasHeartrate + ", averageHeartrate=" + averageHeartrate
				+ ", maxHeartrate=" + maxHeartrate + ", calories=" + calories + ", sufferScore=" + sufferScore
				+ ", has_kudoed=" + has_kudoed + "]";
	}

}
