package com.danny.cathybank.service.imp;

import com.danny.cathybank.dto.CreateCoinRequest;
import com.danny.cathybank.dto.UpdateCoinRequest;
import com.danny.cathybank.model.Coin;
import com.danny.cathybank.repository.CoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;


@DataJpaTest
public class CoinServiceImplTest {
    @InjectMocks
    private CoinServiceImpl coinService;

    @Mock
    private CoinRepository coinRepository;

    @Test
    public void getCoins_success() {
        List<Coin> coins = Arrays.asList(
                new Coin(1L, "USD", "美元", "100", BigDecimal.valueOf(100), "description1", LocalDateTime.now()),
                new Coin(2L, "EUR", "歐元", "200", BigDecimal.valueOf(200), "description2", LocalDateTime.now())
        );

        Mockito.when(coinRepository.findAll(any(Sort.class))).thenReturn(coins);

        List<Coin> result = coinService.getCoins(Sort.by(Sort.Direction.ASC, "code"));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("USD", result.get(0).getCode());
        assertEquals("EUR", result.get(1).getCode());
    }

    @Test
    public void getCoin_success() {
        Coin coin = new Coin(1L, "USD", "美元", "100", BigDecimal.valueOf(100), "description", LocalDateTime.now());
        Mockito.when(coinRepository.findById(1L)).thenReturn(Optional.of(coin));

        Coin result = coinService.getCoin(1L);

        assertNotNull(result);
        assertEquals(coin, result);
    }

    @Transactional
    @Test
    public void createCoin_success() {
        Coin savedCoin = new Coin(1L, "USD", "美元", "100", BigDecimal.valueOf(100), "description", LocalDateTime.now());
        Mockito.when(coinRepository.save(any(Coin.class))).thenReturn(savedCoin);

        CreateCoinRequest request = CreateCoinRequest.builder()
                .code("USD")
                .rate("100")
                .rateFloat(BigDecimal.valueOf(100))
                .description("description")
                .build();

        Coin result = coinService.createCoin(request);

        assertNotNull(result);
        assertEquals(savedCoin, result);
    }

    @Transactional
    @Test
    public void updateCoin_success() {
        Coin existingCoin = new Coin(1L, "USD", "美元", "100", BigDecimal.valueOf(100), "description", LocalDateTime.now());
        UpdateCoinRequest request = UpdateCoinRequest.builder()
                .rate("200")
                .rateFloat(BigDecimal.valueOf(200))
                .build();

        coinService.updateCoin(existingCoin, request);

        Mockito.verify(coinRepository, Mockito.times(1)).save(existingCoin);
    }

    @Transactional
    @Test
    public void deleteCoin_success() {
        coinService.deleteCoin(1L);

        Mockito.verify(coinRepository, Mockito.times(1)).deleteById(1L);
    }
}
