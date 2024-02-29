package com.danny.cathybank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class CreateCoinRequest {
    @NotNull
    private String code;

    @NotNull
    private String rate;

    @NotNull
    private BigDecimal rateFloat;

    @NotNull
    private String description;
}
