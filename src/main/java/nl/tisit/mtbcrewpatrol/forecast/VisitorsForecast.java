package nl.tisit.mtbcrewpatrol.forecast;

import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class VisitorsForecast {
	private LocalDate startDate;
	private Integer days;

	private Map<LocalDate, VisitorsForecastDay> visitorsForecastDays = new TreeMap<LocalDate, VisitorsForecastDay>();

	public VisitorsForecast(LocalDate startDate, Integer days) {
		this.startDate = startDate;
		this.days = days;

		for (int day = 0; day < days; day++) {
			LocalDate date = startDate.plusDays(day);

			visitorsForecastDays.put(date, new VisitorsForecastDay(date));
		}
	}

	public VisitorsForecast(VisitorsForecast vf) {
		this.startDate = vf.startDate;
		this.days = vf.days;

		for (Entry<LocalDate, VisitorsForecastDay> entry : vf.visitorsForecastDays.entrySet()) {
			this.visitorsForecastDays.put(entry.getKey(), new VisitorsForecastDay(entry.getValue()));
		}
	}
	
	public void addForecast(LocalDate date, Integer hour, Double forecast, FeaturesInstance features) {
		visitorsForecastDays.get(date).addForecast(hour, forecast, features);
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Map<LocalDate, VisitorsForecastDay> getVisitorsForecastDays() {
		return visitorsForecastDays;
	}

	public void setVisitorsForecastDays(Map<LocalDate, VisitorsForecastDay> visitorsForecastDays) {
		this.visitorsForecastDays = visitorsForecastDays;
	}
}
