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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import nl.tisit.mtbcrewpatrol.model.ActivityInfoDetail;
import nl.tisit.mtbcrewpatrol.model.Sector;
import nl.tisit.strava.model.Activity;

@Entity
@Table(name = "sector_info")
public class SectorInfo {
	private final static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
	
	@Transient
	private Log log = LogFactory.getLog(SectorInfo.class);
	
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

	@ManyToOne
	Sector sector;
	
	@Lob
	private String info;

	@Transient
	private SectorInfoDetail detail;
	
	public SectorInfo() {
	}	

	public SectorInfo(Sector sector) {
		super();
		this.sector = sector;
		this.detail = new SectorInfoDetail(sector);
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

	public Sector getSector() {
		return sector;
	}

	public SectorInfoDetail getDetail() {
		if (detail == null) {
			try {
				detail =  (info == null) ? null : objectMapper.readValue(info.toString(), SectorInfoDetail.class);
			} catch (IOException ex) {
				log.error("Unexpected IOEx decoding json from database: " + ex);
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
			log.error("Unexpected IOEx decoding json from database: " + ex);
			this.info = null;
		}
	}
}
