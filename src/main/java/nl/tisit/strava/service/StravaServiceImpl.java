package nl.tisit.strava.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import nl.tisit.mtbcrewpatrol.CallCounterService;
import nl.tisit.mtbcrewpatrol.StravaCrawlerServiceImpl;
import nl.tisit.mtbcrewpatrol.model.CallCounterException;
import nl.tisit.strava.model.Activity;
import nl.tisit.strava.model.Athlete;
import nl.tisit.strava.model.DateRange;
import nl.tisit.strava.model.LatLng;
import nl.tisit.strava.model.LeaderBoard;
import nl.tisit.strava.model.Segment;
import nl.tisit.strava.model.SegmentEffort;
import nl.tisit.strava.model.Segments;

@Service
public class StravaServiceImpl implements StravaService {
	private static final String accessToken = "0df844853a2253107f7477f4a4086f01fcb3c523";
	private static final String accessTokenParam = "?access_token={accessToken}";
	private static final String strava = "https://www.strava.com/api/v3";
	private static final String stravaAthlete = "/athletes/{athlete}";
	private static final String stravaActivity = "/activities/{activity}";
	private static final String stravaAthleteActivities = "/athlete/activities";
	private static final String stravaSegment = "/segments/{segment}";
	private static final String stravaSegmentLeaderboard = "/segments/{segment}/leaderboard";
	private static final String stravaSegmentsExplore = "/segments/explore";
	private static final String stravaSegmentAllEfforts = "/segments/{segment}/all_efforts";
	private static final String dateRangeParam = "&date_range={dateRange}";
	private static final String contextEntriesParam = "&context_entries={contextEntries}";
	private static final String startDateParam = "&start_date_local={startDateLocal}";
	private static final String endDateParam = "&end_date_local={endDateLocal}";
	private static final String perPageParam = "&per_page={perPage}";
	private static final String pageParam = "&page={page}";
	private static final String pageAfter = "&after={after}";
	private static final String startDateDefault = "2016-01-01T00:00:00Z";
	//	private static final String initialDateDefault = "2000-01-01T00:00:00Z";
	private static final String endDateDefault = "2100-01-01T00:00:00Z";
	private static final Integer perPageDefault=200;
	private static final String boundsParam = "&bounds={south},{west},{north},{east}";

	@Autowired
	protected CallCounterService callCounterService;

	private Log log = LogFactory.getLog(StravaCrawlerServiceImpl.class);

	public Athlete findAthlete(Integer athleteId) throws CallCounterException {	
		if (callCounterService.increaseAllowed()){
			callCounterService.increaseCounter();

			RestTemplate restTemplate = new RestTemplate();

			String url = strava+stravaAthlete+accessTokenParam;
			Athlete athlete = restTemplate.getForObject(url, Athlete.class, athleteId.toString(), accessToken);

			return athlete;
		} else
			throw new CallCounterException();
	}

	public Activity findActivity(Integer activityId) throws CallCounterException {	
		if (callCounterService.increaseAllowed()){
			callCounterService.increaseCounter();

			RestTemplate restTemplate = new RestTemplate();

			String uri = strava+stravaActivity+accessTokenParam;

			UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(uri);
			String url = urlBuilder.buildAndExpand(activityId.toString(), accessToken).toUriString();

			log.debug("url: " + url);

			Activity activity = restTemplate.getForObject(url, Activity.class);
			activity.setZoned2Local();

			return activity;
		} else
			throw new CallCounterException();
	}

	public Segment findSegment(Integer segmentId) throws CallCounterException {	
		if (callCounterService.increaseAllowed()){
			callCounterService.increaseCounter();

			RestTemplate restTemplate = new RestTemplate();

			String url = strava+stravaSegment+accessTokenParam;
			Segment segment = restTemplate.getForObject(url, Segment.class, segmentId.toString(), accessToken);

			return segment;
		} else
			throw new CallCounterException();
	}

	public LeaderBoard findSegmentLeaderBoard(Integer segmentId) throws CallCounterException {
		if (callCounterService.increaseAllowed()){
			callCounterService.increaseCounter();

			RestTemplate restTemplate = new RestTemplate();

			String url = strava+stravaSegmentLeaderboard+accessTokenParam+contextEntriesParam;
			LeaderBoard leaderboard = restTemplate.getForObject(url, LeaderBoard.class, segmentId.toString(), accessToken, 15);

			return leaderboard;
		} else
			throw new CallCounterException();		
	}

	public LeaderBoard findSegmentLeaderBoard(Integer segmentId, DateRange dateRange) throws CallCounterException {
		if (callCounterService.increaseAllowed()){
			callCounterService.increaseCounter();

			RestTemplate restTemplate = new RestTemplate();

			String url = strava+stravaSegmentLeaderboard+accessTokenParam+contextEntriesParam+dateRangeParam;
			LeaderBoard leaderboard = restTemplate.getForObject(url, LeaderBoard.class, segmentId.toString(), accessToken, 15, dateRange.getName());

			return leaderboard;
		} else
			throw new CallCounterException();		
	}

	public List<SegmentEffort> findSegmentEfforts(Integer segmentId, LocalDateTime startDate, Integer perPage, Integer page) throws CallCounterException {
		if (callCounterService.increaseAllowed()){
			callCounterService.increaseCounter();

			RestTemplate restTemplate = new RestTemplate();

			String uri = strava+stravaSegmentAllEfforts+accessTokenParam+startDateParam+endDateParam+perPageParam+pageParam;

			UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(uri);
			String url = urlBuilder.buildAndExpand(segmentId.toString(), accessToken
					, (startDate == null? startDateDefault : startDate.toString()),
					endDateDefault,(perPage==null?perPageDefault:perPage), (page==null?1:page)).toUriString();

			log.debug("url: " + url);

			ResponseEntity<List<SegmentEffort>> response =
					restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<SegmentEffort>>() {
					});
			
			List<SegmentEffort> segmentEfforts = response.getBody();
			for (SegmentEffort segmentEffort: segmentEfforts) segmentEffort.setZoned2Local();

			return segmentEfforts;
		} else
			throw new CallCounterException();
	}

	public List<Activity> findAthleteActivities(LocalDateTime startDate, Integer perPage) throws CallCounterException {
		if (callCounterService.increaseAllowed()){
			callCounterService.increaseCounter();

			RestTemplate restTemplate = new RestTemplate();

			String uri = strava+stravaAthleteActivities+accessTokenParam+pageAfter+perPageParam;

			UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(uri);
			String url = urlBuilder.buildAndExpand(accessToken, (startDate==null?0:startDate.atZone(ZoneId.systemDefault()).toEpochSecond()),(perPage==null?perPageDefault:perPage)).toUriString();

			log.debug("url: " + url);

			ResponseEntity<List<Activity>> response =
					restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Activity>>() {
					});

			for (Activity activity : response.getBody())
				activity.setZoned2Local();
			
			return response.getBody();		
		} else
			throw new CallCounterException();
	}

	public List<Segment> findSegments(LatLng sw, LatLng ne) throws CallCounterException {
		if (callCounterService.increaseAllowed()){
			callCounterService.increaseCounter();

			RestTemplate restTemplate = new RestTemplate();

			String uri = strava+stravaSegmentsExplore+accessTokenParam+boundsParam;

			UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(uri);
			String url = urlBuilder.buildAndExpand(accessToken, sw.getLat(), sw.getLng(), ne.getLat(), ne.getLng()).toUriString();

			log.debug("url: " + url);

			Segments segments = restTemplate.getForObject(url, Segments.class);

			return (segments==null?null:segments.getSegments());
		} else
			throw new CallCounterException();
	}
}
