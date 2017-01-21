package nl.tisit.mtbcrewpatrol;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import nl.tisit.mtbcrewpatrol.model.ActivityStatus;
import nl.tisit.mtbcrewpatrol.model.AthleteStatus;
import nl.tisit.mtbcrewpatrol.model.CallCounter;
import nl.tisit.mtbcrewpatrol.model.CallCounterException;
import nl.tisit.mtbcrewpatrol.model.CrawlStatus;
import nl.tisit.mtbcrewpatrol.model.ResourceStatus;
import nl.tisit.mtbcrewpatrol.model.SegmentEffortStatus;
import nl.tisit.mtbcrewpatrol.model.SegmentStatus;
import nl.tisit.mtbcrewpatrol.repository.ActivityStatusRepository;
import nl.tisit.mtbcrewpatrol.repository.AthleteStatusRepository;
import nl.tisit.mtbcrewpatrol.repository.SegmentEffortStatusRepository;
import nl.tisit.mtbcrewpatrol.repository.SegmentStatusRepository;
import nl.tisit.strava.model.Activity;
import nl.tisit.strava.model.Athlete;
import nl.tisit.strava.model.Club;
import nl.tisit.strava.model.Gear;
import nl.tisit.strava.model.LatLng;
import nl.tisit.strava.model.LeaderBoard;
import nl.tisit.strava.model.LeaderBoardEntry;
import nl.tisit.strava.model.Segment;
import nl.tisit.strava.model.SegmentEffort;
import nl.tisit.strava.repository.ActivityRepository;
import nl.tisit.strava.repository.AthleteRepository;
import nl.tisit.strava.repository.ClubRepository;
import nl.tisit.strava.repository.GearRepository;
import nl.tisit.strava.repository.SegmentEffortRepository;
import nl.tisit.strava.repository.SegmentRepository;
import nl.tisit.strava.service.StravaService;

@Service
public class StravaCrawlerServiceImpl implements StravaCrawlerService {
	@Autowired
	protected StravaService stravaService;
	@Autowired
	protected SegmentRepository segmentRepository;
	@Autowired
	protected ActivityRepository activityRepository;
	@Autowired
	protected AthleteRepository athleteRepository;
	@Autowired
	protected SegmentEffortRepository segmentEffortRepository;
	@Autowired
	protected AthleteStatusRepository athleteStatusRepository;
	@Autowired
	protected ActivityStatusRepository activityStatusRepository;
	@Autowired
	protected SegmentStatusRepository segmentStatusRepository;
	@Autowired
	protected SegmentEffortStatusRepository segmentEffortStatusRepository;
	@Autowired
	protected GearRepository gearRepository;
	@Autowired
	protected ClubRepository clubRepository;
	@Autowired
	protected CallCounterService callCounterService;

	private static final Integer athleteId=6126748;

	private Log log = LogFactory.getLog(StravaCrawlerServiceImpl.class);

	private SegmentStatus findOrCreateSegment(Segment segment) {
		SegmentStatus segmentStatus = segmentStatusRepository.findOne(segment.getId());
		if (segmentStatus == null) {
			segmentStatus = new SegmentStatus(segment.getId());
		}
		
		Segment local = segmentRepository.findOne(segment.getId());
		if (local == null || local.getResourceState() < segment.getResourceState()) {
			local = segmentRepository.save(segment);
		}
		
		if (segmentStatus.getStatus().getId() < local.getResourceState()) {
			segmentStatus.setStatus(ResourceStatus.getResourceStatus(local.getResourceState()));			
			segmentStatus = segmentStatusRepository.save(segmentStatus);
		}
		
		return segmentStatus;
	}
	
	private SegmentEffortStatus findOrcreateSegmentEffortFromActivity(SegmentEffort segmentEffort) {
		SegmentEffortStatus segmentEffortStatus = segmentEffortStatusRepository.findOne(segmentEffort.getId());
		if (segmentEffortStatus == null) {
			segmentEffortStatus = new SegmentEffortStatus(segmentEffort.getId(), 
					segmentEffort.getAthlete().getId(),
					segmentEffort.getActivity().getId(), 
					segmentEffort.getSegment().getId());
		}
		
		SegmentEffort local = segmentEffortRepository.findOne(segmentEffort.getId());
		if (local == null || local.getResourceState() < segmentEffort.getResourceState()) {
			findOrCreateSegment(segmentEffort.getSegment());

			local = segmentEffortRepository.save(segmentEffort);
		}
		
		if (segmentEffortStatus.getStatus().getId() < local.getResourceState()) {
			segmentEffortStatus.setStatus(ResourceStatus.getResourceStatus(segmentEffort.getResourceState()));
			segmentEffortStatus = segmentEffortStatusRepository.save(segmentEffortStatus);	
		}		
		
		return segmentEffortStatus;
	}
	
	private SegmentEffortStatus findOrcreateSegmentEffortFromSegment(SegmentEffort segmentEffort) {
		SegmentEffortStatus segmentEffortStatus = segmentEffortStatusRepository.findOne(segmentEffort.getId());
		if (segmentEffortStatus == null) {
			segmentEffortStatus = new SegmentEffortStatus(segmentEffort.getId(), 
					segmentEffort.getAthlete().getId(),
					segmentEffort.getActivity().getId(), 
					segmentEffort.getSegment().getId());
		}
		
		SegmentEffort local = segmentEffortRepository.findOne(segmentEffort.getId());
		if (local == null || local.getResourceState() < segmentEffort.getResourceState()) {
			findOrCreateAthlete(segmentEffort.getAthlete());
			findOrCreateActivity(segmentEffort.getActivity());

			local = segmentEffortRepository.save(segmentEffort);
		}
		
		if (segmentEffortStatus.getStatus().getId() < local.getResourceState()) {
			segmentEffortStatus.setStatus(ResourceStatus.getResourceStatus(segmentEffort.getResourceState()));
			segmentEffortStatus = segmentEffortStatusRepository.save(segmentEffortStatus);	
		}		
		
		return segmentEffortStatus;
	}
	
	private ActivityStatus findOrCreateActivity(Activity activity) {
		ActivityStatus activityStatus = activityStatusRepository.findOne(activity.getId());
		if (activityStatus == null) {
			activityStatus = new ActivityStatus(activity.getId(), (activity.getAthlete() != null?activity.getAthlete().getId():null));
		}
		
		Activity local = activityRepository.findOne(activity.getId());
		if (local == null || local.getResourceState() < activity.getResourceState()) {
			local = activityRepository.save(activity);
		}
		
		if (activityStatus.getStatus().getId() < local.getResourceState()) {
			activityStatus.setStatus(ResourceStatus.getResourceStatus(local.getResourceState()));			
			activityStatus = activityStatusRepository.save(activityStatus);
		}
		
		return activityStatus;	
	}

	@Transactional
	private AthleteStatus findOrCreateAthlete(Athlete athlete) {
		AthleteStatus athleteStatus = athleteStatusRepository.findOne(athlete.getId());
		if (athleteStatus == null) {
			athleteStatus = new AthleteStatus(athlete.getId());
		}
		
		Athlete local = athleteRepository.findOne(athlete.getId());
		if (local == null || local.getResourceState() < athlete.getResourceState()) {
			if (athlete.getBikes() != null)
				for (Gear gear : athlete.getBikes()) {
					gearRepository.save(gear);
				}
			if (athlete.getShoes() != null)
				for (Gear gear : athlete.getShoes()) {
					gearRepository.save(gear);
				}
			if (athlete.getClubs() != null)
				for (Club club : athlete.getClubs()) {
					clubRepository.save(club);
				}

			local = athleteRepository.save(athlete);
		}
		
		if (athleteStatus.getStatus().getId() < local.getResourceState()) {
			athleteStatus.setStatus(ResourceStatus.getResourceStatus(local.getResourceState()));			
			athleteStatus = athleteStatusRepository.save(athleteStatus);
		}
		
		return athleteStatus;	
	}

	public Integer collectAthletesFromSegment(Integer segmentId) {
		try {
			Segment segment = stravaService.findSegment(segmentId);

			if (segment == null) 
				return 0;

			Segment segmentStored = segmentRepository.findOne(segment.getId());
			if (segmentStored == null)
				segmentStored = segmentRepository.save(segment);

			LeaderBoard leaderBoard = stravaService.findSegmentLeaderBoard(segmentStored.getId());

			log.debug("leaderBoard.getEntries().size: " + leaderBoard.getEntries().size());

			for (LeaderBoardEntry leaderBoardEntry : leaderBoard.getEntries()) {
				Athlete athlete = athleteRepository.findOne(leaderBoardEntry.getAthleteId());
				if (athlete == null) {
					athlete = stravaService.findAthlete(leaderBoardEntry.getAthleteId());
					athlete = athleteRepository.save(athlete);
				}

				Activity activity = activityRepository.findOne(leaderBoardEntry.getActivityId());
				if (activity == null) {
					activity = stravaService.findActivity(leaderBoardEntry.getActivityId());
					activity = activityRepository.save(activity);
				}
			}

			return segment.getAthleteCount();
		}
		catch (CallCounterException e) {
			return 0;
		}
	}

	@Transactional
	public CrawlStatus collectEffortsFromSegment(Integer segmentId, CallCounter pages) {
		CrawlStatus crawlStatus = CrawlStatus.Finished;
		
		SegmentStatus segmentStatus = segmentStatusRepository.findOne(segmentId);

		LocalDateTime lastChecked =	segmentStatus.getLastChecked();
		if (lastChecked != null) lastChecked = lastChecked.minusHours(8);

		Integer page = 1;
		Boolean found = true;
		Boolean retry = true;

		while (found) {
			try {
				List<SegmentEffort> segmentEfforts = stravaService.findSegmentEfforts(segmentId, lastChecked, 200, page);
				if (segmentEfforts == null || segmentEfforts.size() == 0) {
					found = false;
					break;
				}

				log.info("page: " + page + ", segmentEfforts.size: " + segmentEfforts.size());

				for (SegmentEffort segmentEffort : segmentEfforts) {
					findOrcreateSegmentEffortFromSegment(segmentEffort);
					segmentStatus.setLastChecked(segmentEffort.getStartDateLocal());
				}

				if (segmentEfforts.size() < 200)
					break;

				page++;
				retry = true;
			} catch (HttpClientErrorException e) {
				crawlStatus = CrawlStatus.Interrupted;
				break;
			} catch (HttpServerErrorException e) {
				log.error(e);
				if (retry) {
					retry = false;
				} else {
					log.warn("problem with segmenteffors, skip to next");
					crawlStatus = CrawlStatus.Skip;
					break;
				}					
		    } catch (CallCounterException e) {
				crawlStatus = CrawlStatus.Interrupted;
				break;
			}
		}		

		if (crawlStatus == CrawlStatus.Finished) 
			segmentStatus.setLastChecked(LocalDateTime.now());
			
		segmentStatus = segmentStatusRepository.save(segmentStatus);

		pages.setDayCount(page);
		
		return crawlStatus;
	}

	@Transactional
	public Integer collectEffortsFromActivity(Integer activityId) {
		try {
			log.debug("activityId: " + activityId);
			Activity activity = activityRepository.findOne(activityId);
			if (activity == null || activity.getResourceState() == 1) {
				log.debug("1");
				activity = stravaService.findActivity(activityId);
				if (activity == null) 
					return 0;

				log.debug("activity.getName(): " + activity.getName());

				activity = activityRepository.save(activity);
			}
			log.debug("activity.getResourceState(): " + activity.getResourceState().toString());
			log.debug("activity.getSegmentEfforts().size(): " + (activity.getSegmentEfforts() == null?"null":activity.getSegmentEfforts().size()));

			for (SegmentEffort segmentEffort : activity.getSegmentEfforts()) {
				SegmentEffort segmentEffortLocal = segmentEffortRepository.findOne(segmentEffort.getId());
				if (segmentEffortLocal == null) {
					Athlete athlete = athleteRepository.findOne(segmentEffort.getAthlete().getId());
					if (athlete == null) {
						athlete = athleteRepository.save(segmentEffort.getAthlete());
					}

					Segment segment = segmentRepository.findOne(segmentEffort.getSegment().getId());
					if (segment == null) {
						segment = segmentRepository.save(segmentEffort.getSegment());
					}

					segmentEffortLocal = segmentEffortRepository.save(segmentEffort);
				}
			}

			return activity.getSegmentEfforts().size();
		}
		catch (CallCounterException e) {
			return 0;
		}
	}

	@Transactional
	public CrawlStatus collectMyActivities() {
		AthleteStatus athleteStatus = athleteStatusRepository.findOne(athleteId);
		if (athleteStatus== null) {
			try {
				Athlete athlete = stravaService.findAthlete(athleteId);

				athleteStatus = findOrCreateAthlete(athlete);
			} catch (HttpClientErrorException e) {
				return CrawlStatus.Interrupted;
			} catch (CallCounterException e) {
				return CrawlStatus.Interrupted;
			}
		}

		LocalDateTime lastChecked =	athleteStatus.getLastChecked();
		if (lastChecked != null) lastChecked = lastChecked.minusHours(6);

		Integer page = 1;
		Boolean found = true;

		while (found) {
			try {
				List<Activity> activities = stravaService.findAthleteActivities(lastChecked, 200);

				if (activities == null || activities.size() == 0) {
					found = false;
					break;
				}

				for (Activity activity : activities) {
					findOrCreateActivity(activity);

					lastChecked = activity.getStartDateLocal();
					log.debug("lastChecked: "+lastChecked.toString());
				}

				athleteStatus.setLastChecked(lastChecked);
				athleteStatus = athleteStatusRepository.save(athleteStatus);

				page++;
			} catch (HttpClientErrorException e) {
				return CrawlStatus.Interrupted;
			} catch (CallCounterException e) {
				return CrawlStatus.Interrupted;
			}
		}

		athleteStatus.setLastChecked(LocalDateTime.now());
		athleteStatus = athleteStatusRepository.save(athleteStatus);

		return CrawlStatus.Finished;
	}

	@Transactional
	public CrawlStatus collectDetailedActivities() {
		List<ActivityStatus> activitiesStatus = activityStatusRepository.findByAthleteIdAndStatus(athleteId, ResourceStatus.SUMMARY);

		for (ActivityStatus activityStatus : activitiesStatus) {
			try {
				Activity activity = stravaService.findActivity(activityStatus.getId());

				for (SegmentEffort segmentEffort : activity.getSegmentEfforts()) {
					findOrcreateSegmentEffortFromActivity(segmentEffort);
				}

				activity = activityRepository.save(activity);
				activityStatus.setStatus(ResourceStatus.getResourceStatus(activity.getResourceState()));
				activityStatus = activityStatusRepository.save(activityStatus);
			} catch (HttpClientErrorException e) {
				switch (e.getStatusCode()) {
				case NOT_FOUND:
					activityStatus.setStatus(ResourceStatus.UNKNOWN);
					activityStatus = activityStatusRepository.save(activityStatus);	
					break;
				case UNAUTHORIZED:
					activityStatus.setStatus(ResourceStatus.UNKNOWN);
					activityStatus = activityStatusRepository.save(activityStatus);	
					break;
				case FORBIDDEN:
					return CrawlStatus.Interrupted;
				default : 
					log.error(e);
					return CrawlStatus.Interrupted;
				}
			} catch (CallCounterException e) {
				return CrawlStatus.Interrupted;
			}
		}

		return CrawlStatus.Finished;
	}
	
	@Transactional
	public CrawlStatus collectSummaryActivities() {
		List<ActivityStatus> activitiesStatus = activityStatusRepository.findByStatus(ResourceStatus.META);

		if (activitiesStatus != null && activitiesStatus.size() > 0) {
			Boolean retry = true;

			for (ActivityStatus activityStatus : activitiesStatus) {
				try {
					Activity activity = stravaService.findActivity(activityStatus.getId());

					if (activity.getSegmentEfforts() != null) {
						for (SegmentEffort segmentEffort : activity.getSegmentEfforts()) {
							findOrcreateSegmentEffortFromActivity(segmentEffort);
						}
					}

					activity = activityRepository.save(activity);
					activityStatus.setStatus(ResourceStatus.getResourceStatus(activity.getResourceState()));
					activityStatus = activityStatusRepository.save(activityStatus);	
					
					retry = true;
				} catch (HttpClientErrorException e) {
					switch (e.getStatusCode()) {
					case NOT_FOUND:
						activityStatus.setStatus(ResourceStatus.UNKNOWN);
						activityStatus = activityStatusRepository.save(activityStatus);	
						break;
					case UNAUTHORIZED:
						activityStatus.setStatus(ResourceStatus.UNKNOWN);
						activityStatus = activityStatusRepository.save(activityStatus);	
						break;
					case FORBIDDEN:
						return CrawlStatus.Interrupted;
					default : 
						log.error(e);
						return CrawlStatus.Interrupted;
					}
				} catch (HttpServerErrorException e) {
					log.error(e);
					if (retry) {
						retry = false;
					} else {
						return CrawlStatus.Interrupted;
					}
				} catch (CallCounterException e) {
					return CrawlStatus.Interrupted;
				}
			}
			
			return CrawlStatus.Finished;
		} else
			return CrawlStatus.Finished;
	}
	
	@Transactional
	public CrawlStatus collectSummaryAthletes() {
		List<AthleteStatus> athletesStatus = athleteStatusRepository.findByStatus(ResourceStatus.META);

		if (athletesStatus != null && athletesStatus.size() > 0) {
			Boolean retry = true;

			for (AthleteStatus athleteStatus : athletesStatus) {
				try {
					Athlete athlete = stravaService.findAthlete(athleteStatus.getId());

					athlete = athleteRepository.save(athlete);
					athleteStatus.setStatus(ResourceStatus.getResourceStatus(athlete.getResourceState()));
					athleteStatus = athleteStatusRepository.save(athleteStatus);	
					
					retry = true;
				} catch (HttpClientErrorException e) {
					switch (e.getStatusCode()) {
					case NOT_FOUND:
						athleteStatus.setStatus(ResourceStatus.UNKNOWN);
						athleteStatus = athleteStatusRepository.save(athleteStatus);	
						break;
					case UNAUTHORIZED:
						athleteStatus.setStatus(ResourceStatus.UNKNOWN);
						athleteStatus = athleteStatusRepository.save(athleteStatus);	
						break;
					case FORBIDDEN:
						return CrawlStatus.Interrupted;
					default : 
						log.error(e);
						return CrawlStatus.Interrupted;
					}
				} catch (HttpServerErrorException e) {
					log.error(e);
					if (retry) {
						retry = false;
					} else {
						return CrawlStatus.Interrupted;
					}
				} catch (CallCounterException e) {
					return CrawlStatus.Interrupted;
				}
			}
			
			return CrawlStatus.Finished;
		} else
			return CrawlStatus.Finished;
	}
	
	public CrawlStatus collectEffortsFromSegments(Integer maxCount, Path2D.Double path) {
		List<SegmentStatus> statusList = segmentStatusRepository.findByStatusOrderByLastChecked(ResourceStatus.SUMMARY);
		
		int i= 0;

		if (statusList != null) {
			for (SegmentStatus status : statusList) {
				Segment segment = segmentRepository.findOne(status.getId());

				if (path == null || path.contains(segment.getStartLatlng().getLng(), segment.getStartLatlng().getLat())
						|| path.contains(segment.getEndLatlng().getLng(), segment.getEndLatlng().getLat())) {
					log.info(segment.getId() + ": " + segment.getName());
					
					CallCounter pages = new CallCounter(); 
					if (collectEffortsFromSegment(status.getId(), pages) == CrawlStatus.Interrupted)
						return CrawlStatus.Interrupted;
					
					log.debug("pages:" + pages.getDayCount());
					i += pages.getDayCount();
				} else {
					log.debug(segment.getName() + " not in area ("+ segment.getStartLatlng().getLng() + ", " + segment.getStartLatlng().getLat() + ")");
				}

				 if (i >= maxCount)
					 return CrawlStatus.Interrupted;
			}
		}

		return CrawlStatus.Finished;
	}
	
	private CrawlStatus collectSegments(Path2D.Double path, double x) {
		Rectangle2D r = path.getBounds2D();
		
		double south = r.getY();
		double west = r.getX();
		double north = south + r.getHeight();
		double east = west + r.getWidth();
				
		double sc = south;
		
		while (sc < north) {
			double wc = west;

			while (wc < east) {
				try {
					LatLng sw = new LatLng(new Float(sc), new Float(wc));
					LatLng ne = new LatLng(new Float(sc+x), new Float(wc+x));
					LatLng se = new LatLng(new Float(sc), new Float(wc+x));
					LatLng nw = new LatLng(new Float(sc+x), new Float(wc));
					
					if (path.contains(sw.getLng(), sw.getLat())
					  || path.contains(ne.getLng(), ne.getLat())
					  || path.contains(sw.getLng(), se.getLat())
					  || path.contains(sw.getLng(), nw.getLat())) {						
						List<Segment> segments = this.stravaService.findSegments(sw, ne);
						for (Segment segment: segments) {
							findOrCreateSegment(segment);
						}
						
						log.info("IN:" + sw.toString() + " " +ne.toString() + " " + segments.size());
					} else {
						log.info("OUT:" + sw.toString() + " " +ne.toString());			
					}

				} catch (HttpClientErrorException e) {
					switch (e.getStatusCode()) {
					case NOT_FOUND:
						break;
					case UNAUTHORIZED:
						break;
					case FORBIDDEN:
						return CrawlStatus.Interrupted;
					default : 
						log.error(e);
						return CrawlStatus.Interrupted;
					}
				} catch (HttpServerErrorException e) {
					log.error(e);
				} catch (CallCounterException e) {
					callCounterService.waitIfNecessary();
					continue;
				}

				wc += (x/2);
			}
			sc += (x/2);
		}
					
		return CrawlStatus.Finished;		
	}
	
	@Transactional
	public CrawlStatus collectSegments(Path2D.Double path) {
		CrawlStatus status;
		status = collectSegments(path, 0.015);
		if (status == CrawlStatus.Finished)
			status = collectSegments(path, 0.030);
		if (status == CrawlStatus.Finished)
			status = collectSegments(path, 0.05);
		
		return status;
	}
	

}
