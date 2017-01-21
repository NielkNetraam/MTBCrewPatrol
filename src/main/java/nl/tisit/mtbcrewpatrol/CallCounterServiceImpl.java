package nl.tisit.mtbcrewpatrol;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import nl.tisit.mtbcrewpatrol.model.CallCounter;

@Service
public class CallCounterServiceImpl implements CallCounterService {
	private final static Integer maxDayCount = 30000;
	private final static Integer maxQuarterCount = 600;

	final Lock lock = new ReentrantLock();
	final Condition condition  = lock.newCondition(); 
	   
	private Log log = LogFactory.getLog(StravaCrawlerServiceImpl.class);

	private CallCounter callCounter = new CallCounter();

	private LocalDateTime end15Minute() { 
		if (callCounter.getLastCall().getMinute() < 15)
			return callCounter.getLastCall().withMinute(15).withSecond(0).withNano(0);
		else if (callCounter.getLastCall().getMinute() < 30)
			return callCounter.getLastCall().withMinute(30).withSecond(0).withNano(0);
		else if (callCounter.getLastCall().getMinute() < 45)
			return callCounter.getLastCall().withMinute(45).withSecond(0).withNano(0);
		else			
			return callCounter.getLastCall().withMinute(0).withSecond(0).withNano(0).plusHours(1);
	}

	public CallCounter findCounter() {
		return this.callCounter;
	}
	
	public void increaseCounter() {
		LocalDateTime now = LocalDateTime.now();
		
		callCounter.increaseTotalCount();
		
		if (now.toLocalDate().equals(callCounter.getLastCall().toLocalDate()))
			callCounter.increaseDayCount();
		else
			callCounter.setDayCount(1);

		if(now.isBefore(end15Minute()))
			callCounter.increaseQuarterCount();
		else
			callCounter.setQuarterCount(1);

		callCounter.setLastCall(now);
		
		log.debug(callCounter.toString());
	}

	public Boolean increaseAllowed() {
		LocalDateTime now = LocalDateTime.now();

		if (callCounter.getDayCount() >= CallCounterServiceImpl.maxDayCount && 
				now.toLocalDate().equals(callCounter.getLastCall().toLocalDate()))
			return false;
		else 
			return !(callCounter.getQuarterCount() >= CallCounterServiceImpl.maxQuarterCount &&
					now.isBefore(end15Minute()));
	}
	
	public void waitIfNecessary() {
		lock.lock();
		
		if (!increaseAllowed())
			try {
				log.info("awaitUntil: " + end15Minute().toString());
//				condition.awaitUntil(DateTime.now().plusSeconds(3).toDate());
				condition.awaitUntil(Date.from(end15Minute().atZone(ZoneId.systemDefault()).toInstant()));
			} catch (InterruptedException e) {
				log.debug("interrupted");
			} finally {
				lock.unlock();
			}
		
		return;
	}
}
