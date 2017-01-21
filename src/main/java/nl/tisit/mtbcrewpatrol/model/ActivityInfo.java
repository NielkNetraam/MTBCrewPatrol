package nl.tisit.mtbcrewpatrol.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ActivityInfo {

	private Float totalDistance;
	private Float trackDistance;
	private Float wildDistance;
	private Float roadDistance;
	private Integer totalPoints;
	private Integer trackPoints;
	private Integer wildPoints;
	private Integer roadPoints;
	private Integer startMTBPoints;
	private Float startMTBDistance;
	private Integer stopMTBPoints;
	private Float stopMTBDistance;
	private Float trackDistancePercentage;
	private Float wildDistancePercentage;
	private Float roadDistancePercentage;
	private Float trackPointsPercentage;
	private Float wildPointsPercentage;
	private Float roadPointsPercentage;

	private Map<Integer, ActivityInfoDetail> trackDetail;
	private Map<Integer, ActivityInfoDetail> sectorDetail;

	public ActivityInfo() {
		super();
		trackDetail = new HashMap<Integer, ActivityInfoDetail>();
		sectorDetail = new HashMap<Integer, ActivityInfoDetail>();
	}

	public void addSector(Sector sector, LocalDateTime start) {
		if (sectorDetail == null)
			sectorDetail = new HashMap<Integer, ActivityInfoDetail>();

		ActivityInfoDetail detail = sectorDetail.get(sector.getId());
		if (detail == null) {
			detail = new ActivityInfoDetail();
			sectorDetail.put(sector.getId(), detail);
		}

		// Date date =
		// Date.from(start.atZone(ZoneId.systemDefault()).toInstant());

		detail.add(start);
	}

	public void addTrack(Track track, LocalDateTime start) {
		if (trackDetail == null)
			trackDetail = new HashMap<Integer, ActivityInfoDetail>();

		ActivityInfoDetail detail = trackDetail.get(track.getId());
		if (detail == null) {
			detail = new ActivityInfoDetail();
			trackDetail.put(track.getId(), detail);
		}

		detail.add(start);
	}

	public Float getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Float totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Float getTrackDistance() {
		return trackDistance;
	}

	public void setTrackDistance(Float trackDistance) {
		this.trackDistance = trackDistance;
	}

	public Float getWildDistance() {
		return wildDistance;
	}

	public void setWildDistance(Float wildDistance) {
		this.wildDistance = wildDistance;
	}

	public Float getRoadDistance() {
		return roadDistance;
	}

	public void setRoadDistance(Float roadDistance) {
		this.roadDistance = roadDistance;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Integer getTrackPoints() {
		return trackPoints;
	}

	public void setTrackPoints(Integer trackPoints) {
		this.trackPoints = trackPoints;
	}

	public Integer getWildPoints() {
		return wildPoints;
	}

	public void setWildPoints(Integer wildPoints) {
		this.wildPoints = wildPoints;
	}

	public Integer getRoadPoints() {
		return roadPoints;
	}

	public void setRoadPoints(Integer roadPoints) {
		this.roadPoints = roadPoints;
	}

	public Integer getStartMTBPoints() {
		return startMTBPoints;
	}

	public void setStartMTBPoints(Integer startMTBPoints) {
		this.startMTBPoints = startMTBPoints;
	}

	public Float getStartMTBDistance() {
		return startMTBDistance;
	}

	public void setStartMTBDistance(Float startMTBDistance) {
		this.startMTBDistance = startMTBDistance;
	}

	public Integer getStopMTBPoints() {
		return stopMTBPoints;
	}

	public void setStopMTBPoints(Integer stopMTBPoints) {
		this.stopMTBPoints = stopMTBPoints;
	}

	public Float getStopMTBDistance() {
		return stopMTBDistance;
	}

	public void setStopMTBDistance(Float stopMTBDistance) {
		this.stopMTBDistance = stopMTBDistance;
	}

	public Float getTrackDistancePercentage() {
		return trackDistancePercentage;
	}

	public void setTrackDistancePercentage(Float trackDistancePercentage) {
		this.trackDistancePercentage = trackDistancePercentage;
	}

	public Float getWildDistancePercentage() {
		return wildDistancePercentage;
	}

	public void setWildDistancePercentage(Float wildDistancePercentage) {
		this.wildDistancePercentage = wildDistancePercentage;
	}

	public Float getRoadDistancePercentage() {
		return roadDistancePercentage;
	}

	public void setRoadDistancePercentage(Float roadDistancePercentage) {
		this.roadDistancePercentage = roadDistancePercentage;
	}

	public Float getTrackPointsPercentage() {
		return trackPointsPercentage;
	}

	public void setTrackPointsPercentage(Float trackPointsPercentage) {
		this.trackPointsPercentage = trackPointsPercentage;
	}

	public Float getWildPointsPercentage() {
		return wildPointsPercentage;
	}

	public void setWildPointsPercentage(Float wildPointsPercentage) {
		this.wildPointsPercentage = wildPointsPercentage;
	}

	public Float getRoadPointsPercentage() {
		return roadPointsPercentage;
	}

	public void setRoadPointsPercentage(Float roadPointsPercentage) {
		this.roadPointsPercentage = roadPointsPercentage;
	}

	public Map<Integer, ActivityInfoDetail> getTrackDetail() {
		return trackDetail;
	}

	public void setTrackDetail(Map<Integer, ActivityInfoDetail> trackDetail) {
		this.trackDetail = trackDetail;
	}

	public Map<Integer, ActivityInfoDetail> getSectorDetail() {
		return sectorDetail;
	}

	public void setSectorDetail(Map<Integer, ActivityInfoDetail> sectorDetail) {
		this.sectorDetail = sectorDetail;
	}

	@Override
	public String toString() {
		return "ActivityInfo [totalDistance=" + totalDistance + ", trackDistance=" + trackDistance + ", wildDistance="
				+ wildDistance + ", roadDistance=" + roadDistance + ", totalPoints=" + totalPoints + ", trackPoints="
				+ trackPoints + ", wildPoints=" + wildPoints + ", roadPoints=" + roadPoints + ", startMTBPoints="
				+ startMTBPoints + ", startMTBDistance=" + startMTBDistance + ", stopMTBPoints=" + stopMTBPoints
				+ ", stopMTBDistance=" + stopMTBDistance + ", trackDistancePercentage=" + trackDistancePercentage
				+ ", wildDistancePercentage=" + wildDistancePercentage + ", roadDistancePercentage="
				+ roadDistancePercentage + ", trackPointsPercentage=" + trackPointsPercentage
				+ ", wildPointsPercentage=" + wildPointsPercentage + ", roadPointsPercentage=" + roadPointsPercentage
				+ ", trackDetail=" + trackDetail + ", sectorDetail=" + sectorDetail + "]";
	}

}
