package nl.tisit.keyregister.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "holiday")
public class Holiday {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Type(type ="org.hibernate.type.LocalDateType")
	private LocalDate holiday;
	private String description;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public LocalDate getHoliday() {
		return holiday;
	}
	
	public void setHoliday(LocalDate holiday) {
		this.holiday = holiday;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Holiday [id=" + id + ", holiday=" + holiday + ", description=" + description + "]";
	}
}
