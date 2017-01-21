package nl.tisit.mtbcrewpatrol.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "segment_effort_status")
public class SegmentEffortStatus {
    @Id
    protected Long id;
	@Type(type ="org.hibernate.type.ZonedDateTimeType")
    protected LocalDateTime lastChecked;
    protected ResourceStatus status;
	private Integer athleteId;
	private Integer activityId;
	private Integer segmentId;

	public SegmentEffortStatus() {	
	}
	
	public SegmentEffortStatus(Long id, Integer athleteId, Integer activityId, Integer segmentId) {
		this.id = id;
		this.athleteId = athleteId;
		this.activityId = activityId;
		this.segmentId = segmentId;
		this.status = ResourceStatus.UNKNOWN;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getLastChecked() {
		return lastChecked;
	}

	public void setLastChecked(LocalDateTime lastChecked) {
		this.lastChecked = lastChecked;
	}

	public ResourceStatus getStatus() {
		return status;
	}

	public void setStatus(ResourceStatus status) {
		this.status = status;
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

	public Integer getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(Integer segmentId) {
		this.segmentId = segmentId;
	}
		
}
