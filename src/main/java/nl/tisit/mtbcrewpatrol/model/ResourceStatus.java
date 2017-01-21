package nl.tisit.mtbcrewpatrol.model;

public enum ResourceStatus {
	UNKNOWN(0),
	META(1),
	SUMMARY(2),
	DETAILED(3);
	
    private Integer id;

 
    private ResourceStatus(final Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	static public ResourceStatus getResourceStatus(Integer id) {
		switch (id) {
		case 1: return ResourceStatus.META;
		case 2: return ResourceStatus.SUMMARY;
		case 3: return ResourceStatus.DETAILED;
		default: return ResourceStatus.UNKNOWN;
		}
	}
}
