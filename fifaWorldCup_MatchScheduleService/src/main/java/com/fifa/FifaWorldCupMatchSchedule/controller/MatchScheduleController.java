package com.fifa.FifaWorldCupMatchSchedule.controller;

import com.fifa.FifaWorldCupMatchSchedule.dto.MatchRequest;
import com.fifa.FifaWorldCupMatchSchedule.dto.MatchResponse;
import com.fifa.FifaWorldCupMatchSchedule.service.MatchScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fifa-world-cup")
public class MatchScheduleController {

    private final MatchScheduleService matchScheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatchResponse createMatch(@RequestBody MatchRequest matchRequest) {
        return matchScheduleService.createMatch(matchRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MatchResponse> getAllMatches() {
        return matchScheduleService.getAllMatches();
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Welcome To The FIFA Schedule";
    }
}
