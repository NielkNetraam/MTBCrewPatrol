package nl.tisit.mtbcrewpatrol.model;

public enum CheckType {
	DEFAULT(0),
	LINE(1),
	NOT(2);
	
    private Integer id;

    private CheckType(final Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	static public CheckType getResourceStatus(Integer id) {
		switch (id) {
		case 1: return CheckType.LINE;
		case 2: return CheckType.NOT;
		default: return CheckType.DEFAULT;
		}
	}
}
