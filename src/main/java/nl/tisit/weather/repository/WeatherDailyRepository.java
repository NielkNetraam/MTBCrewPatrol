package nl.tisit.weather.repository;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.weather.model.WeatherDaily;

public interface WeatherDailyRepository extends CrudRepository<WeatherDaily, Integer> {
	WeatherDaily findByStnAndYyyymmdd(Integer stn, Integer yyyymmdd);
	WeatherDaily findFirstByOrderByYyyymmddDesc();
}

