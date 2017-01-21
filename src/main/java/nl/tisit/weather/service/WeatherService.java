package nl.tisit.weather.service;

import java.time.LocalDate;
import java.util.List;

import nl.tisit.weather.model.WeatherDaily;
import nl.tisit.weather.model.WeatherHourly;

public interface WeatherService {
	public LocalDate loadWeather();
	public List<WeatherDaily> findWeatherDaily(Integer station, WeatherDaily weatherDaily);
	public List<WeatherHourly> findWeatherHourly(Integer station, WeatherHourly weatherHourly);
}
