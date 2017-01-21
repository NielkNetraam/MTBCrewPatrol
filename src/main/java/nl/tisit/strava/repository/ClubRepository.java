package nl.tisit.strava.repository;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.strava.model.Club;

public interface ClubRepository extends CrudRepository<Club, Integer> {
}
