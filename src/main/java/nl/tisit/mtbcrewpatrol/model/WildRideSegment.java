package nl.tisit.mtbcrewpatrol.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;

import nl.tisit.util.Geometry;

@Entity
@Table(name = "wildride_segment")
public class WildRideSegment {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
	@ManyToOne
	private WildRide wildride;
	
	@JsonProperty("polyline")
	@Lob
	private String polyline;
	private Integer points;
	private Float distance;
	
	@Transient
	private List<LatLng> latLngs;

	public WildRideSegment() {
		super();
	}

	public WildRideSegment(WildRide wildRide) {
		super();
		this.wildride = wildRide;
		this.points = 0;
		this.distance = new Float(0);
	}

	public void setPolylineAndDistanceFromLatLngs() {
		if (latLngs != null && latLngs.size() > 0) {
			this.polyline = PolylineEncoding.encode(latLngs);
			this.distance = new Float(Geometry.distance(latLngs));
		} else {
			this.polyline = null;
			this.distance = new Float(0);
		}
	}
	
	public void addLatLng(LatLng latLng, boolean point) {
		if (latLngs == null) 
			latLngs = new ArrayList<LatLng>();
		
		latLngs.add(latLng);
		
		if (point) points++;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WildRide getWildride() {
		return wildride;
	}

	public void setWildride(WildRide wildride) {
		this.wildride = wildride;
	}

	public String getPolyline() {
		return polyline;
	}

	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "WildRideSegment [id=" + id + ", wildride=" + wildride.getId() + ", polyline=" + polyline + ", points=" + points
				+ ", distance=" + distance + ", latLngs=" + latLngs + "]";
	}

	
}
