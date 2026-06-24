package com.fifa.FifaWorldCupMatchSchedule.dto;

import java.math.BigDecimal;

public record MatchResponse(
        String id,
        String name,
        String firstTeam,
        String secondTeam,
        String Group,
        BigDecimal price
) {
}
