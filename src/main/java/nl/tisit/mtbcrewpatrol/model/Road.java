package nl.tisit.mtbcrewpatrol.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "road")
public class Road {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;   
	private String name;
	@Lob
	private String polyline;
	@Type(type ="org.hibernate.type.LocalDateType")
	private LocalDate startDate;
	@Type(type ="org.hibernate.type.LocalDateType")
	private LocalDate endDate;
		
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPolyline() {
		return polyline;
	}

	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Road [id=" + id + ", name=" + name + ", polyline=" + polyline + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}


}
