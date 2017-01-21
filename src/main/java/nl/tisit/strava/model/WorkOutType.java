package nl.tisit.strava.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkOutType {
	RUN_DEFAULT  ( 0, "default"),
	RUN_RACE     ( 1, "race"),
	RUN_LONG_RUN ( 2, "long run"),
	RUN_WORKOUT  ( 3, "workout"),
	NOT_USED4    ( 4, "not used"),
	NOT_USED5    ( 5, "not used"),
	NOT_USED6    ( 6, "not used"),
	NOT_USED7    ( 7, "not used"),
	NOT_USED8    ( 8, "not used"),
	NOT_USED9    ( 9, "not used"),
	RIDE_DEFAULT (10, "default"),
	RIDE_RACE    (11, "race"),
	RIDE_WORKOUT (12, "workout");

    private Integer id;
    private String name;
 
    private WorkOutType(final Integer id, final String name) {
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
