package nl.tisit.mtbcrewpatrol.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ActivityInfoDetail   {
	
	private Integer count;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
	private List<LocalDateTime> starts;
	
	public ActivityInfoDetail() {
		super();
		count = 0;
		starts = null;
	}
	
	public void add(LocalDateTime start) {
		if (starts == null) 
			starts = new ArrayList<LocalDateTime>();
		
		if (count == null)
			count = 1;
		else
			count++;

		starts.add(start);
	}	

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<LocalDateTime> getStarts() {
		return starts;
	}

	public void setStarts(List<LocalDateTime> starts) {
		this.starts = starts;
	}

	@Override
	public String toString() {
		return "ActivityInfoDetail [count=" + count + ", starts=" + starts + "]";
	}
}
