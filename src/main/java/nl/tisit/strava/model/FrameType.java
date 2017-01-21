package nl.tisit.strava.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FrameType {
	MTB(1, "Mtb"),
	CROSS(2, "Cross"),
	ROAD(3, "Road"),
	TIME_TRIAL(4,"Time trial");
	
    private Integer id;
    private String name;
 
    private FrameType(final Integer id, final String name) {
		this.id = id;
		this.name = name;
	}

	@JsonValue
	public Integer getId() {
		return id;
	}

    public String getName() {
        return name;
    }
}
