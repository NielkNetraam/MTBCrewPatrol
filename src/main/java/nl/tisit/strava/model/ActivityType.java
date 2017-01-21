package nl.tisit.strava.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActivityType {
	RIDE("Ride", "Ride"),
	RUN("Run", "Run");

	private String id;
    private String name;
 
    private ActivityType(final String id, final String name) {
		this.id = id;
		this.name = name;
	}

	@JsonValue
	public String getId() {
		return id;
	}

    public String getName() {
        return name;
    }
}
