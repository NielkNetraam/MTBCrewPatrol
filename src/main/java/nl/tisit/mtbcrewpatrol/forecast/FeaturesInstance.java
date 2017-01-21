package nl.tisit.mtbcrewpatrol.forecast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import nl.tisit.util.Feature;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class FeaturesInstance {
	private Features features;
	private String id;
	private LocalDate date;
	private Integer hour;
	private Double count;
	private LocalDateTime dateTime;
	
	public FeaturesInstance(Features features, String id, LocalDate date, Integer hour, Double count) {
		this.features = features;
		this.id = id;
		this.date = date;
		this.hour = hour;
		this.count = count;
		this.dateTime = date.atTime(hour, 0);
	}
	
	public FeaturesInstance(Features features, String id, LocalDate date, Integer hour) {
		this.features = features;
		this.id = id;
		this.date = date;
		this.hour = hour;
		this.count = null;
		this.dateTime = date.atTime(hour, 0);
	}

	private Integer getTemperature() {
		if (features.getForecast()) 
			return features.getForecastHour(dateTime).getTemperature();
		else 
			return features.getWeatherHourly(dateTime).getT();
	}
	
	private Float getPrecipitation() {
		if (features.getForecast()) 
			return features.getForecastHour(dateTime).getPrecipitation();
		else 
			if (features.getWeatherHourly(dateTime).getRh() == null)
				return new Float(0);
			else 
				return ((float)(features.getWeatherHourly(dateTime).getRh()==-1?0.5:features.getWeatherHourly(dateTime).getRh())/10);
	}
	
	private Integer getSunshine() {
		if (features.getForecast()) 
			return features.getForecastHour(dateTime).getSunshine();
		else 
//			return ((float)(weatherHourly.getSq()==-1?0.5:weatherHourly.getSq()));
			return features.getWeatherHourly(dateTime).getQ();
	}
	
	private Boolean getRain() {
		if (features.getForecast()) 
			return (features.getForecastHour(dateTime).getRain());
		else 
			return (features.getWeatherHourly(dateTime).getR().equals(1));
	}
	
	private Boolean getSnow() {
		if (features.getForecast()) 
			return (features.getForecastHour(dateTime).getSnow());
		else 
			return (features.getWeatherHourly(dateTime).getS().equals(1));
	}
	
	public Instance getInstance(Instances instances) {
	
		Instance instance = new DenseInstance(features.getAttributes().size());
		if (features.active("id")) instance.setValue(instances.attribute("id"), id);
		if (features.active("date")) instance.setValue(instances.attribute("date"), date.format(DateTimeFormatter.ISO_LOCAL_DATE));
		if (features.active("year")) instance.setValue(instances.attribute("year"), date.getYear());
		if (features.active("month")) instance.setValue(instances.attribute("month"), date.getMonthValue());
		if (features.active("month_shift")) instance.setValue(instances.attribute("month_shift"), Feature.shiftMonth(date));
		if (features.active("day_of_week")) instance.setValue(instances.attribute("day_of_week"), date.getDayOfWeek().getValue());
		if (features.active("day_of_week_shift")) instance.setValue(instances.attribute("day_of_week_shift"), Feature.shiftDayOfWeek(date));
		if (features.active("day_of_year")) instance.setValue(instances.attribute("day_of_year"), date.getDayOfYear());
		if (features.active("day_of_year_shift")) instance.setValue(instances.attribute("day_of_year_shift"), Feature.shiftDayOfYear(date));
		if (features.active("hour")) instance.setValue(instances.attribute("hour"), hour);
		if (features.active("summer_time")) instance.setValue(instances.attribute("summer_time"), Feature.isSummerTime(date).toString());
		if (features.active("holiday")) instance.setValue(instances.attribute("holiday"), features.getHolidayList().isHoliday(date).toString());
		if (features.active("holidaysNorth")) instance.setValue(instances.attribute("holidaysNorth"), features.getHolidayList().isHolidaysNorth(date).toString());
		if (features.active("holidaysMiddle")) instance.setValue(instances.attribute("holidaysMiddle"), features.getHolidayList().isHolidaysMiddle(date).toString());
		if (features.active("holidaysSouth")) instance.setValue(instances.attribute("holidaysSouth"), features.getHolidayList().isHolidaysSouth(date).toString());
		if (features.active("holidaysCount")) instance.setValue(instances.attribute("holidaysCount"), features.getHolidayList().holidaysCount(date));
		if (features.active("temperature")) instance.setValue(instances.attribute("temperature"), getTemperature());
		if (features.active("sunshine")) instance.setValue(instances.attribute("sunshine"), getSunshine());
		if (features.active("rainfallTime")) instance.setValue(instances.attribute("rainfallTime"), "false");
		if (features.active("precipitation")) instance.setValue(instances.attribute("precipitation"), getPrecipitation());
		if (features.active("fog")) instance.setValue(instances.attribute("fog"), "false");
		if (features.active("rain")) instance.setValue(instances.attribute("rain"), getRain().toString());
		if (features.active("snow")) instance.setValue(instances.attribute("snow"), getSnow().toString());
		if (features.active("lightning")) instance.setValue(instances.attribute("lightning"), "false");
		if (features.active("ice")) instance.setValue(instances.attribute("ice"), "false");
		if (features.active("count") && count != null) instance.setValue(instances.attribute("count"), count);

		return instance;
	}	

	public String getInstanceString(String separator) {
		String instance = "";	
		if (features.active("id")) instance += ((instance.length() > 0 ? separator : "") + id);
		if (features.active("date")) instance += ((instance.length() > 0 ? separator : "") + date.format(DateTimeFormatter.ISO_LOCAL_DATE));
		if (features.active("year")) instance += ((instance.length() > 0 ? separator : "") + date.getYear());
		if (features.active("month")) instance += ((instance.length() > 0 ? separator : "") + date.getMonthValue());
		if (features.active("month_shift")) instance += ((instance.length() > 0 ? separator : "") + Feature.shiftMonth(date));
		if (features.active("day_of_week")) instance += ((instance.length() > 0 ? separator : "") + date.getDayOfWeek().getValue());
		if (features.active("day_of_week_shift")) instance += ((instance.length() > 0 ? separator : "") + Feature.shiftDayOfWeek(date));
		if (features.active("day_of_year")) instance += ((instance.length() > 0 ? separator : "") + date.getDayOfYear());
		if (features.active("day_of_year_shift")) instance += ((instance.length() > 0 ? separator : "") + Feature.shiftDayOfYear(date));
		if (features.active("hour")) instance += ((instance.length() > 0 ? separator : "") + hour);
		if (features.active("summer_time")) instance += ((instance.length() > 0 ? separator : "") + Feature.isSummerTime(date));
		if (features.active("holiday")) instance += ((instance.length() > 0 ? separator : "") + features.getHolidayList().isHoliday(date));
		if (features.active("holidaysNorth")) instance += ((instance.length() > 0 ? separator : "") + features.getHolidayList().isHolidaysNorth(date));
		if (features.active("holidaysMiddle")) instance += ((instance.length() > 0 ? separator : "") + features.getHolidayList().isHolidaysMiddle(date));
		if (features.active("holidaysSouth")) instance += ((instance.length() > 0 ? separator : "") + features.getHolidayList().isHolidaysSouth(date));
		if (features.active("holidaysCount")) instance += ((instance.length() > 0 ? separator : "") + features.getHolidayList().holidaysCount(date));
		if (features.active("temperature")) instance += ((instance.length() > 0 ? separator : "") + getTemperature());
		if (features.active("sunshine")) instance += ((instance.length() > 0 ? separator : "") + getSunshine());
		if (features.active("rainfallTime")) instance += ((instance.length() > 0 ? separator : "") + features.getWeatherHourly(dateTime).getDr());
		if (features.active("precipitation")) instance += ((instance.length() > 0 ? separator : "") + getPrecipitation());
		if (features.active("fog")) instance += ((instance.length() > 0 ? separator : "") + features.getWeatherHourly(dateTime).getM().equals(1));
		if (features.active("rain")) instance += ((instance.length() > 0 ? separator : "") + getRain());
		if (features.active("snow")) instance += ((instance.length() > 0 ? separator : "") + getSnow());
		if (features.active("lightning")) instance += ((instance.length() > 0 ? separator : "") + features.getWeatherHourly(dateTime).getO().equals(1));
		if (features.active("ice")) instance += ((instance.length() > 0 ? separator : "") + features.getWeatherHourly(dateTime).getY().equals(1));
		if (features.active("count")) instance += ((instance.length() > 0 ? separator : "") + count);
	
		return instance;
	}
	
}
