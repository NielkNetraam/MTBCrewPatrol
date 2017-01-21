package nl.tisit.weather.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class WeatherForecast {
	private Map<LocalDateTime, WeatherForecastHour> weatherForecastHourMap;
	
	public WeatherForecast() {
		this.weatherForecastHourMap = new HashMap<LocalDateTime, WeatherForecastHour>(336);
	}
	
	public void addWeatherForecastHour(LocalDate date, Integer hour, WeatherForecastHour weatherForecastHour) {
		weatherForecastHourMap.put(date.atTime(hour, 0), weatherForecastHour);		
	}
	
	public WeatherForecastHour getWeatherForecastHour(LocalDateTime dateTime) {
		return weatherForecastHourMap.get(dateTime);
	}
}
