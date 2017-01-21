package nl.tisit.weather.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.weather.model.Forecast;
import nl.tisit.weather.model.WeatherForecast;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class BuienradarServiceTests {
	@Autowired
	protected BuienradarServiceImpl service;

	@Test
	public void shouldGetForecast14Days() {
		assertThat(this.service).isNotNull();

		Forecast forecast = this.service.getForecast14Days();
		assertThat(forecast.getTimeOffset()).isEqualTo(new Float(1.0));
		
	}

	@Test
	public void shouldBuildWeatherForecast() {
		assertThat(this.service).isNotNull();
		
		WeatherForecast weatherForecast = service.buildWeatherForecast();
		LocalDateTime dateTime = LocalDate.now().plusDays(8).atTime(8, 0);
		assertThat(weatherForecast.getWeatherForecastHour(dateTime).getTemperature()).isEqualTo(new Float(1.0));
	}
}
