package nl.tisit.mtbcrewpatrol.model;

import java.time.LocalDateTime;

public class CallCounter {
	private Integer totalCount = 0;
	private Integer dayCount = 0;
	private Integer quarterCount = 0;
	private LocalDateTime lastCall = LocalDateTime.now();

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getDayCount() {
		return dayCount;
	}

	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}

	public Integer getQuarterCount() {
		return quarterCount;
	}

	public void setQuarterCount(Integer quarterCount) {
		this.quarterCount = quarterCount;
	}

	public LocalDateTime getLastCall() {
		return lastCall;
	}

	public void setLastCall(LocalDateTime lastCall) {
		this.lastCall = lastCall;
	}
	
	public Integer increaseTotalCount() {
		return this.totalCount++; 
	}
	
	public Integer increaseDayCount() {
		return this.dayCount++; 
	}

	public Integer increaseQuarterCount() {
		return this.quarterCount++; 
	}

	@Override
	public String toString() {
		return "CallCounter [totalCount=" + totalCount + ", dayCount=" + dayCount + ", quarterCount=" + quarterCount
				+ ", lastCall=" + lastCall + "]";
	}
	
}
