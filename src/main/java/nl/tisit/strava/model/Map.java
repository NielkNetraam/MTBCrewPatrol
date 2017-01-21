package nl.tisit.strava.model;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable 
public class Map {
	private String id;
	@JsonProperty("resource_state")
	private Integer resourceState;
	@Lob
	private String polyline;
	@JsonProperty("summary_polyline")
	@Lob
	private String summaryPolyline;
	
	public Map() {
		
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getResourceState() {
		return resourceState;
	}
	public void setResourceState(Integer resourceState) {
		this.resourceState = resourceState;
	}
	public String getPolyline() {
		return polyline;
	}
	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}
	public String getSummaryPolyline() {
		return summaryPolyline;
	}
	public void setSummaryPolyline(String summaryPolyline) {
		this.summaryPolyline = summaryPolyline;
	}
	@Override
	public String toString() {
		return "Map [id=" + id + ", resourceState=" + resourceState + ", polyline=" + polyline + ", summaryPolyline="
				+ summaryPolyline + "]";
	}
	
	
}
