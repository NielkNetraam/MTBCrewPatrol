package nl.tisit.mtbcrewpatrol.model;

public enum TrackType {
	PERMANENT(0),
	CROSS_CUT(1),
	DETOUR(2),
	CONNECTION(3);
	
    private Integer id;

    private TrackType(final Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	static public TrackType getResourceStatus(Integer id) {
		switch (id) {
		case 1: return TrackType.CROSS_CUT;
		case 2: return TrackType.DETOUR;
		case 3: return TrackType.CONNECTION;
		default: return TrackType.PERMANENT;
		}
	}
}
