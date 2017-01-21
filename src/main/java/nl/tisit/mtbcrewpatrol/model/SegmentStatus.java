package nl.tisit.mtbcrewpatrol.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "segment_status")
public class SegmentStatus extends BaseStatus {
	private SegmentType segmentType = SegmentType.UNKNOWN;
	private WildType wild = WildType.UNKNOWN;

	@ManyToOne
	private Track track;
	
	public SegmentStatus() {
	}

	public SegmentStatus(Integer id) {
		super(id);
	}

	public SegmentType getSegmentType() {
		return segmentType;
	}

	public void setSegmentType(SegmentType segmentType) {
		this.segmentType = segmentType;
	}

	public WildType getWild() {
		return wild;
	}

	public void setWild(WildType wild) {
		this.wild = wild;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

}
