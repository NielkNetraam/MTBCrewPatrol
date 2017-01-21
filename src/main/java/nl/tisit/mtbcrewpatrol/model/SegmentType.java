package nl.tisit.mtbcrewpatrol.model;

public enum SegmentType {
	UNKNOWN(0),
	MTB(1),
	RACE(2);
	
    private Integer id;

 
    private SegmentType(final Integer id) {	
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	static public SegmentType getResourceStatus(Integer id) {
		switch (id) {
		case 1: return SegmentType.MTB;
		case 2: return SegmentType.RACE;
		default: return SegmentType.UNKNOWN;
		}
	}
}
