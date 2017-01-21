package nl.tisit.mtbcrewpatrol.model;

import java.awt.geom.Line2D;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.maps.model.LatLng;

@Entity
@Table(name = "restricted_line")
public class RestrictedLine {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  
    
	@ManyToOne
	private RestrictedArea restrictedArea;

	@Column(name="start_lat")
    private double startLat;
	@Column(name="start_lng")
    private double startLng;
	@Column(name="end_lat")
    private double endLat;
	@Column(name="end_lng")
    private double endLng;
  
	@Transient
	private Line2D.Double line;
	
	public Line2D.Double getLine() {
		if (line == null) line = new Line2D.Double(startLng, startLat, endLng, endLat);
		return line;
	}
	
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
	
	public boolean intersects(LatLng from, LatLng to) {
		return (getLine().intersectsLine(from.lng, from.lat, to.lng, to.lat));
	}

	public double getStartLat() {
		return startLat;
	}

	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}

	public double getStartLng() {
		return startLng;
	}

	public void setStartLng(double startLng) {
		this.startLng = startLng;
	}

	public double getEndLat() {
		return endLat;
	}

	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}

	public double getEndLng() {
		return endLng;
	}

	public void setEndLng(double endLng) {
		this.endLng = endLng;
	}

	@Override
	public String toString() {
		return "RestrictedLine [id=" + id + ", restrictedArea=" + restrictedArea + ", startLat=" + startLat
				+ ", startLng=" + startLng + ", endLat=" + endLat + ", endLng=" + endLng + "]";
	}
	
	
	
}
