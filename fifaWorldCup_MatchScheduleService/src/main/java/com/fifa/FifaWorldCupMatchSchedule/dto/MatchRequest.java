package com.fifa.FifaWorldCupMatchSchedule.dto;

import java.math.BigDecimal;

public record MatchRequest(
        String name,
        String firstTeam,
        String secondTeam,
        String Group,
        BigDecimal price) {
}
