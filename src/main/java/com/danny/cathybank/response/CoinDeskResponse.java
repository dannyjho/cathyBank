package com.danny.cathybank.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CoinDeskResponse {

    List<CoinDesk> coinDeskList;

    @Data
    @Builder
    public static class CoinDesk{
        private String code;
        private String currencyName;
        private BigDecimal rateFloat;
        private LocalDateTime lastModifyTime;
    }
}
