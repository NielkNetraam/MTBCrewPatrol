package nl.tisit.strava.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FollowingStatus {
	PENDING("pending", "Pending"),
	ACCEPTED("accepted", "Accepted"), 
	BLOCKED("blocked", "Blocked");

	private String id;
    private String name;
 
    private FollowingStatus(final String id, final String name) {
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
