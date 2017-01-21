package nl.tisit.mtbcrewpatrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.tisit.mtbcrewpatrol.model.ActivityStatus;
import nl.tisit.mtbcrewpatrol.model.ResourceStatus;
import nl.tisit.mtbcrewpatrol.model.RideType;

public interface ActivityStatusRepository extends CrudRepository<ActivityStatus, Integer> {
	List<ActivityStatus> findByAthleteIdAndStatus(Integer athleteId, ResourceStatus status);
	List<ActivityStatus> findByAthleteId(Integer athleteId);
	List<ActivityStatus> findByStatus(ResourceStatus status);

	@Query(value = "select a.* from Activity_Status a"
			+ "  left join WildRide wr on wr.activityId = a.id and wr.restrictedArea_id = ?1 "
			+ " where wr.id is null"
			+ "   and a.status >= 2 ", nativeQuery = true)
	List<ActivityStatus> findByRestrictedArea(Integer restrictedAreaId);

	List<ActivityStatus> findByInfoIsNull();
	List<ActivityStatus> findByRideTypeAndInfoIsNull(RideType rideType);
	List<ActivityStatus> findByRideType(RideType rideType);
	
	@Query(value = "select s.* "
			+ "  from Activity_Status s "
			+ " inner join Activity a on a.id = s.id "
			+ " where s.rideType = ?1 "
			+ " order by a.startDateLocal desc"
			+ " limit 0, 1"
			, nativeQuery = true)
	ActivityStatus findLast(Integer rideType);
}
