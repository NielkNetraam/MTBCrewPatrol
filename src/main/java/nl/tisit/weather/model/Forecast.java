package nl.tisit.weather.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
	Float timeOffset;
//	private Location location;
	private Integer altitude;
	private Integer elevation;
	private List<ForecastDay> days;
	private Date timestamp;
	private String machinename;
	private Float maxtemp;
	private Float mintemp;

	public Float getTimeOffset() {
		return timeOffset;
	}

	public void setTimeOffset(Float timeOffset) {
		this.timeOffset = timeOffset;
	}

//	public LatLng getLocation() {
//		return location;
//	}
//
//	public void setLocation(LatLng location) {
//		this.location = location;
//	}

	public Integer getAltitude() {
		return altitude;
	}

	public void setAltitude(Integer altitude) {
		this.altitude = altitude;
	}

	public Integer getElevation() {
		return elevation;
	}

	public void setElevation(Integer elevation) {
		this.elevation = elevation;
	}

	public List<ForecastDay> getDays() {
		return days;
	}

	public void setDays(List<ForecastDay> days) {
		this.days = days;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMachinename() {
		return machinename;
	}

	public void setMachinename(String machinename) {
		this.machinename = machinename;
	}

	public Float getMaxtemp() {
		return maxtemp;
	}

	public void setMaxtemp(Float maxtemp) {
		this.maxtemp = maxtemp;
	}

	public Float getMintemp() {
		return mintemp;
	}

	public void setMintemp(Float mintemp) {
		this.mintemp = mintemp;
	}

	@Override
	public String toString() {
		return "Forecast [timeOffset=" + timeOffset + ", location=" + /*location*/ null + ", altitude=" + altitude
				+ ", elevation=" + elevation + ", days=" + days + ", timestamp=" + timestamp + ", machinename="
				+ machinename + ", maxtemp=" + maxtemp + ", mintemp=" + mintemp + "]";
	}
}
