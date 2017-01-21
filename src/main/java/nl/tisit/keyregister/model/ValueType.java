package nl.tisit.keyregister.model;

public enum ValueType {
	STRING(0),
	NUMERIC(1),
	DATE(2);
	
    private Integer id;

    private ValueType(final Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	static public ValueType getResourceStatus(Integer id) {
		switch (id) {
		case 1: return ValueType.NUMERIC;
		case 2: return ValueType.DATE;
		default: return ValueType.STRING;
		}
	}
}
