package nl.tisit.mtbcrewpatrol.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.mtbcrewpatrol.model.AthleteStatus;
import nl.tisit.mtbcrewpatrol.model.ResourceStatus;

public interface AthleteStatusRepository extends CrudRepository<AthleteStatus, Integer> {
	List<AthleteStatus> findByStatus(ResourceStatus status);
}
