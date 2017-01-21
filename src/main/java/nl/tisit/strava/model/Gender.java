package nl.tisit.strava.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
	MALE("M", "Male"),
	FEMALE("F", "Female");
	 
    private String id;
    private String name;
 
    private Gender(final String id, final String name) {
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
