package nl.tisit.mtbcrewpatrol.forecast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.tisit.keyregister.model.HolidayList;
import nl.tisit.keyregister.model.Settings;
import nl.tisit.keyregister.repository.HolidayRepository;
import nl.tisit.keyregister.repository.HolidaysRepository;
import nl.tisit.keyregister.repository.SettingsRepository;
import nl.tisit.keyregister.service.KeyRegisterService;
import nl.tisit.mtbcrewpatrol.WildRideService;
import nl.tisit.mtbcrewpatrol.etl.EtlServiceImpl;
import nl.tisit.mtbcrewpatrol.model.ActivityStatus;
import nl.tisit.mtbcrewpatrol.model.RideType;
import nl.tisit.mtbcrewpatrol.model.Sector;
import nl.tisit.mtbcrewpatrol.model.Track;
import nl.tisit.mtbcrewpatrol.model.TrackSector;
import nl.tisit.mtbcrewpatrol.model.TrackType;
import nl.tisit.mtbcrewpatrol.model.dm.SectorInfo;
import nl.tisit.mtbcrewpatrol.repository.ActivityStatusRepository;
import nl.tisit.mtbcrewpatrol.repository.SectorInfoRepository;
import nl.tisit.mtbcrewpatrol.repository.SectorRepository;
import nl.tisit.mtbcrewpatrol.repository.TrackRepository;
import nl.tisit.strava.model.Activity;
import nl.tisit.strava.repository.ActivityRepository;
import nl.tisit.weather.model.WeatherForecast;
import nl.tisit.weather.repository.WeatherHourlyRepository;
import nl.tisit.weather.service.WeatherForecastService;
import nl.tisit.weather.service.WeatherService;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.M5P;
import weka.core.Instances;
import weka.core.SerializationHelper;

@Service
public class ForecastServiceImpl implements ForecastService {
	@Autowired
	protected WeatherForecastService weatherForecastService;
	@Autowired
	protected KeyRegisterService keyRegisterService;
	@Autowired
	protected WeatherService knmiService;
	@Autowired
	protected WildRideService wildRideService;
	@Autowired
	protected SectorRepository sectorRepository;
	@Autowired
	protected SectorInfoRepository sectorInfoRepository;
	@Autowired
	protected HolidayRepository holidayRepository;
	@Autowired
	protected HolidaysRepository holidaysRepository;
	@Autowired
	protected WeatherHourlyRepository weatherHourlyRepository;
	@Autowired
	protected ActivityStatusRepository activityStatusRepository;
	@Autowired
	protected ActivityRepository activityRepository;
	@Autowired
	protected SettingsRepository settingsRepository;
	@Autowired
	protected TrackRepository trackRepository;
	
	private Log log = LogFactory.getLog(EtlServiceImpl.class);

	private final static String fileLocation = "/Users/mklein/Documents/MTB/models/";
	
	private String getModelName(Sector sector, LocalDate modelTill) {
		return modelTill.format(DateTimeFormatter.BASIC_ISO_DATE) + "/" + "model_sector_" + sector.getId() + "_" + modelTill.format(DateTimeFormatter.BASIC_ISO_DATE);
	}
	
	private String getModelName(Track track, LocalDate modelTill) {
		return modelTill.format(DateTimeFormatter.BASIC_ISO_DATE) + "/" + "model_track_" + track.getId() + "_" + modelTill.format(DateTimeFormatter.BASIC_ISO_DATE);
	}
	
	private void modelForecastSector(Sector sector, LocalDate modelTill, Features fs) throws IOException {
		SectorInfo info = sectorInfoRepository.findBySectorId(sector.getId());
		
		LocalDate start = sector.getStartDate();
		log.debug("start: "+ start);
					
		LocalDate d = start;
		int daysBetween = (int)ChronoUnit.DAYS.between(start, modelTill);
		
		Instances instances = new Instances("track", fs.getAttributes(), daysBetween*24);
		instances.setClassIndex(fs.getAttributes().size() - 1);

		String modelName = getModelName(sector, modelTill);

		File csvFile = new File(fileLocation + modelName + ".csv");
		String separator = ";";
		
		if (!csvFile.exists()) 
			csvFile.createNewFile();

		BufferedWriter bwCsvFile = new BufferedWriter(new FileWriter(csvFile.getAbsoluteFile()));
		bwCsvFile.write(fs.csvHeader() + "\n");

		while (d.isBefore(modelTill)) {
			if (!sector.exception(d))
				for (Integer h = 0; h < 24; h++) {
					LocalDateTime hour = d.atTime(h, 0);
	
					Integer count = info.getDetail().getHourCount().get(hour);
					if (count == null) count = 0;
						
					FeaturesInstance f = new FeaturesInstance(fs, info.getSector().getId().toString(), d, h, count.doubleValue());
					instances.add(f.getInstance(instances));
					bwCsvFile.write(f.getInstanceString(separator) + "\n");
				}			
		
			d = d.plusDays(1);
		}
		
		bwCsvFile.close();		
			
		makeModel(modelName, instances, 4);
	}
	
	private Double calculateCount(Map<Sector, SectorInfo> sectorInfoMap, LocalDateTime hour, Boolean max) {
		Integer maxCount = 0;
		Integer sumCount = 0;
		Integer count = 0;
		
		for (Entry<Sector,SectorInfo> sectorInfo : sectorInfoMap.entrySet()) {
			if (!sectorInfo.getKey().exception(hour.toLocalDate())) {
				Integer lCount = sectorInfo.getValue().getDetail().getHourCount().get(hour);
				if (lCount != null) {
					if (lCount > maxCount) maxCount = lCount;
					sumCount += lCount;
					count++; 
				}
			}
		}
		
		if (max)
			return maxCount.doubleValue();
		else
			return (sumCount.doubleValue()) / (count==0?1:count);
	}

	private void modelForecastTrack(Track track, LocalDate modelTill, Features fs, Boolean max) throws IOException {
		String modelName = getModelName(track, modelTill);

		File csvFile = new File(fileLocation + modelName + (max?"_max":"_avg") + ".csv");
		String separator = ";";
		
		if (!csvFile.exists()) 
			csvFile.createNewFile();

		BufferedWriter bwCsvFile = new BufferedWriter(new FileWriter(csvFile.getAbsoluteFile()));
		bwCsvFile.write(fs.csvHeader() + "\n");

		LocalDate start = LocalDate.now();
		int daysBetween = 0;
		
		Map<Sector, SectorInfo> sectorInfoMap = new HashMap<Sector, SectorInfo>();
		
		for (TrackSector trackSector : track.getSectors()) 
			if (trackSector.getModel()) {
				sectorInfoMap.put(trackSector.getSector(), sectorInfoRepository.findBySectorId(trackSector.getSector().getId()));

				if (trackSector.getSector().getStartDate().isBefore(start))
						start = trackSector.getSector().getStartDate();
		}
		
		daysBetween = (int)ChronoUnit.DAYS.between(start, modelTill);
				
		log.debug("start: "+ start);

		Instances instances = new Instances("track", fs.getAttributes(), daysBetween*24);
		instances.setClassIndex(fs.getAttributes().size() - 1);

		log.debug("track: "+ track.getName());

						
		LocalDate d = start;
	
		while (d.isBefore(modelTill)) {
			for (Integer h = 0; h < 24; h++) {
				LocalDateTime hour = d.atTime(h, 0);

				Double count = calculateCount(sectorInfoMap, hour, max);
				FeaturesInstance f = new FeaturesInstance(fs, track.getId().toString(), d, h, count);
				instances.add(f.getInstance(instances));
				bwCsvFile.write(f.getInstanceString(separator) + "\n");
			}

			d = d.plusDays(1);
		}
			
		bwCsvFile.close();		
			
		makeModel(modelName + (max?"_max":"_avg"), instances, 4);		
	}
	
	private void modelForecastTrack(Track track, LocalDate modelTill, Features fs) throws IOException {
		String modelName = getModelName(track, modelTill);

		File csvFile = new File(fileLocation + modelName + ".csv");
		String separator = ";";
		
		if (!csvFile.exists()) 
			csvFile.createNewFile();

		BufferedWriter bwCsvFile = new BufferedWriter(new FileWriter(csvFile.getAbsoluteFile()));
		bwCsvFile.write(fs.csvHeader() + "\n");

		LocalDate start = LocalDate.now();
		int daysBetween = 0;
		long maxDays = 0;
		
		for (TrackSector trackSector : track.getSectors()) 
			if (trackSector.getModel()) {
				daysBetween += (int)ChronoUnit.DAYS.between(trackSector.getSector().getStartDate(), modelTill);
				if (ChronoUnit.DAYS.between(trackSector.getSector().getStartDate(), modelTill) > maxDays)
					maxDays = ChronoUnit.DAYS.between(trackSector.getSector().getStartDate(), modelTill);
				log.debug("daysBetween: "+ daysBetween);
				if (trackSector.getSector().getStartDate().isBefore(start))
						start = trackSector.getSector().getStartDate();
			}
				
		log.debug("start: "+ start);
		log.debug("daysBetween: "+ daysBetween);
		log.debug("maxDays: "+ maxDays);
		int minNumInstances = (int)((4.0 * daysBetween) / maxDays);
		log.debug("minNumInstances: "+ minNumInstances);
		Instances instances = new Instances("track", fs.getAttributes(), daysBetween*24);
		instances.setClassIndex(fs.getAttributes().size() - 1);

		log.debug("track: "+ track.getName());

		for (TrackSector trackSector : track.getSectors()) {
			if (trackSector.getModel()) {
				Sector sector = trackSector.getSector();
				
				SectorInfo info = sectorInfoRepository.findBySectorId(sector.getId());
								
				LocalDate d = start;
	
				while (d.isBefore(modelTill)) {
					if (!d.isBefore(sector.getStartDate())) {
						if (!sector.exception(d))
							for (Integer h = 0; h < 24; h++) {
								LocalDateTime hour = d.atTime(h, 0);
	
								Integer count = info.getDetail().getHourCount().get(hour);
								if (count == null) count = 0;
									
								FeaturesInstance f = new FeaturesInstance(fs, info.getSector().getId().toString(), d, h, count.doubleValue());
								instances.add(f.getInstance(instances));
								bwCsvFile.write(f.getInstanceString(separator) + "\n");
							}			
					}

					d = d.plusDays(1);
				}
			}
		}
			
		bwCsvFile.close();		
			
		makeModel(modelName, instances, minNumInstances);		
	}
	
	private void makeModel(String modelName, Instances instances, Integer minNumInstances) throws IOException {
		// maak model
		M5P cl = new M5P();
		cl.setMinNumInstances(minNumInstances);
		try {
			cl.buildClassifier(instances);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("Number of instances: " + instances.numInstances());
		log.debug("Number of measures: " + cl.measureNumRules());

		File txtFile = new File(fileLocation + modelName + ".txt");
		if (!txtFile.exists()) {
			txtFile.createNewFile();
		}
		BufferedWriter bwFile = new BufferedWriter(new FileWriter(txtFile.getAbsoluteFile()));

		try {
			Evaluation eval = new Evaluation(instances);
			eval.crossValidateModel(cl, instances, 10, new Random(1));
			
			bwFile.write(eval.toSummaryString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// save model
		

		bwFile.write(cl.toString());
		bwFile.close();
					
		// save model + header
		File modelFile = new File(fileLocation + modelName + ".model");

		try {
			SerializationHelper.write(new FileOutputStream(modelFile), cl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException(e);
		}					
	}
	
	@Override
	public void modelForecast() throws IOException {
		Settings settings = settingsRepository.findByParameter("model");
		
		// load weather
		LocalDate weatherTill = weatherHourlyRepository.findFirstByOrderByYyyymmddDescHhDesc().getDate();
		
		ActivityStatus activityStatus = activityStatusRepository.findLast(RideType.MTB.getId());
		Activity activity = activityRepository.findOne(activityStatus.getId());
		LocalDate aggegratedTill = activity.getStartDateLocal().toLocalDate(); 			
		
		LocalDate modelTill = (weatherTill.isBefore(aggegratedTill)?weatherTill.plusDays(1):aggegratedTill);
		
		HolidayList holidayList = keyRegisterService.getHolidayList();
		
		Features fs = new Features(holidayList, weatherHourlyRepository.findAll(), false);
		
		File path = new File(fileLocation + modelTill.format(DateTimeFormatter.BASIC_ISO_DATE));
        if (!path.exists()) 
            path.mkdir();
  		
		for (Sector sector : sectorRepository.findAll()) {
			modelForecastSector(sector, modelTill, fs);		
		}
		
		for (Track track : trackRepository.findAll()) {
			if (track.getTrackType().equals(TrackType.PERMANENT)) {
				modelForecastTrack(track, modelTill, fs);		
				modelForecastTrack(track, modelTill, fs, false);		
				modelForecastTrack(track, modelTill, fs, true);	
			}
		}
				
		settings.setDateValue(modelTill);
		settingsRepository.save(settings);
	}
	
	@Override
	public VisitorsForecast forcast14DaySector(Sector sector, HolidayList holidayList, WeatherForecast weatherForecast, LocalDate modelDate) throws IOException, DatatypeConfigurationException {
		// read model and header

		M5P m5p = null;
		String model = fileLocation + getModelName(sector, modelDate) + ".model";

		try {
			m5p = (M5P) SerializationHelper.read(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("SerializationHelper.read: " + model, e);
		}

		LocalDate startDate = LocalDate.now();
		VisitorsForecast vfc = new VisitorsForecast(startDate, 14);

		Features fs = new Features(holidayList, weatherForecast);

		Instances instances = new Instances("track", fs.getAttributes(), 1);
		instances.setClassIndex(fs.getAttributes().size() - 1);

		for (int day = 0; day < 14; day++) {
			LocalDate date = startDate.plusDays(day);
			for (Integer hour = 0; hour < 24; hour++) {

				FeaturesInstance features = new FeaturesInstance(fs, "", date, hour);
				
				double forecast = 0;

				try {
					forecast = m5p.classifyInstance(features.getInstance(instances));
				} catch (Exception e) {
					throw new DatatypeConfigurationException("SerializationHelper.read: " + model, e);
				}

				vfc.addForecast(date, hour, forecast, features);
				if (forecast < 0)
					forecast = 0.0;
				long result = Math.round(forecast);
				BigDecimal bd = new BigDecimal(forecast).setScale(1, BigDecimal.ROUND_HALF_UP);

				log.debug("date:" + date + ", hour:" + hour + ", fc:" + forecast + " - " + result + " - " + bd);
			}
		}
		
		return vfc;
	}

	@Override
	public Map<Integer, VisitorsForecast> forcast14DaySectors() throws IOException, DatatypeConfigurationException {
		Map<Integer, VisitorsForecast> forecast = new TreeMap<Integer, VisitorsForecast>();
		
		HolidayList holidayList = keyRegisterService.getHolidayList();
		
		Settings settings = settingsRepository.findByParameter("model");

		WeatherForecast weatherForecast = this.weatherForecastService.buildWeatherForecast();

		for (Sector sector : sectorRepository.findAll()) {
			forecast.put(sector.getId(), forcast14DaySector(sector, holidayList, weatherForecast, settings.getDateValue()));
		}
	
		return forecast;
	}

	private Map<Integer, VisitorsForecast> forcast14DayTracksSector(boolean max) throws IOException, DatatypeConfigurationException {
		Map<Integer, VisitorsForecast> forecastSector = forcast14DaySectors();
		Map<Integer, VisitorsForecast> forecastTrack = new TreeMap<Integer, VisitorsForecast>();
				
		Map<LocalDateTime, Double> map = new HashMap<LocalDateTime, Double>();
		Integer count = 0;
		for (Track track : trackRepository.findAll()) {
			if (track.getTrackType().equals(TrackType.PERMANENT)) {
				for (TrackSector trackSector : track.getSectors()) {
					if (trackSector.getModel()) {
						count++;
						VisitorsForecast vf = forecastSector.get(trackSector.getSector().getId());
						for (VisitorsForecastDay vfd : vf.getVisitorsForecastDays().values())
							for (Entry<Integer,Double> fc : vfd.getHourlyVisitors().entrySet()) {
								LocalDateTime ldt = vfd.getDate().atTime(fc.getKey(), 0);
								if (map.containsKey(ldt)) {
									if (max) {
										if (fc.getValue() > map.get(ldt)) map.put(ldt, fc.getValue());
									} else
										map.put(ldt, map.get(ldt) + fc.getValue());
								} else 
									map.put(ldt, fc.getValue());							
						}
						
					}
				}
			}
		}

		for (Track track : trackRepository.findAll()) {
			if (track.getTrackType().equals(TrackType.PERMANENT)) {
				for (TrackSector trackSector : track.getSectors()) {
					if (trackSector.getModel()) {
						VisitorsForecast vf = new VisitorsForecast(forecastSector.get(trackSector.getSector().getId()));
						for (VisitorsForecastDay vfd : vf.getVisitorsForecastDays().values())
							for (Integer fc : vfd.getHourlyVisitors().keySet()) {
								LocalDateTime ldt = vfd.getDate().atTime(fc, 0);
								if (max)
									vfd.getHourlyVisitors().put(fc, map.get(ldt));
								else 
									vfd.getHourlyVisitors().put(fc, map.get(ldt)/count);
							}
						
						forecastTrack.put(track.getId(), vf);
						break;
					}
				}
			}
		}

		return forecastTrack;
	}

	@Override
	public Map<Integer, VisitorsForecast> forcast14DayTracksSectorAvg() throws IOException, DatatypeConfigurationException {
		return forcast14DayTracksSector(false);
	}

	@Override
	public Map<Integer, VisitorsForecast> forcast14DayTracksSectorMax() throws IOException, DatatypeConfigurationException {
		return forcast14DayTracksSector(true);
	}
	
	
	private Map<Integer, VisitorsForecast> forcast14DayTracksLocal(String model) throws IOException, DatatypeConfigurationException {
		Map<Integer, VisitorsForecast> forecast = new TreeMap<Integer, VisitorsForecast>();
	
		HolidayList holidayList = keyRegisterService.getHolidayList();
	
		Settings settings = settingsRepository.findByParameter("model");

		WeatherForecast weatherForecast = this.weatherForecastService.buildWeatherForecast();

		for (Track track : trackRepository.findAll()) {
//			forecast.put(track.getId(), forcast14DayTrack(track, holidayList, weatherForecast, settings.getDateValue()));
		}

		return forecast;
	}
	
	@Override
	public Map<Integer, VisitorsForecast> forcast14DayTracks() throws IOException, DatatypeConfigurationException {
		Map<Integer, VisitorsForecast> forecastTrack = new TreeMap<Integer, VisitorsForecast>();
		return forecastTrack;
	}
	
	@Override
	public Map<Integer, VisitorsForecast> forcast14DayTracksAvg() throws IOException, DatatypeConfigurationException {
		Map<Integer, VisitorsForecast> forecastTrack = new TreeMap<Integer, VisitorsForecast>();
		return forecastTrack;
	}
	
	@Override
	public Map<Integer, VisitorsForecast> forcast14DayTracksMax() throws IOException, DatatypeConfigurationException {
		Map<Integer, VisitorsForecast> forecastTrack = new TreeMap<Integer, VisitorsForecast>();
		return forecastTrack;
	}

}
