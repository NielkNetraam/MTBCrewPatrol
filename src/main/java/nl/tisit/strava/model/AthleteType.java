package nl.tisit.strava.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AthleteType {
	CYCLIST(0, "Cyclist"),
	RUNNER(1, "Runner");

    private Integer id;
    private String name;
 
    private AthleteType(final Integer id, final String name) {
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
