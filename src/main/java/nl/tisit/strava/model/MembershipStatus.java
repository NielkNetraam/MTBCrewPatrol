package nl.tisit.strava.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MembershipStatus {
	PENDING("pending", "Pending"),
	MEMBER("member", "Member");

	private String id;
    private String name;
 
    private MembershipStatus(final String id, final String name) {
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
