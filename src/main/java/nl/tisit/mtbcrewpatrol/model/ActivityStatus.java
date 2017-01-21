package nl.tisit.mtbcrewpatrol.model;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "activity_status")
public class ActivityStatus extends BaseStatus {
	private final static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
	
	private Integer athleteId;
	@Lob
	private String info;
	private RideType rideType = RideType.UNKNOWN;

	public ActivityStatus() {
	}

	public ActivityStatus(Integer id, Integer athleteId) {
		super(id);
		this.athleteId = athleteId;
	}

	public Integer getAthleteId() {
		return athleteId;
	}

	public void setAthleteId(Integer athleteId) {
		this.athleteId = athleteId;
	}
	

	public RideType getRideType() {
		return rideType;
	}

	public void setRideType(RideType rideType) {
		this.rideType = rideType;
	}

	public String getInfoRaw() {
		return info;
	}
	
	public ActivityInfo getInfo() {
		try {
			ActivityInfo object =  (info == null) ? null : objectMapper.readValue(info.toString(), ActivityInfo.class);
			return object;
		} catch (IOException ex) {
			// logger.error("Unexpected IOEx decoding json from database: " +
			// dbData);
			return null;
		}
	//	return info;
	}

	public void setInfo(ActivityInfo info) {
		//this.info = info;
		
		try {
			String string = objectMapper.writeValueAsString(info);
			this.info = string;
		} catch (JsonProcessingException ex) {
			this.info = null;
			// or throw an error
		}
	}
	
	
}
