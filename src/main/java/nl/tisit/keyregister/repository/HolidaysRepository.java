package nl.tisit.keyregister.repository;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.keyregister.model.Holidays;

public interface HolidaysRepository extends CrudRepository<Holidays, Integer> {
}

