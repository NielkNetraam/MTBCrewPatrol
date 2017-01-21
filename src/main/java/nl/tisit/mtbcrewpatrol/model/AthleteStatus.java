package nl.tisit.mtbcrewpatrol.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "athlete_status")
public class AthleteStatus extends BaseStatus {

	public AthleteStatus() {
	}

	public AthleteStatus(Integer id) {
		super(id);
	}

}
