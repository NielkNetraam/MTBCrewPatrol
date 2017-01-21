package nl.tisit.mtbcrewpatrol.forecast;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;

import nl.tisit.keyregister.model.HolidayList;
import nl.tisit.mtbcrewpatrol.model.Sector;
import nl.tisit.weather.model.WeatherForecast;

public interface ForecastService {
	public void modelForecast() throws IOException;
	
	public Map<Integer, VisitorsForecast> forcast14DaySectors() throws IOException, DatatypeConfigurationException;
	public Map<Integer, VisitorsForecast> forcast14DayTracksSectorAvg() throws IOException, DatatypeConfigurationException;
	public Map<Integer, VisitorsForecast> forcast14DayTracksSectorMax() throws IOException, DatatypeConfigurationException;
	public Map<Integer, VisitorsForecast> forcast14DayTracks() throws IOException, DatatypeConfigurationException;
	public Map<Integer, VisitorsForecast> forcast14DayTracksAvg() throws IOException, DatatypeConfigurationException;
	public Map<Integer, VisitorsForecast> forcast14DayTracksMax() throws IOException, DatatypeConfigurationException;

	public VisitorsForecast forcast14DaySector(Sector sector, HolidayList holidayList, WeatherForecast weatherForecast, LocalDate modelDate) throws IOException, DatatypeConfigurationException;
}
