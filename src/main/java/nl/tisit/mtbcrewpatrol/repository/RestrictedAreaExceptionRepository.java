package nl.tisit.mtbcrewpatrol.repository;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.mtbcrewpatrol.model.RestrictedAreaException;

public interface RestrictedAreaExceptionRepository extends CrudRepository<RestrictedAreaException, Integer> {
}
