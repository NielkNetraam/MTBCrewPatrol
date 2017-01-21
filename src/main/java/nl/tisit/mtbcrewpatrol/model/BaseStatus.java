package nl.tisit.mtbcrewpatrol.model;

import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

@MappedSuperclass
public class BaseStatus {
    @Id
    protected Integer id;
	@Type(type ="org.hibernate.type.LocalDateTimeType")
    protected LocalDateTime lastChecked;
    protected ResourceStatus status = ResourceStatus.UNKNOWN;
   
    public BaseStatus() {
	}

    public BaseStatus(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public LocalDateTime getLastChecked() {
		return lastChecked;
	}

	public void setLastChecked(LocalDateTime lastChecked) {
		this.lastChecked = lastChecked;
	}

	public ResourceStatus getStatus() {
		return status;
	}

	public void setStatus(ResourceStatus status) {
		this.status = status;
	}

	public boolean isNew() {
        return this.id == null;
    }
}
