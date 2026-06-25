package com.fifa.FifaWorldCupMatchSchedule.service;

import com.fifa.FifaWorldCupMatchSchedule.dto.MatchRequest;
import com.fifa.FifaWorldCupMatchSchedule.dto.MatchResponse;
import com.fifa.FifaWorldCupMatchSchedule.model.Match;
import com.fifa.FifaWorldCupMatchSchedule.repository.MatchScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchScheduleService {
    
    private final MatchScheduleRepository matchScheduleRepository;

    public MatchResponse createMatch(MatchRequest matchRequest) {
        Match match = Match.builder()
                .name(matchRequest.name())
                .firstTeam(matchRequest.firstTeam())
                .secondTeam(matchRequest.secondTeam())
                .groupName(matchRequest.Group())
                .price(matchRequest.price())
                .build();
        matchScheduleRepository.save(match);

        log.info("Match created successfully");
        return new MatchResponse(match.getId(), match.getName(), match.getFirstTeam(), match.getSecondTeam(), match.getGroupName(), match.getPrice());
    }

    public List<MatchResponse> getAllMatches() {
        return matchScheduleRepository.findAll()
                .stream()
                .map(match -> new MatchResponse(match.getId(), match.getName(), match.getFirstTeam(), match.getSecondTeam(), match.getGroupName(), match.getPrice()))
                .toList();
    }
}
