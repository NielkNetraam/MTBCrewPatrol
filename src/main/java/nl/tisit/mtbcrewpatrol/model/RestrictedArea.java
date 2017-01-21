package nl.tisit.mtbcrewpatrol.model;

import java.awt.geom.Path2D;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.google.maps.model.LatLng;

import nl.tisit.util.Polyline;

@Entity
@Table(name = "restricted_area")
public class RestrictedArea {
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
	
	@OneToMany(mappedBy="restrictedArea")
	private List<WildRide> wildRides;
	
	@OneToMany(mappedBy="restrictedArea", fetch=FetchType.EAGER)
	private List<RestrictedAreaException> restrictedAreaExceptions;
	
	@OneToMany(mappedBy="restrictedArea", fetch=FetchType.EAGER)
	private List<RestrictedLine> restrictedLines;

	private CheckType checkType = CheckType.DEFAULT;

	@Transient
	private Path2D.Double path;
	
	public Path2D.Double getPath() {
		if (path == null) path = Polyline.polyline2path(polyline);
		return path;
	}
	
	public static RestrictedArea inAreas(LatLng latLng, List<RestrictedArea> restrictedAreas, LocalDateTime referenceDate) {
		for (RestrictedArea restrictedArea : restrictedAreas) {
			if (restrictedArea.inArea(latLng, referenceDate))
				return restrictedArea;
		}

		return null;
	}
	
	public boolean intersects(LatLng from, LatLng to) {
		if (checkType.equals(CheckType.LINE)) {
			for (RestrictedLine rl : restrictedLines) 
				if (rl.intersects(from, to)) return true;
		}
		
		return false;
	}
	
	public boolean inArea(LatLng latLng, LocalDateTime referenceDate) {
		if (path == null) path = Polyline.polyline2path(polyline);
		
		if (referenceDate.isBefore(this.startDate.atStartOfDay()) || (this.endDate != null && referenceDate.isAfter(this.endDate.atStartOfDay().plusDays(1))))
			return false;

		if (this.restrictedAreaExceptions != null)
			for (RestrictedAreaException rae : this.restrictedAreaExceptions) {
				if (!referenceDate.isBefore(rae.getStartDate())
						&& (rae.getEndDate() == null || !referenceDate.isAfter(rae.getEndDate())))
					return false;
			}
		
		return path.contains(latLng.lng, latLng.lat);	
	}
	
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
	
	public List<WildRide> getWildRides() {
		return wildRides;
	}
	public void setWildRides(List<WildRide> wildRides) {
		this.wildRides = wildRides;
	}

	public List<RestrictedAreaException> getRestrictedAreaExceptions() {
		return restrictedAreaExceptions;
	}
	
	public void setRestrictedAreaExceptions(List<RestrictedAreaException> restrictedAreaExceptions) {
		this.restrictedAreaExceptions = restrictedAreaExceptions;
	}
	
	public List<RestrictedLine> getRestrictedLines() {
		return restrictedLines;
	}
	
	public void setRestrictedLines(List<RestrictedLine> restrictedLines) {
		this.restrictedLines = restrictedLines;
	}
	
	public CheckType getCheckType() {
		return checkType;
	}

	public void setCheckType(CheckType checkType) {
		this.checkType = checkType;
	}

	@Override
	public String toString() {
		return "RestrictedArea [id=" + id + ", name=" + name + ", polyline=" + polyline + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", checkType=" + checkType + "]";
	}
}
