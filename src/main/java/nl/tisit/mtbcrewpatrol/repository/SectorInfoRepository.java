package nl.tisit.mtbcrewpatrol.repository;

import org.springframework.data.repository.CrudRepository;

import nl.tisit.mtbcrewpatrol.model.dm.SectorInfo;

public interface SectorInfoRepository extends CrudRepository<SectorInfo, Integer> {
	SectorInfo findBySectorId(Integer sectorId);
}

