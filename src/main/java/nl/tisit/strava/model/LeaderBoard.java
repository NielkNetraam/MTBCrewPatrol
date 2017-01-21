package nl.tisit.strava.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaderBoard {
	@JsonProperty("entry_count")
	private Integer entryCount;
	
	private List<LeaderBoardEntry> entries;

	public Integer getEntryCount() {
		return entryCount;
	}

	public void setEntryCount(Integer entryCount) {
		this.entryCount = entryCount;
	}

	public List<LeaderBoardEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<LeaderBoardEntry> entries) {
		this.entries = entries;
	}

	@Override
	public String toString() {
		return "LeaderBoard [entryCount=" + entryCount + ", entries=" + entries + "]";
	}
	
	
}
