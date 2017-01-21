package nl.tisit.weather.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;
import nl.tisit.weather.model.WeatherDaily;
import nl.tisit.weather.model.WeatherHourly;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class KnmiServiceTests {
	@Autowired
	protected WeatherService knmiService;

	@Test
	public void shouldFindWeatherDaily() {
		assertThat(this.knmiService).isNotNull();

		List<WeatherDaily> wdl = this.knmiService.findWeatherDaily(260, null);
		assertThat(wdl.get(0).getYyyymmdd()).isEqualTo(20160101);
		
		WeatherDaily wd = new WeatherDaily();
		wd.setYyyymmdd(20161101);
		wdl = this.knmiService.findWeatherDaily(260, wd);
		assertThat(wdl.get(0).getYyyymmdd()).isEqualTo(20161101);

	}

	@Test
	public void shouldFindWeatherHourly() {
		assertThat(this.knmiService).isNotNull();

		List<WeatherHourly> whl = this.knmiService.findWeatherHourly(260, null);
		assertThat(whl.get(0).getYyyymmdd()).isEqualTo(20160101);
		assertThat(whl.get(0).getHh()).isEqualTo(1);
		
		WeatherHourly wh = new WeatherHourly();
		wh.setYyyymmdd(20161101);
		whl = this.knmiService.findWeatherHourly(260, wh);
		assertThat(whl.get(0).getYyyymmdd()).isEqualTo(20161101);
		assertThat(whl.get(0).getHh()).isEqualTo(1);
	}

	@Test
	public void shouldLoadWeather() {
		assertThat(this.knmiService).isNotNull();

		this.knmiService.loadWeather();
	}
}
