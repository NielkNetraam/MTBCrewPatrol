package nl.tisit.mtbcrewpatrol.model.dm;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import nl.tisit.mtbcrewpatrol.model.ActivityInfoDetail;
import nl.tisit.mtbcrewpatrol.model.Track;
import nl.tisit.strava.model.Activity;

@Entity
@Table(name = "track_info")
public class TrackInfo {
	private final static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
	

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

	@ManyToOne
	Track track;
	
	@Lob
	private String info;

	@Transient
	private TrackInfoDetail detail;
	
	public TrackInfo() {
	}	

	public TrackInfo(Track track) {
		super();
		this.track = track;
		this.detail = new TrackInfoDetail(track);
	}	
	
	public void addActivity(Activity activity, ActivityInfoDetail activityInfoDetail) {		
		getDetail().addActivity(activity, activityInfoDetail);
	}

	public static ObjectMapper getObjectmapper() {
		return objectMapper;
	}

	public Integer getId() {
		return id;
	}

	public String getRawDetail() {
		return info;
	}

	public Track getTrack() {
		return track;
	}

	public TrackInfoDetail getDetail() {
		if (detail == null) {
			try {
				detail =  (info == null) ? null : objectMapper.readValue(info.toString(), TrackInfoDetail.class);
			} catch (IOException ex) {
				// logger.error("Unexpected IOEx decoding json from database: " +
				// dbData);
				return null;
			}
		}
		return detail;
	}

	public void persist() {
		try {
			String string = objectMapper.writeValueAsString(detail);
			this.info = string;
		} catch (JsonProcessingException ex) {
			this.info = null;
			// or throw an error
		}
	}
}
