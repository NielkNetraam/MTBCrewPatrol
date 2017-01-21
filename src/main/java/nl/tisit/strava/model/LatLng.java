package nl.tisit.strava.model;

import java.util.List;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable 
public class LatLng {
	private Float lat;
	private Float lng;
	  
	public LatLng(Float lat, Float lng) {
	    this.lat = lat;
	    this.lng = lng;  
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}
	
	@JsonCreator 
	public LatLng(List<Float> points) {
		this.lat = points.get(0);
		this.lng = points.get(1);		
	}
	
	public LatLng() {
	}
	
	@Override
	public String toString() {
		return "LatLng [lat=" + lat + ", lng=" + lng + "]";
	}
}
