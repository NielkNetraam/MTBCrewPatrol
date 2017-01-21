package nl.tisit.strava.repository;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.strava.model.Gear;

public interface GearRepository extends CrudRepository<Gear, Integer> {
}
