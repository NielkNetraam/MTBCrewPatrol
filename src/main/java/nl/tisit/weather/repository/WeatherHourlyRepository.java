package nl.tisit.weather.repository;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.weather.model.WeatherHourly;

public interface WeatherHourlyRepository extends CrudRepository<WeatherHourly, Integer> {
	WeatherHourly findByStnAndYyyymmddAndHh(Integer stn, Integer yyyymmdd, Integer hh);
	WeatherHourly findFirstByOrderByYyyymmddDescHhDesc();

}

