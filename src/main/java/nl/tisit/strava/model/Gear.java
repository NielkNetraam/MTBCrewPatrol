package nl.tisit.strava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "gear")
public class Gear {
	@Id
	private String id;
	@Column(name = "primary_gear")
	private Boolean primary;
	private String name;
	private Float distance; 
	@JsonProperty("brand_name")
	private String brandName;
	@JsonProperty("model_name")
	private String modelName;
	@JsonProperty("frame_type")
	private FrameType frameType;
	private String description;
	@JsonProperty("resource_state")
	private Integer resourceState;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getPrimary() {
		return primary;
	}
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public FrameType getFrameType() {
		return frameType;
	}
	public void setFrameType(FrameType frameType) {
		this.frameType = frameType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getResourceState() {
		return resourceState;
	}
	public void setResourceState(Integer resourceState) {
		this.resourceState = resourceState;
	}
	
	@Override
	public String toString() {
		return "Gear [id=" + id + ", primary=" + primary + ", name=" + name + ", distance=" + distance + ", brandName="
				+ brandName + ", modelName=" + modelName + ", frameType=" + frameType + ", description=" + description
				+ ", resourceState=" + resourceState + "]";
	}
}
