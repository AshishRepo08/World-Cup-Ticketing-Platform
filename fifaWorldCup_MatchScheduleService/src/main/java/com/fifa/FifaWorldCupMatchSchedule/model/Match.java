package com.fifa.FifaWorldCupMatchSchedule.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    private String id;

    @NotBlank(message = "name should not be blank")
    private String name;

    private String firstTeam;
    private String secondTeam;
    private String groupName;

    @Positive(message = "price should be a positive value")
    private BigDecimal price;
}
