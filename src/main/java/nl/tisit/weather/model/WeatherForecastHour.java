package nl.tisit.weather.model;

public class WeatherForecastHour {

	private Integer temperature;
	private Float precipitation;
	private Integer sunshine;
	private Boolean rain;
	private Boolean snow;

	public WeatherForecastHour(Integer temperature, Float precipitation, Integer sunshine, Boolean rain, Boolean snow) {
		super();
		this.temperature = temperature;
		this.precipitation = precipitation;
		this.sunshine = sunshine;
		this.rain = rain;
		this.snow = snow;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public Float getPrecipitation() {
		return precipitation;
	}

	public Integer getSunshine() {
		return sunshine;
	}

	public Boolean getRain() {
		return rain;
	}

	public Boolean getSnow() {
		return snow;
	}

}
