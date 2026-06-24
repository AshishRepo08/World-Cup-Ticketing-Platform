package com.fifa.FifaWorldCupMatchSchedule.service;

import com.fifa.FifaWorldCupMatchSchedule.dto.MatchRequest;
import com.fifa.FifaWorldCupMatchSchedule.dto.MatchResponse;
import com.fifa.FifaWorldCupMatchSchedule.model.Match;
import com.fifa.FifaWorldCupMatchSchedule.repository.MatchScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
                .Group(matchRequest.Group())
                .price(matchRequest.price())
                .build();
        matchScheduleRepository.save(match);

        log.info("Match created successfully");
        return new MatchResponse(match.getId(), match.getName(), match.getFirstTeam(), match.getSecondTeam(), match.getGroup(), match.getPrice());
    }

    public List<MatchResponse> getAllMatches() {
        return matchScheduleRepository.findAll()
                .stream()
                .map(match -> new MatchResponse(match.getId(), match.getName(), match.getFirstTeam(), match.getSecondTeam(), match.getGroup(), match.getPrice()))
                .toList();
    }
}
