package nl.tisit.mtbcrewpatrol.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import nl.tisit.strava.model.Activity;

@Entity
@Table(name = "wildride")
public class WildRide {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	@Type(type ="org.hibernate.type.LocalDateTimeType")
	private LocalDateTime lastChecked;  
	private Integer athleteId;
	private Integer activityId;

	@ManyToOne
	private RestrictedArea restrictedArea;

	private Boolean wild;
	private LocalDateTime activityDate;
	private Integer count;
	private Integer pointCount;
	private Float distance;

	@OneToMany(mappedBy="wildride")
	private List<WildRideSegment> segments;

	public WildRide() {
		super();
	}

	public WildRide(RestrictedArea restrictedArea, Activity activity) {
		super();
		this.activityId = activity.getId();
		this.lastChecked = LocalDateTime.now();
		this.restrictedArea = restrictedArea;
		this.activityDate = activity.getStartDateLocal();
		this.athleteId = activity.getAthlete().getId();
		this.wild = false;
		this.count = 0;
		this.pointCount = 0;
		this.distance = new Float(0);
		this.segments = new ArrayList<WildRideSegment>();
	}

	public void reset() {
		this.lastChecked = LocalDateTime.now();
		this.wild = false;
		this.count = 0;
		this.pointCount = 0;
		this.distance = new Float(0);
		this.segments = new ArrayList<WildRideSegment>();
	}
	
	public void addSegment(WildRideSegment segment) {
		segments.add(segment);
		wild = true;
		count++;
		pointCount+=segment.getPoints();
		distance+=segment.getDistance();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public LocalDateTime getLastChecked() {
		return lastChecked;
	}
	
	public void setLastChecked(LocalDateTime lastChecked) {
		this.lastChecked = lastChecked;
	}
	
	public Integer getAthleteId() {
		return athleteId;
	}
	
	public void setAthleteId(Integer athleteId) {
		this.athleteId = athleteId;
	}
	
	public Integer getActivityId() {
		return activityId;
	}
	
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
	public Boolean getWild() {
		return wild;
	}
	
	public void setWild(Boolean wild) {
		this.wild = wild;
	}
	
	public LocalDateTime getActivityDate() {
		return activityDate;
	}
	
	public void setActivityDate(LocalDateTime activityDate) {
		this.activityDate = activityDate;
	}
	
	
	public RestrictedArea getRestrictedArea() {
		return restrictedArea;
	}

	public void setRestrictedArea(RestrictedArea restrictedArea) {
		this.restrictedArea = restrictedArea;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPointCount() {
		return pointCount;
	}

	public void setPointCount(Integer pointCount) {
		this.pointCount = pointCount;
	}

	public List<WildRideSegment> getSegments() {
		return segments;
	}
	
	public void setSegments(List<WildRideSegment> segments) {
		this.segments = segments;
	}
	

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "WildRide [id=" + id + ", lastChecked=" + lastChecked + ", athleteId=" + athleteId + ", activityId="
				+ activityId + ", restrictedArea=" + restrictedArea + ", wild=" + wild + ", activityDate="
				+ activityDate + ", count=" + count + ", pointCount=" + pointCount + "]";
	}
	
	
}
