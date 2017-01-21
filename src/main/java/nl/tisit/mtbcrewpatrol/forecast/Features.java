package nl.tisit.mtbcrewpatrol.forecast;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nl.tisit.keyregister.model.HolidayList;
import nl.tisit.weather.model.WeatherForecast;
import nl.tisit.weather.model.WeatherForecastHour;
import nl.tisit.weather.model.WeatherHourly;
import weka.core.Attribute;

public class Features {
	private Map<String, Boolean> active;
	private ArrayList<Attribute> attributes;
	private Map<String, Integer> attributePositions;
	
	private HolidayList holidayList;
	private WeatherForecast weatherForecast;
	private Boolean forecast;
	
	private Map<LocalDateTime, WeatherHourly> dateWeatherHourlyMap;
	
	private void initializeWeather(Iterable<WeatherHourly> weatherHourlyList) {
		dateWeatherHourlyMap = new HashMap<LocalDateTime, WeatherHourly>();
		
		if (weatherHourlyList != null)
			for (WeatherHourly weatherHourly : weatherHourlyList) 
				dateWeatherHourlyMap.put(weatherHourly.getDateTime(), weatherHourly);
	}
	
	private void initializeAttributes(Boolean allAttributes) {
		active = new TreeMap<String, Boolean>();
		
		active.put("id", false || allAttributes);
		active.put("date", false || allAttributes);
		active.put("year", false || allAttributes);
		active.put("month", true);
		active.put("month_shift", false || allAttributes);
		active.put("day_of_week", true);
		active.put("day_of_week_shift", false || allAttributes);
		active.put("day_of_year", true);
		active.put("day_of_year_shift", false || allAttributes);
		active.put("hour", true);
		active.put("summer_time", true);
		active.put("holiday", true);
		active.put("holidaysNorth", false || allAttributes);
		active.put("holidaysMiddle", false || allAttributes);
		active.put("holidaysSouth", false || allAttributes);
		active.put("holidaysCount", true);
		active.put("temperature", true);
		active.put("sunshine", true);
		active.put("rainfallTime", false || allAttributes);
		active.put("precipitation", true);
		active.put("fog", false || allAttributes);
		active.put("rain", false || allAttributes);
		active.put("snow", false || allAttributes);
		active.put("lightning", false || allAttributes);
		active.put("ice", false || allAttributes);
		active.put("count", true);
		
		attributes = new ArrayList<Attribute>();
		if (active.get("id")) attributes.add(new Attribute("id"));
		if (active.get("date")) attributes.add(new Attribute("date"));
		if (active.get("year")) attributes.add(new Attribute("year"));
		if (active.get("month")) attributes.add(new Attribute("month"));
		if (active.get("month_shift")) attributes.add(new Attribute("month_shift"));
		if (active.get("day_of_week")) attributes.add(new Attribute("day_of_week"));
		if (active.get("day_of_week_shift")) attributes.add(new Attribute("day_of_week_shift"));
		if (active.get("day_of_year")) attributes.add(new Attribute("day_of_year"));
		if (active.get("day_of_year_shift")) attributes.add(new Attribute("day_of_year_shift"));
		if (active.get("hour")) attributes.add(new Attribute("hour"));

		List<String> trueFalseString = new ArrayList<String>(2);
		trueFalseString.add("true");
		trueFalseString.add("false");
		if (active.get("summer_time")) attributes.add(new Attribute("summer_time", trueFalseString));
		if (active.get("holiday")) attributes.add(new Attribute("holiday", trueFalseString));
		if (active.get("holidaysNorth")) attributes.add(new Attribute("holidaysNorth", trueFalseString));
		if (active.get("holidaysMiddle")) attributes.add(new Attribute("holidaysMiddle", trueFalseString));
		if (active.get("holidaysSouth")) attributes.add(new Attribute("holidaysSouth", trueFalseString));

		if (active.get("holidaysCount")) attributes.add(new Attribute("holidaysCount"));
		if (active.get("temperature")) attributes.add(new Attribute("temperature"));
		if (active.get("sunshine")) attributes.add(new Attribute("sunshine"));
		if (active.get("rainfallTime")) attributes.add(new Attribute("rainfallTime"));
		if (active.get("precipitation")) attributes.add(new Attribute("precipitation"));

		if (active.get("fog")) attributes.add(new Attribute("fog", trueFalseString));
		if (active.get("rain")) attributes.add(new Attribute("rain", trueFalseString));
		if (active.get("snow")) attributes.add(new Attribute("snow", trueFalseString));
		if (active.get("lightning")) attributes.add(new Attribute("lightning", trueFalseString));
		if (active.get("ice")) attributes.add(new Attribute("ice", trueFalseString));
		if (active.get("count")) attributes.add(new Attribute("count"));

		attributePositions = new TreeMap<String, Integer>();
		int i = 0;
		if (active.get("id")) attributePositions.put("id", i++);
		if (active.get("date")) attributePositions.put("date", i++);
		if (active.get("year")) attributePositions.put("year", i++);
		if (active.get("month")) attributePositions.put("month", i++);
		if (active.get("month_shift")) attributePositions.put("month_shift", i++);
		if (active.get("day_of_week")) attributePositions.put("day_of_week", i++);
		if (active.get("day_of_week_shift")) attributePositions.put("day_of_week_shift", i++);
		if (active.get("day_of_year")) attributePositions.put("day_of_year", i++);
		if (active.get("day_of_year_shift")) attributePositions.put("day_of_year_shift", i++);
		if (active.get("hour")) attributePositions.put("hour", i++);
		if (active.get("summer_time")) attributePositions.put("summer_time", i++);
		if (active.get("holiday")) attributePositions.put("holiday", i++);
		if (active.get("holidaysNorth")) attributePositions.put("holidaysNorth", i++);
		if (active.get("holidaysMiddle")) attributePositions.put("holidaysMiddle", i++);
		if (active.get("holidaysSouth")) attributePositions.put("holidaysSouth", i++);
		if (active.get("holidaysCount")) attributePositions.put("holidaysCount", i++);
		if (active.get("temperature")) attributePositions.put("temperature", i++);
		if (active.get("sunshine")) attributePositions.put("sunshine", i++);
		if (active.get("rainfallTime")) attributePositions.put("rainfallTime", i++);
		if (active.get("precipitation")) attributePositions.put("precipitation", i++);

		if (active.get("fog")) attributePositions.put("fog", i++);
		if (active.get("rain")) attributePositions.put("rain", i++);
		if (active.get("snow")) attributePositions.put("snow", i++);
		if (active.get("lightning")) attributePositions.put("lightning", i++);
		if (active.get("ice")) attributePositions.put("ice", i++);
		if (active.get("count")) attributePositions.put("count", i++);
	}

	public Features(HolidayList holidayList, Iterable<WeatherHourly> weatherHourlyList, Boolean allAttributes) {
		this.holidayList = holidayList;
		initializeWeather(weatherHourlyList);	
		
		forecast = false;
		
		initializeAttributes(allAttributes);
	}
	
	public Features(HolidayList holidayList, WeatherForecast weatherForecast) {
		this.holidayList = holidayList;
		this.weatherForecast = weatherForecast;
		
		forecast = true;

		initializeAttributes(false);
	}

	public WeatherHourly getWeatherHourly(LocalDateTime dateTime) {
		return dateWeatherHourlyMap.get(dateTime);
	}
	
	public WeatherForecastHour getForecastHour(LocalDateTime dateTime) {
		return weatherForecast.getWeatherForecastHour(dateTime);	
	}
	
	public Boolean active(String attributeName) {
		return active.get(attributeName);
	}
	
	public ArrayList<Attribute> getAttributes() {
		return attributes;	
	}
	
	public Map<String, Boolean> getActive() {
		return active;
	}

	public Map<String, Integer> getAttributePositions() {
		return attributePositions;
	}

	public HolidayList getHolidayList() {
		return holidayList;
	}

	public Boolean getForecast() {
		return forecast;
	}

	public String arffHeader() {
		String header = "% 1. Title: MTB efforts\n%\n% 2. Sources:\n%      (a) Creator: M. Klein\n%\n@RELATION MTB_hour_efforts\n";				 
		if (active.get("id")) header += "@ATTRIBUTE id             {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30}\n";
		if (active.get("date")) header += "@ATTRIBUTE date               Date \"yyyy-MM-dd\"\n";
		if (active.get("year")) header += "@ATTRIBUTE year               Numeric\n";
		if (active.get("month")) header += "@ATTRIBUTE month              Numeric\n";
		if (active.get("month_shift")) header += "@ATTRIBUTE month_shift        Numeric\n";
		if (active.get("day_of_week")) header += "@ATTRIBUTE day_of_week        Numeric\n";
		if (active.get("day_of_week_shift")) header += "@ATTRIBUTE day_of_week_shift  Numeric\n";
		if (active.get("day_of_year")) header += "@ATTRIBUTE day_of_year        Numeric\n";
		if (active.get("day_of_year_shift")) header += "@ATTRIBUTE day_of_year_shift  Numeric\n";
		if (active.get("hour")) header += "@ATTRIBUTE hour               Numeric\n";
		if (active.get("summer_time")) header += "@ATTRIBUTE summer_time        {true,false}\n";
		if (active.get("holiday")) header += "@ATTRIBUTE holiday            {true,false}\n";
		if (active.get("holidaysNorth")) header += "@ATTRIBUTE holidaysNorth      {true,false}\n";
		if (active.get("holidaysMiddle")) header += "@ATTRIBUTE holidaysMiddle     {true,false}\n";
		if (active.get("holidaysSouth")) header += "@ATTRIBUTE holidaysSouth      {true,false}\n";
		if (active.get("holidaysCount")) header += "@ATTRIBUTE holidaysCount      Numeric\n";
		if (active.get("temperature")) header += "@ATTRIBUTE temperature        Numeric\n";
		if (active.get("sunshine")) header += "@ATTRIBUTE sunshine           Numeric\n";
		if (active.get("rainfallTime")) header += "@ATTRIBUTE rainfallTime       Numeric\n";
		if (active.get("precipitation")) header += "@ATTRIBUTE precipitation    Numeric\n";
		if (active.get("fog")) header += "@ATTRIBUTE fog	               {true,false}\n";
		if (active.get("rain")) header += "@ATTRIBUTE rain	           {true,false}\n";
		if (active.get("snow")) header += "@ATTRIBUTE snow	           {true,false}\n";
		if (active.get("lightning")) header += "@ATTRIBUTE lightning	       {true,false}\n";
		if (active.get("ice")) header += "@ATTRIBUTE ice	               {true,false}\n";
		if (active.get("count")) header += "@ATTRIBUTE count              Numeric\n";	
		header += "@DATA";
	
		return header;
	}
	
	public String csvHeader() {
		String header = "";	
		String separator = ";";
		if (active.get("id")) header += ((header.length() > 0 ? separator : "") + "id");
		if (active.get("date")) header += ((header.length() > 0 ? separator : "") + "date");
		if (active.get("year")) header += ((header.length() > 0 ? separator : "") + "year");
		if (active.get("month")) header += ((header.length() > 0 ? separator : "") + "month");
		if (active.get("month_shift")) header += ((header.length() > 0 ? separator : "") + "month_shift");
		if (active.get("day_of_week")) header += ((header.length() > 0 ? separator : "") + "day_of_week");
		if (active.get("day_of_week_shift")) header += ((header.length() > 0 ? separator : "") + "day_of_week_shift");
		if (active.get("day_of_year")) header += ((header.length() > 0 ? separator : "") + "day_of_year");
		if (active.get("day_of_year_shift")) header += ((header.length() > 0 ? separator : "") + "day_of_year_shift");
		if (active.get("hour")) header += ((header.length() > 0 ? separator : "") + "hour");
		if (active.get("summer_time")) header += ((header.length() > 0 ? separator : "") + "summer_time");
		if (active.get("holiday")) header += ((header.length() > 0 ? separator : "") + "holiday");
		if (active.get("holidaysNorth")) header += ((header.length() > 0 ? separator : "") + "holidaysNorth");
		if (active.get("holidaysMiddle")) header += ((header.length() > 0 ? separator : "") + "holidaysMiddle");
		if (active.get("holidaysSouth")) header += ((header.length() > 0 ? ";" : "") + "holidaysSouth");
		if (active.get("holidaysCount")) header += ((header.length() > 0 ? separator : "") + "holidaysCount");
		if (active.get("temperature")) header += ((header.length() > 0 ? separator : "") + "temperature");
		if (active.get("sunshine")) header += ((header.length() > 0 ? separator : "") + "sunshine");
		if (active.get("rainfallTime")) header += ((header.length() > 0 ? separator : "") + "rainfallTime");
		if (active.get("precipitation")) header += ((header.length() > 0 ? separator : "") + "precipitation");
		if (active.get("fog")) header += ((header.length() > 0 ? separator : "") + "fog");
		if (active.get("rain")) header += ((header.length() > 0 ? separator : "") + "rain");
		if (active.get("snow")) header += ((header.length() > 0 ? separator : "") + "snow");
		if (active.get("lightning")) header += ((header.length() > 0 ? separator : "") + "lightning");
		if (active.get("ice")) header += ((header.length() > 0 ? separator : "") + "ice");
		if (active.get("count")) header += ((header.length() > 0 ? separator : "") + "count");
			
		return header;
	}
}
