package com.danny.cathybank.scheduler;

import com.danny.cathybank.model.Coin;
import com.danny.cathybank.repository.CoinRepository;
import com.danny.cathybank.response.CoinDeskOutResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RateScheduler {

    private final RestTemplate restTemplate;
    private final CoinRepository coinRepository;

    public RateScheduler(RestTemplate restTemplate, CoinRepository coinRepository) {
        this.restTemplate = restTemplate;
        this.coinRepository = coinRepository;
    }

    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    public void updateExchangeRate() {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        CoinDeskOutResponse coindeskOutResponse = restTemplate.getForObject(url, CoinDeskOutResponse.class);
        Map<String, Coin> coinMap = coinRepository.findAll().stream().collect(Collectors.toMap(Coin::getCode, coin -> coin));

        for (Map.Entry<String, CoinDeskOutResponse.CurrencyData> entry : coindeskOutResponse.getBpi().entrySet()) {
            Coin coin = coinMap.get(entry.getKey());
            coin.setRate(entry.getValue().getRate());
            coin.setRateFloat(entry.getValue().getRate_float());
            coinMap.put(entry.getKey(), coin);
        }

        coinRepository.saveAll(new ArrayList<>(coinMap.values()));
    }
}
