package nl.tisit.mtbcrewpatrol;

import nl.tisit.mtbcrewpatrol.model.CallCounter;

public interface CallCounterService {
	public CallCounter findCounter();
	public void increaseCounter();
	public Boolean increaseAllowed();
	public void waitIfNecessary();
}
