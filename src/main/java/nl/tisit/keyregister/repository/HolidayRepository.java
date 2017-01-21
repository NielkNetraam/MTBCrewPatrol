package nl.tisit.keyregister.repository;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.keyregister.model.Holiday;

public interface HolidayRepository extends CrudRepository<Holiday, Integer> {
}

