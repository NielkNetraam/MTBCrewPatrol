package nl.tisit.keyregister.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "holidays")
public class Holidays {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Type(type ="org.hibernate.type.LocalDateType")
	private LocalDate start;
	@Type(type ="org.hibernate.type.LocalDateType")
	private LocalDate end;
	private Region region;
	private String description;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDate getStart() {
		return start;
	}
	public void setStart(LocalDate start) {
		this.start = start;
	}
	public LocalDate getEnd() {
		return end;
	}
	public void setEnd(LocalDate end) {
		this.end = end;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Holidays [id=" + id + ", start=" + start + ", end=" + end + ", description=" + description + "]";
	}
}
