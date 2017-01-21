package nl.tisit.mtbcrewpatrol.model;

public enum RideType {
	UNKNOWN(0),
	MTB(1),
	ROAD(2),
	OTHER(3),
	TRAINER(4),
	GPSFAILURE(5),
	PRIVATE(6),
	ERROR(7);
	
    private Integer id;

    private RideType(final Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	static public RideType getResourceStatus(Integer id) {
		switch (id) {
		case 1: return RideType.MTB;
		case 2: return RideType.ROAD;
		case 3: return RideType.OTHER;
		case 4: return RideType.TRAINER;
		case 5: return RideType.GPSFAILURE;
		case 6: return RideType.PRIVATE;
		case 7: return RideType.ERROR;
		default: return RideType.UNKNOWN;
		}
	}
}
