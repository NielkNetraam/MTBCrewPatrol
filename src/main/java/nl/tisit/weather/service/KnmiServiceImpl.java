package nl.tisit.weather.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import nl.tisit.weather.model.WeatherDaily;
import nl.tisit.weather.model.WeatherHourly;
import nl.tisit.weather.repository.WeatherDailyRepository;
import nl.tisit.weather.repository.WeatherHourlyRepository;

@Service
public class KnmiServiceImpl implements WeatherService {
	@Autowired
	protected WeatherDailyRepository weatherDailyRepository;
	@Autowired
	protected WeatherHourlyRepository weatherHourlyRepository;

	private Log log = LogFactory.getLog(KnmiServiceImpl.class);

	private static final String knmi = "http://projects.knmi.nl/klimatologie";
	private static final String daggegevens = "/daggegevens/getdata_dag.cgi";
	private static final String uurgegevens = "/uurgegevens/getdata_uur.cgi";
	private static final String stations = "?stns={station}";
	private static final String start = "&start={start}";
	private static final String varsDag = "&vars=DDVEC:FHVEC:FG:FHX:FHXH:FHN:FHNH:FXX:FXXH:TG:TN:TNH:TX:TXH:T10N:T10NH:SQ:SP:Q:DR:RH:RHX:RHXH:PG:PX:PXH:PN:PNH:VVN:VVNH:VVX:VVXH:NG:UG:UX:UXH:UN:UNH:EV24";
	//	private static final String end = "?end={end}";
	private static final String varsHour = "&vars=DD:FH:FF:FX:T:T10:TD:SQ:Q:DR:RH:P:VV:N:U:WW:IX:M:R:S:O:Y";

	@Override
	public List<WeatherDaily> findWeatherDaily(Integer station, WeatherDaily weatherDaily) {
		
		RestTemplate restTemplate = new RestTemplate();

		String uri = knmi+daggegevens+stations+start+varsDag;
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(uri);
		String url = urlBuilder.buildAndExpand(station,(weatherDaily==null?"20160101":weatherDaily.getYyyymmdd())).toUriString();

		log.debug("url: " + url);
		String response = restTemplate.getForObject(url, String.class);
		String lines[] = response.split("\r\n");

//		log.debug("count: " + lines.length);
		
		List<WeatherDaily> weather = new ArrayList<WeatherDaily>();
			
		for (String line : lines) {
			
			if (line.charAt(0) != '#') {
//				weather.add(new Weather(line.split(",")));
				WeatherDaily w = new WeatherDaily(line.split(","));
				weather.add(w);

				log.debug("weather: " + w);
			}
			else
				log.debug("line: " + line);

		}

		return weather;
	}
	
	@Override
	public List<WeatherHourly> findWeatherHourly(Integer station, WeatherHourly weatherHourly) {
		RestTemplate restTemplate = new RestTemplate();

		Integer time = (weatherHourly==null?2016010101:weatherHourly.getYyyymmdd()*100+1);
		
		String uri = knmi+uurgegevens+stations+start+varsHour;
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(uri);
		String url = urlBuilder.buildAndExpand(station,time).toUriString();

		log.debug("url: " + url);
		String response = restTemplate.getForObject(url, String.class);
		String lines[] = response.split("\r\n");

//		log.debug("count: " + lines.length);
		
		List<WeatherHourly> weather = new ArrayList<WeatherHourly>();
			
		for (String line : lines) {
			
			if (line.charAt(0) != '#') {
//				weather.add(new Weather(line.split(",")));
				WeatherHourly w = new WeatherHourly(line.split(","));
				weather.add(w);

				log.debug("weather: " + w);
			}
			else
				log.debug("line: " + line);

		}

		return weather;
	}

	@Override
	public LocalDate loadWeather() {
		
		WeatherDaily weatherDaily = weatherDailyRepository.findFirstByOrderByYyyymmddDesc();
		List<WeatherDaily> weatherDailys = findWeatherDaily(260, weatherDaily);
		

		for (WeatherDaily w : weatherDailys) {
			WeatherDaily sw = weatherDailyRepository.findByStnAndYyyymmdd(w.getStn(), w.getYyyymmdd());
				
			if (sw == null) {
				weatherDailyRepository.save(w);
			}				
		}
		
		WeatherHourly weatherHourly = weatherHourlyRepository.findFirstByOrderByYyyymmddDescHhDesc();
		List<WeatherHourly> weatherHourlys = findWeatherHourly(260, weatherHourly);
		
		LocalDate loadedTill = weatherHourly.getDate();

		for (WeatherHourly w : weatherHourlys) {
			WeatherHourly sw = weatherHourlyRepository.findByStnAndYyyymmddAndHh(w.getStn(), w.getYyyymmdd(), w.getHh());
				
			if (sw == null) {
				weatherHourlyRepository.save(w);
				 loadedTill = w.getDate();
			}
		}
		
		return loadedTill;
	}
}
