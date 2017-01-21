package nl.tisit.keyregister.repository;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.keyregister.model.Settings;

public interface SettingsRepository extends CrudRepository<Settings, Integer> {
	Settings findByParameter(String parameter);
}

