package nl.tisit.mtbcrewpatrol.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "track_sector")
public class TrackSector {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Track track;
    @ManyToOne
    private Sector sector;
	private Boolean start;
	private Boolean model;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Boolean getStart() {
		return start;
	}

	public void setStart(Boolean start) {
		this.start = start;
	}

	public Boolean getModel() {
		return model;
	}

	public void setModel(Boolean model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "TrackSector [id=" + id + ", track=" + track + ", sector=" + sector + ", start=" + start + ", model="
				+ model + "]";
	}

}
