package nl.tisit.mtbcrewpatrol.forecast;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.tisit.MtbCrewPatrolApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtbCrewPatrolApplication.class)
public class ForecastServiceTests {
	@Autowired
	protected ForecastService service;
	
	@Test
	public void shouldModelForecast() throws IOException {
		assertThat(this.service).isNotNull();
		
		service.modelForecast();
	}

	private void writeResult(String fileName, Map<Integer, VisitorsForecast> forecast) throws IOException {
		String separator = ";";
		File file = new File(fileName);

		if (!file.exists())
			file.createNewFile();

		BufferedWriter bwFile = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

		Features fs = new Features(null, null, false);
		bwFile.write("id;date;hour;datetime;forecast;forecastInteger;forecast1d;"+fs.csvHeader()+"\n");
	
		for (Entry<Integer, VisitorsForecast> forecastEntry : forecast.entrySet()) {
			for (Entry<LocalDate, VisitorsForecastDay> vfd : forecastEntry.getValue().getVisitorsForecastDays().entrySet()) {
				for (Entry<Integer, Double> fc : vfd.getValue().getHourlyVisitors().entrySet()) {
					long forecastInteger = Math.round(fc.getValue());
					BigDecimal forecast1d = new BigDecimal(fc.getValue()).setScale(1, BigDecimal.ROUND_HALF_UP);
	
					bwFile.write(forecastEntry.getKey() + separator +
							vfd.getKey() + separator +
							fc.getKey() + separator +
							vfd.getKey().atTime(fc.getKey(), 0) + separator +
							fc.getValue().toString().replace('.', ',') + separator +
							forecastInteger + separator +
							forecast1d.toString().replace('.', ',') + ";" + vfd.getValue().getHourlyFeatures().get(fc.getKey()).getInstanceString(";")+"\n");
				}
			}
		}
	
		bwFile.close();
		
	}
	
	@Test
	public void shouldForcast14DaySectors() throws IOException, DatatypeConfigurationException {
		assertThat(this.service).isNotNull();
		Map<Integer, VisitorsForecast> forecast = this.service.forcast14DaySectors();

		for (Integer sector : forecast.keySet())
			assertThat(forecast.get(sector).getVisitorsForecastDays().size()).isEqualTo(14);

		String file = "/Users/mklein/Documents/workspace-sts/MTBCrewPatrol/forcast14DaySectors.csv";
		writeResult(file, forecast);
	}

	@Test
	public void shouldForcast14DayTracksSectorAvg() throws IOException, DatatypeConfigurationException {
		assertThat(this.service).isNotNull();
		Map<Integer, VisitorsForecast> forecast = this.service.forcast14DayTracksSectorAvg();

		for (Integer track : forecast.keySet())
			assertThat(forecast.get(track).getVisitorsForecastDays().size()).isEqualTo(14);

		String file = "/Users/mklein/Documents/workspace-sts/MTBCrewPatrol/forcast14DayTracksSectorAvg.csv";
		writeResult(file, forecast);
	}

	@Test
	public void shouldForcast14DayTracksSectorMax() throws IOException, DatatypeConfigurationException {
		assertThat(this.service).isNotNull();
		Map<Integer, VisitorsForecast> forecast = this.service.forcast14DayTracksSectorMax();

		for (Integer track : forecast.keySet())
			assertThat(forecast.get(track).getVisitorsForecastDays().size()).isEqualTo(14);

		String file = "/Users/mklein/Documents/workspace-sts/MTBCrewPatrol/forcast14DayTracksSectorMax.csv";
		writeResult(file, forecast);
	}

	@Test
	public void shouldForcast14DayTracks() throws IOException, DatatypeConfigurationException {
		assertThat(this.service).isNotNull();
		Map<Integer, VisitorsForecast> forecast = this.service.forcast14DayTracksSectorAvg();

		for (Integer track : forecast.keySet())
			assertThat(forecast.get(track).getVisitorsForecastDays().size()).isEqualTo(14);

		String file = "/Users/mklein/Documents/workspace-sts/MTBCrewPatrol/forcast14DayTracks.csv";
		writeResult(file, forecast);
	}

	@Test
	public void shouldForcast14DayTracksAvg() throws IOException, DatatypeConfigurationException {
		assertThat(this.service).isNotNull();
		Map<Integer, VisitorsForecast> forecast = this.service.forcast14DayTracksAvg();

		for (Integer track : forecast.keySet())
			assertThat(forecast.get(track).getVisitorsForecastDays().size()).isEqualTo(14);

		String file = "/Users/mklein/Documents/workspace-sts/MTBCrewPatrol/forcast14DayTracksAvg.csv";
		writeResult(file, forecast);
	}

	@Test
	public void shouldForcast14DayTracksMax() throws IOException, DatatypeConfigurationException {
		assertThat(this.service).isNotNull();
		Map<Integer, VisitorsForecast> forecast = this.service.forcast14DayTracksAvg();

		for (Integer track : forecast.keySet())
			assertThat(forecast.get(track).getVisitorsForecastDays().size()).isEqualTo(14);

		String file = "/Users/mklein/Documents/workspace-sts/MTBCrewPatrol/forcast14DayTracksMax.csv";
		writeResult(file, forecast);
	}
}