package com.danny.cathybank.service.imp;

import com.danny.cathybank.model.Coin;
import com.danny.cathybank.repository.CoinRepository;
import com.danny.cathybank.response.CoinDeskResponse;
import com.danny.cathybank.service.CoinDeskService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoinDeskServiceImpl implements CoinDeskService {

    private final CoinRepository coinRepository;

    public CoinDeskServiceImpl(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    @Override
    public List<CoinDeskResponse.CoinDesk> getCoinDesks() {
        List<CoinDeskResponse.CoinDesk> coinDesks = new ArrayList<>();
        for (Coin coin : coinRepository.findAll()) {
            CoinDeskResponse.CoinDesk coinDesk = CoinDeskResponse.CoinDesk.builder()
                    .code(coin.getCode())
                    .currencyName(coin.getCurrencyName())
                    .rateFloat(coin.getRateFloat())
                    .lastModifyTime(coin.getLastModifyTime())
                    .build();
            coinDesks.add(coinDesk);
        }
        return coinDesks;
    }
}
