package nl.tisit.mtbcrewpatrol.model;

public enum WildType {
	NOT_WILD(0),
	WILD(1),
	UNKNOWN(2);
	
    private Integer id;

    private WildType(final Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	static public WildType getResourceStatus(Integer id) {
		switch (id) {
		case 0: return WildType.NOT_WILD;
		case 1: return WildType.WILD;
		default: return WildType.UNKNOWN;
		}
	}
}
