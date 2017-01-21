package nl.tisit.mtbcrewpatrol;

import java.awt.geom.Path2D;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;

import nl.tisit.mtbcrewpatrol.model.ActivityInfo;
import nl.tisit.mtbcrewpatrol.model.ActivityInfoDetail;
import nl.tisit.mtbcrewpatrol.model.ActivityStatus;
import nl.tisit.mtbcrewpatrol.model.CheckType;
import nl.tisit.mtbcrewpatrol.model.RestrictedArea;
import nl.tisit.mtbcrewpatrol.model.RestrictedAreaException;
import nl.tisit.mtbcrewpatrol.model.RideType;
import nl.tisit.mtbcrewpatrol.model.Sector;
import nl.tisit.mtbcrewpatrol.model.Track;
import nl.tisit.mtbcrewpatrol.model.TrackSector;
import nl.tisit.mtbcrewpatrol.model.WildRide;
import nl.tisit.mtbcrewpatrol.model.WildRideSegment;
import nl.tisit.mtbcrewpatrol.repository.ActivityStatusRepository;
import nl.tisit.mtbcrewpatrol.repository.RestrictedAreaRepository;
import nl.tisit.mtbcrewpatrol.repository.WildRideRepository;
import nl.tisit.mtbcrewpatrol.repository.WildRideSegmentRepository;
import nl.tisit.strava.model.Activity;
import nl.tisit.strava.model.ActivityType;
import nl.tisit.strava.repository.ActivityRepository;
import nl.tisit.util.Geometry;
import nl.tisit.util.Polyline;
import nl.tisit.util.PolylineDetail;

@Service
public class WildRideActivityServiceImpl implements WildRideActivityService {
	@Autowired
	protected RestrictedAreaRepository restrictedAreaRepository;
	@Autowired
	protected ActivityStatusRepository activityStatusRepository;
	@Autowired
	protected ActivityRepository activityRepository;
	@Autowired
	protected WildRideRepository wildRideRepository;
	@Autowired
	protected WildRideSegmentRepository wildRideSegmentRepository;

	private Log log = LogFactory.getLog(WildRideActivityServiceImpl.class);

	private Float addWildRideSement(WildRide wildRide, List<LatLng> segment, Integer count) {
		Float distance = new Float(Geometry.distance(segment));
		String polyline = PolylineEncoding.encode(segment);
		WildRideSegment wildRideSegment = new WildRideSegment();
		wildRideSegment.setWildride(wildRide);
		wildRideSegment.setPolyline(polyline);
		wildRideSegment.setDistance(distance);
		wildRideSegment.setPoints(count);
		wildRide.getSegments().add(wildRideSegment);

		return distance;
	}

	@Override
	@Transactional
	public WildRide checkActivity(ActivityStatus activityStatus, RestrictedArea restrictedArea) {
		Path2D.Double restrictedAreaPath2D = Polyline.polyline2path(restrictedArea.getPolyline());

		return checkActivityInternal(activityStatus, restrictedArea, restrictedAreaPath2D);
	}

	@Override
	@Transactional
	public WildRide checkActivity(ActivityStatus activityStatus, RestrictedArea restrictedArea,
			Path2D.Double restrictedAreaPath2D) {

		WildRide wildride = checkActivityInternal(activityStatus, restrictedArea, restrictedAreaPath2D);

		wildride = wildRideRepository.save(wildride);
		for (WildRideSegment segment : wildride.getSegments()) {
			log.debug("segment:" + segment.getPolyline());
			wildRideSegmentRepository.save(segment);
		}

		return wildride;
	}

	private WildRide checkActivityInternal(ActivityStatus activityStatus, RestrictedArea restrictedArea,
			Path2D.Double restrictedAreaPath2D) {
		Activity activity = activityRepository.findOne(activityStatus.getId());
		
		LocalDateTime startLocalDateTime = activity.getStartDateLocal();
		LocalDate startLocalDate = startLocalDateTime.toLocalDate();
		
		WildRide wildRide = new WildRide();
		wildRide.setActivityId(activity.getId());
		wildRide.setLastChecked(LocalDateTime.now());
		wildRide.setRestrictedArea(restrictedArea);
		wildRide.setActivityDate(startLocalDateTime);
		wildRide.setAthleteId(activity.getAthlete().getId());
		wildRide.setWild(false);
		wildRide.setCount(0);
		wildRide.setPointCount(0);
		wildRide.setDistance(new Float(0));
		wildRide.setSegments(new ArrayList<WildRideSegment>());

		/*
		 * no wildride if: - no summaryPolyline or - trainer = true -
		 * activityType is not Ride - startdate < startdate restricted area -
		 * startdate > enddate restricted area
		 */
		if (startLocalDate.isBefore(restrictedArea.getStartDate())
				|| (restrictedArea.getEndDate() != null
						&& startLocalDate.isAfter(restrictedArea.getEndDate()))
				|| activity.getTrainer() == true
				|| !activity.getActivityType().equalsIgnoreCase(ActivityType.RIDE.getName())
				|| activity.getMap().getSummaryPolyline() == null) {
			return wildRide;
		}

		/*
		 * no wildride if: - startdate in restricted area exception
		 */
		if (restrictedArea.getRestrictedAreaExceptions() != null)
			for (RestrictedAreaException rae : restrictedArea.getRestrictedAreaExceptions()) {
				if (!startLocalDateTime.isBefore(rae.getStartDate())
						&& (rae.getEndDate() == null || !startLocalDateTime.isAfter(rae.getEndDate())))
					return wildRide;
			}

		List<LatLng> latLng = PolylineEncoding.decode(activity.getMap().getSummaryPolyline());
		LatLng previous = null;
		List<LatLng> segment = new ArrayList<LatLng>();

		int count = 0;
		int pointCount = 0;
		int pointsSegment = 0;
		Float distance = new Float(0);
		Boolean foundWildRide = false;

		previous = null;
		for (int i = 0; i < latLng.size(); i++) {
			LatLng current = latLng.get(i);
			if (previous != null && Geometry.distance(previous, current) >= 1000) {
				// GPS failure als punten meer dan 1000 meter uit elkaar liggen
				if (foundWildRide) {
					distance += addWildRideSement(wildRide, segment, pointsSegment);
					pointsSegment = 0;
					segment = new ArrayList<LatLng>();
				}

				previous = current;
				continue;
			}

			if (restrictedAreaPath2D.contains(current.lng, current.lat)) {
				if (!foundWildRide) {
					count++;
					foundWildRide = true;
					if (previous != null) {
						for (LatLng interpolated : Geometry.interpolate(previous, current, 50)) {
							if (restrictedAreaPath2D.contains(interpolated.lng, interpolated.lat)) {
								break;
							} else
								previous = interpolated;
						}
						segment.add(previous);
					}
				}
				segment.add(current);
				pointCount++;
				pointsSegment++;
			} else {
				if (foundWildRide) {
					LatLng toAdd = current;
					for (LatLng interpolated : Geometry.interpolate(previous, current, 50)) {
						if (!restrictedAreaPath2D.contains(interpolated.lng, interpolated.lat)) {
							toAdd = interpolated;
							break;
						}
					}
					segment.add(toAdd);

					distance += addWildRideSement(wildRide, segment, pointsSegment);
					pointsSegment = 0;
					segment = new ArrayList<LatLng>();
				} else {
					if (previous != null) {
						for (LatLng interpolated : Geometry.interpolate(previous, current, 50)) {
							if (foundWildRide) {
								if (!restrictedAreaPath2D.contains(interpolated.lng, interpolated.lat)) {
									segment.add(interpolated);
									count++;
									distance += addWildRideSement(wildRide, segment, pointsSegment);
									pointsSegment = 0;

									segment = new ArrayList<LatLng>();
									foundWildRide = false;
									previous = interpolated;
								}
							} else {
								if (restrictedAreaPath2D.contains(interpolated.lng, interpolated.lat)) {
									foundWildRide = true;
									segment.add(previous);
								} else {
									previous = interpolated;
								}
							}
						}

						if (foundWildRide) {
							segment.add(current);
							count++;
							distance += addWildRideSement(wildRide, segment, pointsSegment);
							pointsSegment = 0;
							segment = new ArrayList<LatLng>();
						}
					}
				}

				foundWildRide = false;
			}

			previous = current;
		}

		if (foundWildRide) {
			distance += addWildRideSement(wildRide, segment, pointsSegment);
		}

		if (count > 0)
			log.debug(activity + (count > 0 ? " = WILD" : " = GOOD"));
		wildRide.setWild(count > 0 ? true : false);
		wildRide.setPointCount(pointCount);
		wildRide.setCount(count);
		wildRide.setDistance(distance);

		// }

		return wildRide;
	}

	private enum PointType {
		TRACK,
		ROAD,
		WILD
	}
	
	private class SectorStart {
		Sector sector;
		LocalDateTime start;
		
		SectorStart(Sector sector, LocalDateTime start) {
			this.sector = sector;
			this.start = start;
		}
	}
	
	private WildRideSegment startWildRideSegment(WildRide wildRide, LatLng current, LatLng previous) {
		WildRideSegment wildRideSegment = new WildRideSegment(wildRide);
		
		if (previous != null) {
			LatLng add = previous;
			Path2D restrictedAreaPath2D = wildRideSegment.getWildride().getRestrictedArea().getPath();
			for (LatLng interpolated : Geometry.interpolate(previous, current, 50)) {
				if (restrictedAreaPath2D.contains(interpolated.lng, interpolated.lat)) {
					break;
				} else
					add = interpolated;
			}
			wildRideSegment.addLatLng(add, false);	
		}

		wildRideSegment.addLatLng(current, true);
		
		return wildRideSegment;
	}
	
	private void endWildRideSegment(WildRideSegment wildRideSegment, LatLng current, LatLng previous) {
		if (current != null) {
			LatLng add = current;
			Path2D restrictedAreaPath2D = wildRideSegment.getWildride().getRestrictedArea().getPath();
			for (LatLng interpolated : Geometry.interpolate(previous, current, 50)) {
				if (!restrictedAreaPath2D.contains(interpolated.lng, interpolated.lat)) {
					add = interpolated;
					break;
				}
			}
			wildRideSegment.addLatLng(add, false);
		}
		
		wildRideSegment.setPolylineAndDistanceFromLatLngs();
		wildRideSegment.getWildride().addSegment(wildRideSegment);
	}
	
	private void addWildRideSegment(WildRide wildRide, LatLng start, LatLng end) {
		WildRideSegment wildRideSegment = new WildRideSegment(wildRide);
		
		wildRideSegment.addLatLng(start, false);	
		wildRideSegment.addLatLng(end, false);	
				
		wildRideSegment.setPolylineAndDistanceFromLatLngs();
		wildRideSegment.getWildride().addSegment(wildRideSegment);
	}
	

	private Sector intersectsStart(LatLng previous, LatLng current, List<Track> tracks) {
		Sector sector = null;
		
		for (Track track : tracks) {
			if ((sector = Sector.intersectsStartList(previous, current, track.getSectors())) != null)
				return sector;
		}
		
		return null;
	}
	
	private SectorStart checkSectors(LatLng current, LatLng previous, SectorStart currentSector, List<Track> tracks, ActivityInfo activityInfo, LocalDateTime start) {
		SectorStart result = currentSector;
		if (previous != null) {
			Sector sector = null;
			if (currentSector != null 
					&& currentSector.sector.intersectsEnd(previous, current)) {
				activityInfo.addSector(currentSector.sector, currentSector.start); 			
				result = null;
			}
			
			if ((sector = intersectsStart(previous, current, tracks)) != null) {
				if (!sector.equals(currentSector) && sector.intersectsEnd(previous, current)) {
					activityInfo.addSector(sector, start); 				
					return null;
				}
				return new SectorStart(sector, start);
			}
		}
		
		return result;
	}
	
	private void checkTracks(List<Track> tracks, ActivityInfo activityInfo) {
		if (activityInfo.getSectorDetail() != null) {	
			for (Track track : tracks) {
				List<LocalDateTime> starts = null;
				LocalDateTime startDate = null;

				Integer count = 999;
				for (TrackSector trackSector : track.getSectors()) {
					Integer lCount;
					ActivityInfoDetail sectorDetail = activityInfo.getSectorDetail().get(trackSector.getSector().getId());
					if (sectorDetail != null && (lCount = sectorDetail.getCount()) != null) {
						if (trackSector.getStart() && (startDate == null || sectorDetail.getStarts().get(0).isBefore(startDate))) {
							starts = sectorDetail.getStarts();
							startDate = sectorDetail.getStarts().get(0);	
						}
						
						if (lCount < count) count = lCount;
					} else {
						count = 0;
						break;
					}	
				}
				
				if (count > 0 && count != 999) {
//					if (count > starts.size())
//						log.error("ERROR: " + activityInfo);
					for (int i = 0; i < count; i++)
						activityInfo.addTrack(track, starts.get(i)); 
				}
			}
		}
	}

	private boolean alwaysMTB(List<Track> tracks, ActivityInfo activityInfo) {
		if (activityInfo.getSectorDetail() != null) {	
			for(Integer id : activityInfo.getSectorDetail().keySet()) {
				for (Track track : tracks) {
					for (TrackSector trackSector : track.getSectors()) {	
						if (trackSector.getSector().getId().equals(id) && trackSector.getSector().getAlwaysMTB())
							return true;
					}
				}
			}
		}
			
		return false;
	}
		
	
	@Override
	@Transactional
	public void classifyActivity(ActivityStatus activityStatus, List<PolylineDetail> trackPolylineDetailList,
			List<PolylineDetail> roadPolylineDetailList, List<RestrictedArea> restrictedAreas, List<Track> tracks, Integer maxDistance) {

		ActivityInfo activityInfo = new ActivityInfo();

		Activity activity = activityRepository.findOne(activityStatus.getId());
		
		LocalDateTime now = LocalDateTime.now();
		
		List<WildRide> wildrides = wildRideRepository.findByActivityId(activityStatus.getId());
		for (WildRide wildRide : wildrides) {
			if (wildRide.getWild()) {
				wildRideSegmentRepository.delete(wildRide.getSegments());
			}
			wildRideRepository.delete(wildRide);
	//		}  else
	//			wildRideMap.put(wildRide.getRestrictedArea().getId(), wildRide);
		}
		
		if (activity.getActivityType() == null)
			activityStatus.setRideType(RideType.PRIVATE);
		else if (!activity.getActivityType().equalsIgnoreCase(ActivityType.RIDE.getName()))
			activityStatus.setRideType(RideType.OTHER);
		else if (activity.getTrainer())
			activityStatus.setRideType(RideType.TRAINER);
		else if (activity.getMap() == null || activity.getMap().getSummaryPolyline() == null)
			activityStatus.setRideType(RideType.GPSFAILURE);
		else if (activity.getAthlete() == null || activity.getStartDateLocal().isAfter(now) || activity.getDistance() < 500 || activity.getMovingTime() > 50000)
			activityStatus.setRideType(RideType.ERROR);
		else {
			List<LatLng> activityLatLngs = PolylineEncoding.decode(activity.getMap().getSummaryPolyline());

			if (Geometry.maxDistance(activityLatLngs) > 10000)
				activityStatus.setRideType(RideType.GPSFAILURE);
			else {
				Map<Integer, WildRide> wildRideMap = new HashMap<Integer,WildRide>();

					
				for (RestrictedArea restrictedArea : restrictedAreas) 
					if (!wildRideMap.containsKey(restrictedArea.getId()))
						wildRideMap.put(restrictedArea.getId(), new WildRide(restrictedArea, activity));
				
				WildRideSegment wildRideSegment = null;

				activityInfo.setTotalPoints(activityLatLngs.size());
				activityInfo.setTotalDistance(new Float(Geometry.distance(activityLatLngs)));

				boolean trackFound = false;
				PointType currentPointType = null;
				PointType previousPointType = null;
				double startDistance = 0;
				int trackPoint = 0;
				double trackDistance = 0;
				int startPoint = 0;
				double endDistance = 0;
				int endPoint = 0;
				LatLng previous = null;
				int wildPoint = 0;
				int roadPoint = 0;
				RestrictedArea ra = null;
				SectorStart currentSector = null;
					
	//			Date activityDate = Date.from(activity.getStartDateLocal().toInstant());
				LocalDateTime activityDatelocal = activity.getStartDateLocal();
				
				LocalDateTime latLngStart = null;
				double stepTime = 0;
				
				if (activityLatLngs.size() <= 1)
					stepTime = 0;
				else {
					double distance = 0;
					LatLng p = null;
					for (LatLng latLng : activityLatLngs) {
						if (p != null)
							distance += Geometry.distance(p, latLng);
							
						p = latLng;	
					}
						
					if(activity.getMovingTime() > 0 && (activity.getElapsedTime()-activity.getMovingTime()) > 3600 && (activity.getElapsedTime()/activity.getMovingTime()) > 2) 
						stepTime = (activity.getMovingTime().doubleValue() / distance);
					else
						stepTime = (activity.getElapsedTime().doubleValue() / distance);
				}
				
				for (LatLng latLng : activityLatLngs) {
					if (latLngStart == null)
						latLngStart = activityDatelocal;
					else 
						latLngStart = latLngStart.plusSeconds((int)(stepTime*Geometry.distance(previous, latLng)));
					
					double distance = (previous != null) ? Geometry.distance(previous, latLng) : 0;

					if (PolylineDetail.onLineList(latLng, trackPolylineDetailList, maxDistance, activityDatelocal)) {
						if (PointType.ROAD.equals(previousPointType) && PolylineDetail.onLineList(latLng, roadPolylineDetailList, maxDistance, activityDatelocal)) {
							currentPointType = PointType.ROAD;
							roadPoint++;
						}
						else {
							currentPointType = PointType.TRACK;
							trackPoint++;
						}
					} else if ((ra = RestrictedArea.inAreas(latLng, restrictedAreas, activityDatelocal)) != null) {
						if (PolylineDetail.onLineList(latLng, roadPolylineDetailList, maxDistance, activityDatelocal)) {
							currentPointType = PointType.ROAD;
							roadPoint++;
						}
						else {
							currentPointType = PointType.WILD;
							wildPoint++;
						}
					} else {
						currentPointType = PointType.ROAD;
						roadPoint++;
					}

					if (PointType.WILD.equals(currentPointType)) {
						if (wildRideSegment == null) {
							wildRideSegment = startWildRideSegment(wildRideMap.get(ra.getId()), latLng, previous);
						} else {
							if (wildRideSegment.getWildride().getRestrictedArea().equals(ra)) {
								wildRideSegment.addLatLng(latLng, true);
							} else {
								endWildRideSegment(wildRideSegment, latLng, previous);
								wildRideSegment = startWildRideSegment(wildRideMap.get(ra.getId()), latLng, previous);								
							}
						}
					} else {
						if (wildRideSegment != null) {
							endWildRideSegment(wildRideSegment, latLng, previous);
							wildRideSegment = null;
						} else {
							if (previous != null && Geometry.distance(previous, latLng) < 1000) {
								LatLng start = previous;
								LatLng end = latLng;
								boolean found = false;
								RestrictedArea raFound = null;
								for (LatLng interpolated : Geometry.interpolate(previous, latLng, 50)) {
									if ((ra = RestrictedArea.inAreas(interpolated, restrictedAreas, activityDatelocal)) != null &&
											!PolylineDetail.onLineList(interpolated, roadPolylineDetailList, maxDistance, activityDatelocal)) {
										if (!found) {
											found = true;
											raFound = ra;
										}
										
									} else {
										if (found) {
											end = interpolated;
											break;
										} else 
											start = interpolated;
									}
								}
								if (found && (raFound.getCheckType().equals(CheckType.DEFAULT) || raFound.intersects(start, end)))
									addWildRideSegment(wildRideMap.get(raFound.getId()), start, end);
							}
						}
					} 
					
					if (PointType.TRACK.equals(currentPointType)) {
//						log.debug(activityStatus.getId() + " - onTrack:" + latLng.lat + ", " + latLng.lng);
						if (!trackFound)
							startDistance += distance;
						trackFound = true;
						endDistance = 0;
						endPoint = 0;

						if (PointType.TRACK.equals(previousPointType))
							trackDistance += distance;
						
					} else {
						if (trackFound) {
							endPoint++;
							endDistance += distance;
						} else {
							startPoint++;
							startDistance += distance;
						}
					}
					currentSector = checkSectors(latLng, previous, currentSector, tracks, activityInfo, latLngStart);

					previous = latLng;
					previousPointType = currentPointType;
				}
				
				if (wildRideSegment != null)
					endWildRideSegment(wildRideSegment, null, previous);
					
				if (trackPoint == 1 && trackDistance == 0 && startPoint == 0) { // road trip started on track/road point 
					trackPoint = 0;
					roadPoint++;
					trackFound = false;
				}
				
				activityInfo.setTrackDistance(new Float(trackDistance));
				activityInfo.setTrackPoints(trackPoint);

				if (trackFound) {
					activityInfo.setStartMTBDistance(new Float(startDistance));
					activityInfo.setStartMTBPoints(startPoint);
					activityInfo.setStopMTBDistance(new Float(endDistance));
					activityInfo.setStopMTBPoints(endPoint);
				}

				Float wildDistance = new Float(0);
				for (WildRide wildRide : wildRideMap.values()) {
					if (wildRide.getWild()) {
						wildDistance += wildRide.getDistance();
					}
				}

				// log.debug("activity: " + activityStatus.getId());
				// log.debug("wildDistance: " + wildDistance.toString());
				// log.debug("totalDistance: " +
				// activityInfo.getTotalDistance().toString());

				activityInfo.setWildDistance(wildDistance);
				activityInfo.setWildPoints(wildPoint);

				activityInfo.setRoadDistance(activityInfo.getTotalDistance() - activityInfo.getTrackDistance() - wildDistance);
				activityInfo.setRoadPoints(roadPoint);


				if (activityInfo.getRoadPoints().equals(activityInfo.getTotalPoints()))
					activityStatus.setRideType(RideType.ROAD);
				else if (activityInfo.getTrackPoints().equals(activityInfo.getTotalPoints()))
					activityStatus.setRideType(RideType.MTB);
				else if ((activityInfo.getTrackDistance()+activityInfo.getWildDistance()) >= 4000)
					activityStatus.setRideType(RideType.MTB);
				else if (activityInfo.getSectorDetail().size() >= 3)
					activityStatus.setRideType(RideType.MTB);
				else if (activityInfo.getSectorDetail().size() >= 1 && alwaysMTB(tracks, activityInfo))
					activityStatus.setRideType(RideType.MTB);
	 			else if ((activityInfo.getTrackDistance().equals(new Float(0)) && activityInfo.getWildDistance().equals(new Float(0))) ||
	 			         (activityInfo.getTrackDistance().equals(new Float(0)) && activityInfo.getWildDistance() < 500 && activityInfo.getRoadDistance() >= 50000) ||
	 			         (activityInfo.getSectorDetail().size() >= 1 && !alwaysMTB(tracks, activityInfo))		    
	 			        ) {
	 				activityStatus.setRideType(RideType.ROAD);
					activityInfo.setTrackPoints(0);
					activityInfo.setWildPoints(0);
					activityInfo.setRoadPoints(activityInfo.getTotalPoints());
					activityInfo.setStartMTBDistance(null);
					activityInfo.setStartMTBPoints(null);
					activityInfo.setStopMTBDistance(null);
					activityInfo.setStopMTBPoints(null);
	 			} else 
	 				activityStatus.setRideType(RideType.MTB);

				
				if (activityStatus.getRideType().equals(RideType.ROAD)) {
					activityInfo.setSectorDetail(null); //new HashMap<Integer, Integer>()) ;
					activityInfo.setTrackDetail(null); //new HashMap<Integer, Integer>());
			
					if (activityInfo.getWildPoints().equals(0) && activityInfo.getWildDistance() > 0) {
						for (WildRide wildRide : wildRideMap.values()) {
							if (wildRide.getWild()) {
								wildRide.setWild(false);
								wildRide.setDistance(new Float(0));
								wildRide.setSegments(new ArrayList<WildRideSegment>());
							}
						}
						activityInfo.setRoadDistance(activityInfo.getTotalDistance()-activityInfo.getTrackDistance());
						activityInfo.setWildDistance(new Float(0));
					}
				}
				
				if (activityInfo.getTotalDistance() > 0) {
					activityInfo
							.setWildDistancePercentage(new BigDecimal(activityInfo.getWildDistance() / activityInfo.getTotalDistance())
									.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
					activityInfo.setTrackDistancePercentage(
							new BigDecimal(activityInfo.getTrackDistance() / activityInfo.getTotalDistance())
									.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
					activityInfo.setRoadDistancePercentage(new Float(1.0) - activityInfo.getTrackDistancePercentage()
							- activityInfo.getWildDistancePercentage());
				}

				if (activityInfo.getTotalPoints() > 0) {
					activityInfo.setWildPointsPercentage(
							new BigDecimal((new Float(activityInfo.getWildPoints()) / activityInfo.getTotalPoints()))
									.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
					activityInfo.setTrackPointsPercentage(
							new BigDecimal((new Float(activityInfo.getTrackPoints()) / activityInfo.getTotalPoints()))
									.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
					activityInfo.setRoadPointsPercentage(new Float(1.0) - activityInfo.getTrackPointsPercentage()
							- activityInfo.getWildPointsPercentage());
				}

				checkTracks(tracks, activityInfo);
				activityStatus.setInfo(activityInfo);
				
//				log.debug("activityInfo: " + activityInfo.toString());
//				log.debug("activityInfo: " + activityStatus.getInfoRaw());
//				for (WildRide wildRide : wildRideMap.values()) {
//					if (wildRide.getWild()) {
//						log.debug("wildRide: " + wildRide.toString());
//						for (WildRideSegment segment : wildRide.getSegments())
//							log.debug("wildRideSegment: " + segment.toString());
//					}
//				}

				wildRideRepository.save(wildRideMap.values());
				for (WildRide wildRide : wildRideMap.values())
					if (wildRide.getWild() && wildRide.getSegments() != null && wildRide.getSegments().size() > 0)
						wildRideSegmentRepository.save(wildRide.getSegments());
			}
		}
		
		activityStatusRepository.save(activityStatus);
	}

}
