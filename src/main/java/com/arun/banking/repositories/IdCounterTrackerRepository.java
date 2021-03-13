package com.arun.banking.repositories;

import com.arun.banking.entities.IdCounterTracker;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface to Query against MongoDb for IdCounterTracker Collection
 */
public interface IdCounterTrackerRepository extends MongoRepository<IdCounterTracker, String> {

    IdCounterTracker findByCounterField(String counterField);
}
