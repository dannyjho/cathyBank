package com.danny.cathybank.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class CoinDeskOutResponse {
    private Time time;
    private String disclaimer;
    private String chartName;
    private Map<String, CurrencyData> bpi;

    @Data
    public static class Time {
        private String updated;
        private String updatedISO;
        private String updatedUK;
    }

    @Data
    public static class CurrencyData {
        private String code;
        private String symbol;
        private String rate;
        private String description;
        private BigDecimal rate_float;
    }
}
