package com.danny.cathybank.service.imp;

import com.danny.cathybank.model.Coin;
import com.danny.cathybank.repository.CoinRepository;
import com.danny.cathybank.response.CoinDeskResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoinDeskServiceImplTest {
    @Mock
    private CoinRepository coinRepository;

    @InjectMocks
    private CoinDeskServiceImpl coinDeskService;

    @BeforeEach
    public void setUp() {
        List<Coin> mockCoins = new ArrayList<>();
        mockCoins.add(new Coin(1L, "USD", "美元", "100", BigDecimal.valueOf(100), "United State Dollar", LocalDateTime.now()));
        mockCoins.add(new Coin(2L, "EUR", "歐元", "200", BigDecimal.valueOf(200), "Europe", LocalDateTime.now()));

        when(coinRepository.findAll()).thenReturn(mockCoins);
    }

    @Test
    public void getCoinDesk_success() {
        List<CoinDeskResponse.CoinDesk> coinDesks = coinDeskService.getCoinDesks();

        Assertions.assertEquals(2, coinDesks.size());
        Assertions.assertEquals("USD", coinDesks.get(0).getCode());
        Assertions.assertEquals("美元", coinDesks.get(0).getCurrencyName());
        Assertions.assertEquals("EUR", coinDesks.get(1).getCode());
        Assertions.assertEquals("歐元", coinDesks.get(1).getCurrencyName());
    }


}
