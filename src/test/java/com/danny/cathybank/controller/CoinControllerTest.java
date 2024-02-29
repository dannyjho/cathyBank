package com.danny.cathybank.controller;

import com.danny.cathybank.constant.CurrencyName;
import com.danny.cathybank.dto.CreateCoinRequest;
import com.danny.cathybank.dto.UpdateCoinRequest;
import com.danny.cathybank.model.Coin;
import com.danny.cathybank.service.CoinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CoinControllerTest {
    private final List<Coin> descCoins = Arrays.asList(
            new Coin(1L, "USD", "美元", "51,583.726", BigDecimal.valueOf(51583.7261), "United States Dollar", LocalDateTime.now()),
            new Coin(2L, "EUR", "歐元", "47,615.442", BigDecimal.valueOf(47615.4417), "Euro", LocalDateTime.now())
    );

    private final List<Coin> ascCoins = Arrays.asList(
            new Coin(2L, "EUR", "歐元", "47,615.442", BigDecimal.valueOf(47615.4417), "Euro", LocalDateTime.now()),
            new Coin(1L, "USD", "美元", "51,583.726", BigDecimal.valueOf(51583.7261), "United States Dollar", LocalDateTime.now())
    );

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private CoinService coinService;

    @BeforeEach
    public void setUp() {
        Mockito.when(coinService.getCoins(Sort.by(Sort.Direction.ASC, "code"))).thenReturn(ascCoins);
        Mockito.when(coinService.getCoins(Sort.by(Sort.Direction.DESC, "code"))).thenReturn(descCoins);
        Mockito.when(coinService.getCoin(1L)).thenReturn(ascCoins.get(0));
    }

    @Test
    public void getCoins_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/coins")
                .param("direction", "ASC");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("EUR"))
                .andExpect(jsonPath("$[0].currencyName").value("歐元"))
                .andExpect(jsonPath("$[0].rate").value("47,615.442"))
                .andExpect(jsonPath("$[0].rateFloat").value(47615.4417))
                .andExpect(jsonPath("$[0].description").value("Euro"))
                .andExpect(jsonPath("$[1].code").value("USD"))
                .andExpect(jsonPath("$[1].currencyName").value("美元"))
                .andExpect(jsonPath("$[1].rate").value("51,583.726"))
                .andExpect(jsonPath("$[1].rateFloat").value(51583.7261))
                .andExpect(jsonPath("$[1].description").value("United States Dollar"));
    }

    @Test
    public void getCoins_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/coins")
                .param("direction", "DESC");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("USD"))
                .andExpect(jsonPath("$[1].code").value("EUR"));
    }

    @Test
    public void getCoin_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/coins/{id}", 1)
                .param("direction", "ASC");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.code").value("EUR"))
                .andExpect(jsonPath("$.currencyName").value("歐元"))
                .andExpect(jsonPath("$.rate").value("47,615.442"))
                .andExpect(jsonPath("$.rateFloat").value(47615.4417))
                .andExpect(jsonPath("$.description").value("Euro"));
    }

    @Test
    public void getCoin_notFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/coins/{id}", 99999)
                .param("direction", "ASC");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Transactional
    @Test
    public void createCoin_success() throws Exception {
        CreateCoinRequest request = CreateCoinRequest.builder()
                .code("JPY")
                .rate("110.00")
                .rateFloat(BigDecimal.valueOf(110.00))
                .description("Japanese Yen").build();

        Coin createdCoin = new Coin(1L, "JPY", CurrencyName.JPY.getCurrencyChineseName(), "110.00", BigDecimal.valueOf(110.00), "Japanese Yen", LocalDateTime.now());

        Mockito.when(coinService.createCoin(isA(CreateCoinRequest.class)))
                .thenReturn(createdCoin);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.code").value("JPY"))
                .andExpect(jsonPath("$.currencyName").value("日元"))
                .andExpect(jsonPath("$.rate").value("110.00"))
                .andExpect(jsonPath("$.rateFloat").value(110.00))
                .andExpect(jsonPath("$.description").value("Japanese Yen"));
    }

    @Test
    void createCoin_illegalArgument() throws Exception {
        Coin createCoin = new Coin();
        createCoin.setCode("JPY");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCoin));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    public void updateCoin_success() throws Exception {
        UpdateCoinRequest request = UpdateCoinRequest.builder()
                .rate("110.00")
                .rateFloat(BigDecimal.valueOf(110.00))
                .build();

        Coin existingCoin = new Coin(1L, "USD", "美元", "3456.00", BigDecimal.valueOf(3456.00), "United States Dollar", LocalDateTime.now());
        Coin updatedCoin = new Coin(1L, "USD", "美元", "110.00", BigDecimal.valueOf(110.00), "United States Dollar", LocalDateTime.now());

        Mockito.when(coinService.getCoin(1L))
                .thenReturn(existingCoin);

        Mockito.when(coinService.updateCoin(eq(existingCoin), eq(request)))
                .thenReturn(updatedCoin);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/coins/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.currencyName").value("美元"))
                .andExpect(jsonPath("$.rate").value("110.00"))
                .andExpect(jsonPath("$.rateFloat").value(110.00))
                .andExpect(jsonPath("$.description").value("United States Dollar"));
    }

    @Test
    public void updateCoin_illegalArgument() throws Exception {
        UpdateCoinRequest request = UpdateCoinRequest.builder().rateFloat(BigDecimal.valueOf(110.00)).build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/coins/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCoin_coinNotFound() throws Exception {
        UpdateCoinRequest request = UpdateCoinRequest.builder()
                .rate("110.00")
                .rateFloat(BigDecimal.valueOf(110.00))
                .build();


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/coins/{id}", 9987)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Transactional
    @Test
    public void deleteCoin_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/coins/{id}", 1);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCoin_illegalArgument() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/coins/{id}", "NOT_ID");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
}

