package nl.tisit.weather.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import nl.tisit.weather.model.Forecast;
import nl.tisit.weather.model.ForecastDay;
import nl.tisit.weather.model.ForecastHour;
import nl.tisit.weather.model.WeatherForecast;
import nl.tisit.weather.model.WeatherForecastHour;
import nl.tisit.weather.repository.WeatherDailyRepository;
import nl.tisit.weather.repository.WeatherHourlyRepository;

@Service
public class BuienradarServiceImpl implements WeatherForecastService {
	@Autowired
	protected WeatherDailyRepository weatherDailyRepository;
	@Autowired
	protected WeatherHourlyRepository weatherHourlyRepository;

	private Log log = LogFactory.getLog(BuienradarServiceImpl.class);

	private static final String buienradar = "http://api.buienradar.nl/data/forecast/1.1/all";
	private static final String days14 = "/2757783";
	private static final String moment = "?btc={moment}";

	public Forecast getForecast14Days() {
		
		RestTemplate restTemplate = new RestTemplate();

		String url = buienradar+days14+moment;
		log.debug("url: "+url);
		
		Calendar calendar = GregorianCalendar.getInstance(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		Forecast forecast = restTemplate.getForObject(url, Forecast.class, dateFormat.format(calendar.getTime()));

		log.debug("forecast:"+forecast);
		return forecast;
	}
	
	
	
	@Override
	public WeatherForecast buildWeatherForecast() {
		WeatherForecast weatherForecast = new WeatherForecast();
		
		Forecast forecast = getForecast14Days();
		
		LocalDateTime sunrise = null;
		LocalDateTime sunset = null;

		for (ForecastDay forecastDay : forecast.getDays()) {
			if (forecastDay.getSunrise() != null) sunrise = forecastDay.getSunrise();
			if (forecastDay.getSunset() != null) sunset = forecastDay.getSunset();

			if (forecastDay.getHours() == null || forecastDay.getHours().size() != 24) {
				long diffInSeconds = sunset.atZone(ZoneId.systemDefault()).toEpochSecond() - sunrise.atZone(ZoneId.systemDefault()).toEpochSecond();
			    long deltaMinutes = TimeUnit.MINUTES.convert(diffInSeconds,TimeUnit.SECONDS) - 120;
				    
			    Float deltaTemp = null;
			    if (forecastDay.getMaxtemperature() == null)
			    	deltaTemp = ((forecastDay.getMaxtemp()-forecastDay.getMintemp())/deltaMinutes) * 60;
			    else
			    	deltaTemp = ((forecastDay.getMaxtemperature()-forecastDay.getMintemperature())/deltaMinutes) * 60;
					
			    Integer sunriseHour = sunrise.getHour();
			    Integer sunsetHourMin2 = sunset.getHour() - 2;
				    
				for (Integer hour = 0; hour < 24; hour++) {
					LocalDateTime time = forecastDay.getDate().atTime(hour, 0);
						
					ForecastHour forecastHour = new ForecastHour();
						
					forecastHour.setDatetime(time);
					forecastHour.setHour(hour);
					if (hour <= sunriseHour) 
						forecastHour.setTemperature(forecastDay.getMintemperature()+((sunriseHour-hour)*deltaTemp));
					else if (hour <= sunsetHourMin2)
						forecastHour.setTemperature(forecastDay.getMintemperature()+((hour-sunriseHour)*deltaTemp));
					else
						forecastHour.setTemperature(forecastDay.getMaxtemperature()-((hour-sunsetHourMin2)*deltaTemp));

					forecastHour.setSunshinepower(0); //TODO		
						
					forecastHour.setFeeltemperature(forecastHour.getTemperature()+forecastDay.getFeeltemperature()-forecastDay.getTemperature());
					forecastHour.setWindspeed(forecastDay.getWindspeed());
					forecastHour.setBeaufort(forecastDay.getBeaufort());
					forecastHour.setWinddirection(forecastDay.getWinddirection());
					forecastHour.setIconcode(forecastDay.getIconcode());
					forecastHour.setIconid(forecastDay.getIconid());
					forecastHour.setPrecipitationmm(forecastDay.getPrecipitationmm()/12);
					forecastHour.setPrecipationmm(forecastDay.getPrecipationmm()/12);
					forecastHour.setPrecipation(forecastDay.getPrecipation());
					forecastHour.setPrecipitation(forecastDay.getPrecipitation());
					
					weatherForecast.addWeatherForecastHour(forecastDay.getDate(), hour, 
							new WeatherForecastHour((int)((Float) (forecastHour.getTemperature() * 10)).longValue(),
									forecastHour.getPrecipationmm()
									, forecastHour.getSunshinepower()
									, (forecastHour.getPrecipationmm() > 0 && forecastHour.getTemperature() > 0)
									, (forecastHour.getPrecipitationmm() > 0 && forecastHour.getTemperature() <= 0)));
				}
			} else {
				for (ForecastHour forecastHour : forecastDay.getHours()) 
					weatherForecast.addWeatherForecastHour(forecastDay.getDate(), forecastHour.getHour()
							, new WeatherForecastHour(
									(int)((Float) (forecastHour.getTemperature() * 10)).longValue(),
							forecastHour.getPrecipationmm()
							, forecastHour.getSunshinepower()
							, (forecastHour.getPrecipationmm() > 0 && forecastHour.getTemperature() > 0)
							, (forecastHour.getPrecipitationmm() > 0 && forecastHour.getTemperature() <= 0)));	
			}	
		}
		
		return weatherForecast;
	}
}
