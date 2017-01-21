package nl.tisit.mtbcrewpatrol.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.mtbcrewpatrol.model.WildRide;

public interface WildRideRepository extends CrudRepository<WildRide, Integer> {
	List<WildRide> findByActivityId(Integer activityId);
	List<WildRide> findByWild(Boolean wild);
}
