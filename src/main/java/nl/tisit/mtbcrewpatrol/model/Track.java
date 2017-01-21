package nl.tisit.mtbcrewpatrol.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "track")
public class Track {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
	private String name;
	private TrackType trackType;
	private String color;
	@Lob
	private String polyline;	
	private Integer effortThreshold;
    
	@OneToMany(mappedBy="track")
	private List<SegmentStatus> segmentStatusses;

	@OneToMany(mappedBy="track", fetch=FetchType.EAGER)
	private Set<TrackSector> sectors;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TrackType getTrackType() {
		return trackType;
	}

	public void setTrackType(TrackType trackType) {
		this.trackType = trackType;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getEffortThreshold() {
		return effortThreshold;
	}

	public void setEffortThreshold(Integer effortThreshold) {
		this.effortThreshold = effortThreshold;
	}

	public String getPolyline() {
		return polyline;
	}

	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}

	public List<SegmentStatus> getSegmentStatusses() {
		return segmentStatusses;
	}

	public void setSegmentStatusses(List<SegmentStatus> segmentStatusses) {
		this.segmentStatusses = segmentStatusses;
	}
	
	public Set<TrackSector> getSectors() {
		return sectors;
	}

	public void setSectors(Set<TrackSector> sectors) {
		this.sectors = sectors;
	}

	@Override
	public String toString() {
		return "Track [id=" + id + ", name=" + name + ", trackType=" + trackType + ", color=" + color + ", polyline="
				+ polyline + "]";
	}
}
