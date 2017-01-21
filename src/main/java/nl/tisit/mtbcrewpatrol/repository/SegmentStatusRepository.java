package nl.tisit.mtbcrewpatrol.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.mtbcrewpatrol.model.ResourceStatus;
import nl.tisit.mtbcrewpatrol.model.SegmentStatus;
import nl.tisit.mtbcrewpatrol.model.SegmentType;

public interface SegmentStatusRepository extends CrudRepository<SegmentStatus, Integer> {
	List<SegmentStatus> findByStatus(ResourceStatus status);
	List<SegmentStatus> findByStatusOrderByLastChecked(ResourceStatus status);
	List<SegmentStatus> findBySegmentTypeAndLastCheckedNotNull(SegmentType segmentType);
}
