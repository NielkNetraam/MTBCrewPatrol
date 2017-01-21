package nl.tisit.mtbcrewpatrol.etl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;

import nl.tisit.keyregister.model.HolidayList;
import nl.tisit.keyregister.repository.HolidayRepository;
import nl.tisit.keyregister.repository.HolidaysRepository;
import nl.tisit.keyregister.service.KeyRegisterService;
import nl.tisit.mtbcrewpatrol.forecast.Features;
import nl.tisit.mtbcrewpatrol.forecast.FeaturesInstance;
import nl.tisit.mtbcrewpatrol.model.ActivityStatus;
import nl.tisit.mtbcrewpatrol.model.RideType;
import nl.tisit.mtbcrewpatrol.model.Track;
import nl.tisit.mtbcrewpatrol.model.WildRideSegment;
import nl.tisit.mtbcrewpatrol.model.dm.SectorInfo;
import nl.tisit.mtbcrewpatrol.repository.ActivityStatusRepository;
import nl.tisit.mtbcrewpatrol.repository.SectorInfoRepository;
import nl.tisit.mtbcrewpatrol.repository.TrackRepository;
import nl.tisit.mtbcrewpatrol.repository.WildRideRepository;
import nl.tisit.mtbcrewpatrol.repository.WildRideSegmentRepository;
import nl.tisit.strava.model.Activity;
import nl.tisit.strava.repository.ActivityRepository;
import nl.tisit.strava.repository.SegmentRepository;
import nl.tisit.weather.repository.WeatherHourlyRepository;

@Service
public class EtlServiceImpl implements EtlService {
	@Autowired
	protected SegmentRepository segmentRepository;
	@Autowired
	protected TrackRepository trackRepository;
	@Autowired
	protected WildRideRepository wildRideRepository;
	@Autowired
	protected WildRideSegmentRepository wildRideSegmentRepository;
	@Autowired
	protected SectorInfoRepository sectorInfoRepository;
	@Autowired
	protected ActivityStatusRepository activityStatusRepository;
	@Autowired
	protected ActivityRepository activityRepository;
	@Autowired
	protected HolidayRepository holidayRepository;
	@Autowired
	protected HolidaysRepository holidaysRepository;
	@Autowired
	protected WeatherHourlyRepository weatherHourlyRepository;
	@Autowired
	protected KeyRegisterService keyRegisterService;
	
	private Log log = LogFactory.getLog(EtlServiceImpl.class);
	
	@Override
	@Transactional
	public void exportTracks() {
		try {
			File kml = new File("/Users/mklein/Documents/workspace-sts/MTBCrewPatrol/track.kml");

			if (!kml.exists()) 
				kml.createNewFile();

			BufferedWriter bwKml = new BufferedWriter(new FileWriter(kml.getAbsoluteFile()));
			bwKml.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			bwKml.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");

			List<Track> tracks = (List<Track>)trackRepository.findAll();
			for (Track track : tracks) {
				bwKml.write("<Placemark id=\"track"+track.getId()+"\">\n");
				bwKml.write("<name>"+track.getName()+"</name>\n");
				bwKml.write("<description>"+track.getName()+"</description>\n");
				bwKml.write("<ExtendedData>\n");
				bwKml.write("<Data name=\"color\"><value>"+track.getColor()+"</value></Data>\n");
			    bwKml.write("<Data name=\"trackType\"><value>"+track.getTrackType()+"</value></Data>\n");
			    bwKml.write("</ExtendedData>\n");
				bwKml.write("<LineString>\n");
				bwKml.write("<coordinates>\n");
				
				List<LatLng> latLngs = PolylineEncoding.decode(track.getPolyline());
				for (LatLng latLng : latLngs) {
					bwKml.write(String.valueOf(latLng.lng)+","+String.valueOf(latLng.lat)+",0\n");
				}
				
				bwKml.write("</coordinates>\n");
				bwKml.write("</LineString>\n");
				bwKml.write("</Placemark>\n");		
			}
			bwKml.write("</kml>\n");
			bwKml.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public void exportSegments() {
		
	}
	
	@Override
	@Transactional
	public void exportWildRides() {
		try {
			File kml = new File("/Users/mklein/Documents/workspace-sts/MTBCrewPatrol/wildrides.kml");

			if (!kml.exists()) 
				kml.createNewFile();

			BufferedWriter bwKml = new BufferedWriter(new FileWriter(kml.getAbsoluteFile()));
			bwKml.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			bwKml.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");

			List<WildRideSegment> wildrides = (List<WildRideSegment>)wildRideSegmentRepository.findAll();
			for (WildRideSegment wildride : wildrides) {
				bwKml.write("<Placemark id=\"track"+wildride.getId()+"\">\n");
				bwKml.write("<ExtendedData>\n");
			    bwKml.write("<Data name=\"restrictedAreaId\"><value>"+wildride.getWildride().getRestrictedArea().getId()+"</value></Data>\n");
			    bwKml.write("<Data name=\"activityDate\"><value>"+wildride.getWildride().getActivityDate().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"</value></Data>\n");
				bwKml.write("<Data name=\"athleteId\"><value>"+wildride.getWildride().getAthleteId()+"</value></Data>\n");
				bwKml.write("<Data name=\"activityId\"><value>"+wildride.getWildride().getActivityId()+"</value></Data>\n");
			    bwKml.write("<Data name=\"count\"><value>"+wildride.getWildride().getCount()+"</value></Data>\n");
			    bwKml.write("<Data name=\"pointCount\"><value>"+wildride.getWildride().getPointCount()+"</value></Data>\n");
			    bwKml.write("<Data name=\"day\"><value>"+wildride.getWildride().getActivityDate().getDayOfWeek()+"</value></Data>\n");
			    bwKml.write("<Data name=\"hour\"><value>"+wildride.getWildride().getActivityDate().getHour()+"</value></Data>\n");
			    bwKml.write("</ExtendedData>\n");
				bwKml.write("<LineString>\n");
				bwKml.write("<coordinates>\n");
				
				List<LatLng> latLngs = PolylineEncoding.decode(wildride.getPolyline());
				for (LatLng latLng : latLngs) {
					bwKml.write(String.valueOf(latLng.lng)+","+String.valueOf(latLng.lat)+"\n");
				}
				
	

				bwKml.write("</coordinates>\n");
				bwKml.write("</LineString>\n");
				bwKml.write("</Placemark>\n");		

			}
			bwKml.write("</kml>\n");
			bwKml.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	@Transactional
	public void exportMarkers() {
		
	}

	
	@Override
	@Transactional
	public void exportAggregate(ExportFormat format) {
		try {
			File file = null;
			String separator = "";

			switch (format) {
			case CSV: 
				file = new File("/Users/mklein/Documents/workspace-sts/MTBCrewPatrol/SectorAggregate.csv");
				separator = ";";
				break;
			case ARFF:
				file = new File("/Users/mklein/Documents/workspace-sts/MTBCrewPatrol/SectorAggregate.arff");
				separator = ",";
				break;
			}
			
			if (!file.exists()) 
				file.createNewFile();

			HolidayList holidayList = keyRegisterService.getHolidayList();
			
//			Map<LocalDateTime, WeatherHourly> dateWeatherHourlyMap = new HashMap<LocalDateTime, WeatherHourly>();
//			for (WeatherHourly weatherHourly : weatherHourlyRepository.findAll()) 
//				dateWeatherHourlyMap.put(weatherHourly.getDateTime(), weatherHourly);
			
			BufferedWriter bwFile = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			Features fs = new Features(holidayList, weatherHourlyRepository.findAll(), true);

			switch (format) {
			case CSV: 
				bwFile.write(fs.csvHeader() + "\n");
				break;
			case ARFF:
				bwFile.write(fs.arffHeader() + "\n");
				break;
			}
						
			LocalDate start = LocalDate.of(2016, Month.JANUARY, 1);
			log.debug("start: "+ start);
			ActivityStatus activityStatus = activityStatusRepository.findLast(RideType.MTB.getId());
			Activity activity = activityRepository.findOne(activityStatus.getId());
			log.debug("activityStatus: "+ activityStatus);
			
			LocalDate end = activity.getStartDateLocal().toLocalDate(); 			
			
			for (SectorInfo info : sectorInfoRepository.findAll()) {
				for (Map.Entry<LocalDateTime, Integer> hourCount : info.getDetail().getHourCount().entrySet()) {
					log.debug("hourCount: " + hourCount.getKey()+", "+hourCount.getValue());
					break;
				}

				LocalDate d = start;
				while (d.isBefore(end)) {
					for (Integer h = 0; h < 24; h++) {
						LocalDateTime hour = d.atTime(h, 0);
						
						Integer count = info.getDetail().getHourCount().get(hour);
						if (count == null) count = 0;
						
						FeaturesInstance f = new FeaturesInstance(fs, info.getSector().getId().toString(), d, h, count.doubleValue());
						bwFile.write(f.getInstanceString(separator) + "\n");

					}			

					d = d.plusDays(1);
				}
			}
			bwFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
}
