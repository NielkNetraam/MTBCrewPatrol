package nl.tisit.mtbcrewpatrol.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "restricted_area_exception")
public class RestrictedAreaException {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private RestrictedArea restrictedArea;

	@Type(type = "org.hibernate.type.LocalDateTimeType")
	private LocalDateTime startDate;
	@Type(type = "org.hibernate.type.LocalDateTimeType")
	private LocalDateTime endDate;

	private String reason;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RestrictedArea getRestrictedArea() {
		return restrictedArea;
	}

	public void setRestrictedArea(RestrictedArea restrictedArea) {
		this.restrictedArea = restrictedArea;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "RestrictedAreaException [id=" + id + ", restrictedArea=" + restrictedArea + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", reason=" + reason + "]";
	}

}
