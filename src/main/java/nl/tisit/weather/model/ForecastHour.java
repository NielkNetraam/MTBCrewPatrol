package nl.tisit.weather.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastHour {
	private LocalDateTime datetime;
	private Integer hour;
	private Float temperature;
	private Float feeltemperature;
	private Integer windspeed;
	private Integer beaufort;
    private String winddirection;
    private Integer humidity;
    private String iconcode;
    private Integer iconid;
    private Float precipitationmm;
    private Float precipationmm;
    private Integer precipation;
    private Integer precipitation;
    private Integer sunshinepower;
    private Integer sunshine;
    private Integer sunpower;
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
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
	public Integer getHumidity() {
		return humidity;
	}
	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
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
	public Integer getSunshinepower() {
		return sunshinepower;
	}
	public void setSunshinepower(Integer sunshinepower) {
		this.sunshinepower = sunshinepower;
	}
	public Integer getSunshine() {
		return sunshine;
	}
	public void setSunshine(Integer sunshine) {
		this.sunshine = sunshine;
	}
	public Integer getSunpower() {
		return sunpower;
	}
	public void setSunpower(Integer sunpower) {
		this.sunpower = sunpower;
	}
	@Override
	public String toString() {
		return "ForecastHour [datetime=" + datetime + ", hour=" + hour + ", temperature=" + temperature
				+ ", feeltemperature=" + feeltemperature + ", windspeed=" + windspeed + ", beaufort=" + beaufort
				+ ", winddirection=" + winddirection + ", humidity=" + humidity + ", iconcode=" + iconcode + ", iconid="
				+ iconid + ", precipitationmm=" + precipitationmm + ", precipationmm=" + precipationmm
				+ ", precipation=" + precipation + ", precipitation=" + precipitation + ", sunshinepower="
				+ sunshinepower + ", sunshine=" + sunshine + ", sunpower=" + sunpower + "]";
	}
	
	
}
