package nl.tisit.mtbcrewpatrol.forecast;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class VisitorsForecastDay {
	private LocalDate date;
	private Double totalExpectedVisitor = 0.0;
	private Double maxExpectedVisitor = 0.0;
	private Integer maxExpectedVisitorHour = 0;
	private Map<Integer, Double> hourlyVisitors = new TreeMap<Integer, Double>();
	private Map<Integer, FeaturesInstance> hourlyFeatures = new TreeMap<Integer, FeaturesInstance>();

	public void addForecast(Integer hour, Double forecast, FeaturesInstance features) {
		hourlyVisitors.put(hour, forecast);
		hourlyFeatures.put(hour, features);
		totalExpectedVisitor += forecast;
		if (forecast > maxExpectedVisitor) {
			maxExpectedVisitor = forecast;
			maxExpectedVisitorHour = hour;
		}
	}

	public VisitorsForecastDay(LocalDate date) {
		this.date = date;
		
		for (int hour = 0; hour < 24; hour++)
			hourlyVisitors.put(hour, 0.0);
	}

	public VisitorsForecastDay(VisitorsForecastDay vfd) {
		this.date = vfd.date;
		this.totalExpectedVisitor = vfd.totalExpectedVisitor;
		this.maxExpectedVisitor = vfd.maxExpectedVisitor;
		this.maxExpectedVisitorHour = vfd.maxExpectedVisitorHour;

		for (int hour = 0; hour < 24; hour++) {
			this.hourlyVisitors.put(hour, vfd.hourlyVisitors.get(hour));
			this.hourlyFeatures.put(hour, vfd.hourlyFeatures.get(hour));
		}
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getTotalExpectedVisitor() {
		return totalExpectedVisitor;
	}

	public void setTotalExpectedVisitor(Double totalExpectedVisitor) {
		this.totalExpectedVisitor = totalExpectedVisitor;
	}

	public Double getMaxExpectedVisitor() {
		return maxExpectedVisitor;
	}

	public void setMaxExpectedVisitor(Double maxExpectedVisitor) {
		this.maxExpectedVisitor = maxExpectedVisitor;
	}

	public Integer getMaxExpectedVisitorHour() {
		return maxExpectedVisitorHour;
	}

	public void setMaxExpectedVisitorHour(Integer maxExpectedVisitorHour) {
		this.maxExpectedVisitorHour = maxExpectedVisitorHour;
	}

	public Map<Integer, Double> getHourlyVisitors() {
		return hourlyVisitors;
	}

	public void setHourlyVisitors(Map<Integer, Double> hourlyVisitors) {
		this.hourlyVisitors = hourlyVisitors;
	}

	public Map<Integer, FeaturesInstance> getHourlyFeatures() {
		return hourlyFeatures;
	}

	public void setHourlyFeatures(Map<Integer, FeaturesInstance> hourlyFeatures) {
		this.hourlyFeatures = hourlyFeatures;
	}
	
	
}
