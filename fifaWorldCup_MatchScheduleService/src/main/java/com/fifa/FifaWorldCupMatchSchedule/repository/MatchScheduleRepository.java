package com.fifa.FifaWorldCupMatchSchedule.repository;

import com.fifa.FifaWorldCupMatchSchedule.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchScheduleRepository  extends MongoRepository<Match, String> {
}
