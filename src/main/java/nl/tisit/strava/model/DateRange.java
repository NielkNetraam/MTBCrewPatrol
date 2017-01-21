package nl.tisit.strava.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DateRange {
	YEAR("this_year"),
	MONTH("this_month"), 
	WEEK("this_week"),
	TODAY("today");

    private String name;
 
    private DateRange(final String name) {
		this.name = name;
	}

	@JsonValue
    public String getName() {
        return name;
    }
}
