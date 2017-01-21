package nl.tisit.weather.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDay {
	private LocalDate date;
	private LocalDateTime sunrise;
	private LocalDateTime sunset;
	private List<ForecastHour> hours;
	private Float maxtemp;
    private Float mintemp;
    private Float mintemperature;
    private Float maxtemperature;
	private Float temperature;
	private Float feeltemperature;
	private Integer windspeed;
	private Integer beaufort;
    private String winddirection;
    private String iconcode;
    private Integer iconid;
    private Float precipitationmm;
    private Float precipationmm;
    private Integer precipation;
    private Integer precipitation;

    public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalDateTime getSunrise() {
		return sunrise;
	}
	public void setSunrise(LocalDateTime sunrise) {
		this.sunrise = sunrise;
	}
	public LocalDateTime getSunset() {
		return sunset;
	}
	public void setSunset(LocalDateTime sunset) {
		this.sunset = sunset;
	}
	public List<ForecastHour> getHours() {
		return hours;
	}
	public void setHours(List<ForecastHour> hours) {
		this.hours = hours;
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
	public Float getMintemperature() {
		return mintemperature;
	}
	public void setMintemperature(Float mintemperature) {
		this.mintemperature = mintemperature;
	}
	public Float getMaxtemperature() {
		return maxtemperature;
	}
	public void setMaxtemperature(Float maxtemperature) {
		this.maxtemperature = maxtemperature;
	}
	public Float getTemperature() {
		return temperature;
	}
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	public Float getFeeltemperature() {
		return feeltemperature;
	}
	public void setFeeltemperature(Float feeltemperature) {
		this.feeltemperature = feeltemperature;
	}
	public Integer getWindspeed() {
		return windspeed;
	}
	public void setWindspeed(Integer windspeed) {
		this.windspeed = windspeed;
	}
	public Integer getBeaufort() {
		return beaufort;
	}
	public void setBeaufort(Integer beaufort) {
		this.beaufort = beaufort;
	}
	public String getWinddirection() {
		return winddirection;
	}
	public void setWinddirection(String winddirection) {
		this.winddirection = winddirection;
	}
	public String getIconcode() {
		return iconcode;
	}
	public void setIconcode(String iconcode) {
		this.iconcode = iconcode;
	}
	public Integer getIconid() {
		return iconid;
	}
	public void setIconid(Integer iconid) {
		this.iconid = iconid;
	}
	public Float getPrecipitationmm() {
		return precipitationmm;
	}
	public void setPrecipitationmm(Float precipitationmm) {
		this.precipitationmm = precipitationmm;
	}
	public Float getPrecipationmm() {
		return precipationmm;
	}
	public void setPrecipationmm(Float precipationmm) {
		this.precipationmm = precipationmm;
	}
	public Integer getPrecipation() {
		return precipation;
	}
	public void setPrecipation(Integer precipation) {
		this.precipation = precipation;
	}
	public Integer getPrecipitation() {
		return precipitation;
	}
	public void setPrecipitation(Integer precipitation) {
		this.precipitation = precipitation;
	}
	@Override
	public String toString() {
		return "ForecastDay [date=" + date + ", sunrise=" + sunrise + ", sunset=" + sunset + ", hours=" + hours
				+ ", maxtemp=" + maxtemp + ", mintemp=" + mintemp + ", mintemperature=" + mintemperature
				+ ", maxtemperature=" + maxtemperature + ", temperature=" + temperature + ", feeltemperature="
				+ feeltemperature + ", windspeed=" + windspeed + ", beaufort=" + beaufort + ", winddirection="
				+ winddirection + ", iconcode=" + iconcode + ", iconid=" + iconid + ", precipitationmm="
				+ precipitationmm + ", precipationmm=" + precipationmm + ", precipation=" + precipation
				+ ", precipitation=" + precipitation + "]";
	}

}
