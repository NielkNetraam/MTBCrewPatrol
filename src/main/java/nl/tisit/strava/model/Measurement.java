package nl.tisit.strava.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Measurement {
	FEET("feet", "Feet"), 
	METERS("meters", "Meters");
	 
    private String id;
    private String name;
 
    private Measurement(final String id, final String name) {
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
