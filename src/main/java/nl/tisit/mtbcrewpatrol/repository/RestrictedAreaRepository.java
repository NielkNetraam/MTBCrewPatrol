package nl.tisit.mtbcrewpatrol.repository;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.mtbcrewpatrol.model.RestrictedArea;

public interface RestrictedAreaRepository extends CrudRepository<RestrictedArea, Integer> {
}
