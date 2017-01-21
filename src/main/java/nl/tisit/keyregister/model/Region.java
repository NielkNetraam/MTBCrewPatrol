package nl.tisit.keyregister.model;

public enum Region {
	ALL(0),
	NORTH(1),
	MIDDLE(2),
	SOUTH(3);
	
	private Integer id;


	private Region(final Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	static public Region getRegion(Integer id) {
		switch (id) {
		case 1: return Region.NORTH;
		case 2: return Region.MIDDLE;
		case 3: return Region.SOUTH;
		default: return Region.ALL;
		}
	}
}

