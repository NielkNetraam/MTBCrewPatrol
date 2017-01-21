package nl.tisit.strava.model;

import java.time.ZonedDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "segment")
public class Segment {
	@Id
	private Integer id;
	@JsonProperty("resource_state")
	private Integer resourceState;
	private String name;
	@JsonProperty("activity_type")
	private ActivityType activityType;
	private Float distance;
	@JsonProperty("average_grade")
	private Float averageGrade;
	@JsonProperty("maximum_grade")
	private Float maximumGrade;
	@JsonProperty("elevation_high")
	private Float elevationHigh;
	@JsonProperty("elevation_low")
	private Float elevationLow;
	
	@JsonProperty("start_latlng")
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="lat", column=@Column(name="start_lat")),
	    @AttributeOverride(name="lng", column=@Column(name="start_lng"))
	  })
	private LatLng startLatlng;
	
	@JsonProperty("end_latlng")
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="lat", column=@Column(name="end_lat")),
	    @AttributeOverride(name="lng", column=@Column(name="end_lng"))
	  })
	private LatLng endLatlng;
	
	@JsonProperty("climb_category")
	private Integer climbCategory;
	private String city;
	private String state;
	private String country;
	@JsonProperty("private")
	private Boolean privateSegemnt;
	private Boolean starred;
	@JsonProperty("created_at")
	ZonedDateTime createdAt;
	@JsonProperty("updated_at")
	ZonedDateTime updatedAt;
	@JsonProperty("total_elevation_gain")
	private Float totalElevationGain;

	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="id", column=@Column(name="map_id")),
	    @AttributeOverride(name="resourceState", column=@Column(name="map_resource_state")),
	    @AttributeOverride(name="polyline", column=@Column(name="map_polyline")),
	    @AttributeOverride(name="summaryPolyline", column=@Column(name="map_summary_polyline"))
	  })
	private Map map;

	@JsonProperty("effort_count")
	private Integer effortCount;
	@JsonProperty("athlete_count")
	private Integer athleteCount;
	private Boolean hazardous;
	@JsonProperty("star_count")
	private Integer starCount;
	
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
	public ActivityType getActivityType() {
		return activityType;
	}
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}
	public Float getAverageGrade() {
		return averageGrade;
	}
	public void setAverageGrade(Float averageGrade) {
		this.averageGrade = averageGrade;
	}
	public Float getMaximumGrade() {
		return maximumGrade;
	}
	public void setMaximumGrade(Float maximumGrade) {
		this.maximumGrade = maximumGrade;
	}
	public Float getElevationHigh() {
		return elevationHigh;
	}
	public void setElevationHigh(Float elevationHigh) {
		this.elevationHigh = elevationHigh;
	}
	public Float getElevationLow() {
		return elevationLow;
	}
	public void setElevationLow(Float elevationLow) {
		this.elevationLow = elevationLow;
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
	public Integer getClimbCategory() {
		return climbCategory;
	}
	public void setClimbCategory(Integer climbCategory) {
		this.climbCategory = climbCategory;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Boolean getPrivateSegemnt() {
		return privateSegemnt;
	}
	public void setPrivateSegemnt(Boolean privateSegemnt) {
		this.privateSegemnt = privateSegemnt;
	}
	public Boolean getStarred() {
		return starred;
	}
	public void setStarred(Boolean starred) {
		this.starred = starred;
	}
	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public ZonedDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(ZonedDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Float getTotalElevationGain() {
		return totalElevationGain;
	}
	public void setTotalElevationGain(Float totalElevationGain) {
		this.totalElevationGain = totalElevationGain;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public Integer getEffortCount() {
		return effortCount;
	}
	public void setEffortCount(Integer effortCount) {
		this.effortCount = effortCount;
	}
	public Integer getAthleteCount() {
		return athleteCount;
	}
	public void setAthleteCount(Integer athleteCount) {
		this.athleteCount = athleteCount;
	}
	public Boolean getHazardous() {
		return hazardous;
	}
	public void setHazardous(Boolean hazardous) {
		this.hazardous = hazardous;
	}
	public Integer getStarCount() {
		return starCount;
	}
	public void setStarCount(Integer starCount) {
		this.starCount = starCount;
	}
	
	@Override
	public String toString() {
		return "Segment [id=" + id + ", resourceState=" + resourceState + ", name=" + name + ", activityType="
				+ activityType + ", distance=" + distance + ", averageGrade=" + averageGrade + ", maximumGrade="
				+ maximumGrade + ", elevationHigh=" + elevationHigh + ", elevationLow=" + elevationLow
				+ ", startLatlng=" + startLatlng + ", endLatlng=" + endLatlng + ", climbCategory=" + climbCategory
				+ ", city=" + city + ", state=" + state + ", country=" + country + ", privateSegemnt=" + privateSegemnt
				+ ", starred=" + starred + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", totalElevationGain=" + totalElevationGain + ", map=" + map + ", effortCount=" + effortCount
				+ ", athleteCount=" + athleteCount + ", hazardous=" + hazardous + ", starCount=" + starCount + "]";
	}	
}
