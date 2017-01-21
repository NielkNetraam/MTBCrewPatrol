package nl.tisit.mtbcrewpatrol.model;

import java.awt.geom.Line2D;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.google.maps.model.LatLng;

@Entity
@Table(name = "sector")
public class Sector {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  
    
	@OneToMany(mappedBy = "sector")
	private Set<TrackSector> tracks;

	private String name;

	@Column(name="start_p1_lat")
    private double startP1Lat;
	@Column(name="start_p1_lng")
    private double startP1Lng;
	@Column(name="start_p2_lat")
    private double startP2Lat;
	@Column(name="start_p2_lng")
    private double startP2Lng;
	@Column(name="end_p1_lat")
    private double endP1Lat;
	@Column(name="end_p1_lng")
    private double endP1Lng;
	@Column(name="end_p2_lat")
    private double endP2Lat;
	@Column(name="end_p2_lng")
    private double endP2Lng;

	@Type(type ="org.hibernate.type.LocalDateType")
	@Column(name="start_date")
	private LocalDate startDate;
	@Type(type ="org.hibernate.type.LocalDateType")
	@Column(name="end_date")
	private LocalDate endDate;

	private Boolean alwaysMTB;
	private Integer direction_lat;
	private Integer direction_lng;
	
	@OneToMany(mappedBy="sector", fetch=FetchType.EAGER)
	private List<SectorException> sectorExceptions;

	@Transient
	private Line2D.Double startLine;
	
	@Transient
	private Line2D.Double endLine;

	public Line2D.Double getStartLine() {
		if (startLine == null) startLine = new Line2D.Double(startP1Lng, startP1Lat, startP2Lng, startP2Lat);
		return startLine;
	}
	
	public Line2D.Double getEndLine() {
		if (endLine == null) endLine = new Line2D.Double(endP1Lng, endP1Lat, endP2Lng, endP2Lat);
		return endLine;
	}
		
	public boolean intersectsStart(LatLng from, LatLng to) {
		Integer ft_direction_lat = (from.lat > to.lat ? -1 : 1);
		Integer ft_direction_lng = (from.lng > to.lng ? -1 : 1);
		
		if (direction_lat != null && (direction_lat + ft_direction_lat) == 0 ) return false;
		if (direction_lng != null && (direction_lng + ft_direction_lng) == 0 ) return false;

		return (getStartLine().intersectsLine(from.lng, from.lat, to.lng, to.lat));
	}

	public static Sector intersectsStartList(LatLng from, LatLng to, Set<TrackSector> sectors) {
		for (TrackSector trackSector : sectors) {
			if (trackSector.getSector().intersectsStart(from, to)) 
				return trackSector.getSector();
			
		}

		return null;
	}

	public boolean intersectsEnd(LatLng from, LatLng to) {
		return (getEndLine().intersectsLine(from.lng, from.lat, to.lng, to.lat));
	}

	public boolean exception(LocalDate date) {
		for (SectorException se : this.sectorExceptions) {
			if (!date.isBefore(se.getStartDate()) && !date.isAfter(se.getEndDate()))
				return true;
		}
		
		return false;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Set<TrackSector> getTracks() {
		return tracks;
	}

	public void setTracks(Set<TrackSector> tracks) {
		this.tracks = tracks;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getStartP1Lat() {
		return startP1Lat;
	}


	public void setStartP1Lat(double startP1Lat) {
		this.startP1Lat = startP1Lat;
	}


	public double getStartP1Lng() {
		return startP1Lng;
	}


	public void setStartP1Lng(double startP1Lng) {
		this.startP1Lng = startP1Lng;
	}


	public double getStartP2Lat() {
		return startP2Lat;
	}


	public void setStartP2Lat(double startP2Lat) {
		this.startP2Lat = startP2Lat;
	}


	public double getStartP2Lng() {
		return startP2Lng;
	}


	public void setStartP2Lng(double startP2Lng) {
		this.startP2Lng = startP2Lng;
	}


	public double getEndP1Lat() {
		return endP1Lat;
	}


	public void setEndP1Lat(double endP1Lat) {
		this.endP1Lat = endP1Lat;
	}


	public double getEndP1Lng() {
		return endP1Lng;
	}


	public void setEndP1Lng(double endP1Lng) {
		this.endP1Lng = endP1Lng;
	}


	public double getEndP2Lat() {
		return endP2Lat;
	}


	public void setEndP2Lat(double endP2Lat) {
		this.endP2Lat = endP2Lat;
	}


	public double getEndP2Lng() {
		return endP2Lng;
	}

	public void setEndP2Lng(double endP2Lng) {
		this.endP2Lng = endP2Lng;
	}

	public Boolean getAlwaysMTB() {
		return alwaysMTB;
	}

	public void setAlwaysMTB(Boolean alwaysMTB) {
		this.alwaysMTB = alwaysMTB;
	}

	public Integer getDirection_lat() {
		return direction_lat;
	}

	public void setDirection_lat(Integer direction_lat) {
		this.direction_lat = direction_lat;
	}

	public Integer getDirection_lng() {
		return direction_lng;
	}

	public void setDirection_lng(Integer direction_lng) {
		this.direction_lng = direction_lng;
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

	public List<SectorException> getSectorExceptions() {
		return sectorExceptions;
	}

	public void setSectorExceptions(List<SectorException> sectorExceptions) {
		this.sectorExceptions = sectorExceptions;
	}

	@Override
	public String toString() {
		return "Sector [id=" + id + ", name=" + name + ", startP1Lat=" + startP1Lat
				+ ", startP1Lng=" + startP1Lng + ", startP2Lat=" + startP2Lat + ", startP2Lng=" + startP2Lng
				+ ", endP1Lat=" + endP1Lat + ", endP1Lng=" + endP1Lng + ", endP2Lat=" + endP2Lat + ", endP2Lng="
				+ endP2Lng + ", alwaysMTB=" + alwaysMTB + ", direction_lat=" + direction_lat + ", direction_lng=" + direction_lng + "]";
	}
	
}
