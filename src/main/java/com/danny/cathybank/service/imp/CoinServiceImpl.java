package com.danny.cathybank.service.imp;

import com.danny.cathybank.constant.CurrencyName;
import com.danny.cathybank.dto.CreateCoinRequest;
import com.danny.cathybank.dto.UpdateCoinRequest;
import com.danny.cathybank.model.Coin;
import com.danny.cathybank.repository.CoinRepository;
import com.danny.cathybank.service.CoinService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CoinServiceImpl implements CoinService {
    private final CoinRepository coinRepository;

    public CoinServiceImpl(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    @Override
    public List<Coin> getCoins(Sort sort) {
        return new ArrayList<>(coinRepository.findAll(sort));
    }

    @Override
    public Coin getCoin(Long id) {
        return coinRepository.findById(id).orElse(null);
    }

    @Override
    public Coin createCoin(CreateCoinRequest request) {
        Coin coin = Coin.builder()
                .code(request.getCode())
                .currencyName(CurrencyName.valueOf(request.getCode()).getCurrencyChineseName())
                .rate(request.getRate())
                .rateFloat(request.getRateFloat())
                .description(request.getDescription())
                .lastModifyTime(LocalDateTime.now())
                .build();

        return coinRepository.save(coin);
    }

    @Override
    public Coin updateCoin(Coin existingCoin, UpdateCoinRequest request) {
        existingCoin.setRate(request.getRate());
        existingCoin.setRateFloat(request.getRateFloat());
        return coinRepository.save(existingCoin);
    }

    @Override
    public void deleteCoin(Long id) {
        coinRepository.deleteById(id);
    }
}
