package com.danny.cathybank.scheduler;

import com.danny.cathybank.model.Coin;
import com.danny.cathybank.repository.CoinRepository;
import com.danny.cathybank.response.CoinDeskOutResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateSchedulerTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CoinRepository coinRepository;

    @InjectMocks
    private RateScheduler rateScheduler;

    @Test
    public void testUpdateExchangeRate() {
        CoinDeskOutResponse.CurrencyData currencyData = new CoinDeskOutResponse.CurrencyData();
        currencyData.setRate("1000.0");
        currencyData.setRate_float(BigDecimal.valueOf(1000.0));

        Map<String, CoinDeskOutResponse.CurrencyData> bpi = new HashMap<>();
        bpi.put("USD", currencyData);

        CoinDeskOutResponse coindeskOutResponse = CoinDeskOutResponse.builder()
                .bpi(bpi)
                .build();

        List<Coin> coins = new ArrayList<>();
        Coin coin = new Coin();
        coin.setCode("USD");
        coins.add(coin);

        when(restTemplate.getForObject(anyString(), eq(CoinDeskOutResponse.class))).thenReturn(coindeskOutResponse);
        when(coinRepository.findAll()).thenReturn(coins);
        when(coinRepository.saveAll(anyList())).thenReturn(coins);

        rateScheduler.updateExchangeRate();

        verify(restTemplate).getForObject(anyString(), eq(CoinDeskOutResponse.class));
        verify(coinRepository).findAll();
        verify(coinRepository).saveAll(anyList());
    }
}
