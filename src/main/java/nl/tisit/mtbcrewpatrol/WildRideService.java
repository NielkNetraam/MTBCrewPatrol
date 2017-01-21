package nl.tisit.mtbcrewpatrol;

import java.time.LocalDate;

public interface WildRideService {
	public void checkWildRides();
	public void classifyActivities();
	public LocalDate aggregateActivity();
}
