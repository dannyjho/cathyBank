package com.danny.cathybank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@Builder
@EqualsAndHashCode
public class UpdateCoinRequest {
    @NotNull
    private String rate;

    @NotNull
    private BigDecimal rateFloat;
}
