package com.danny.cathybank.service;

import com.danny.cathybank.dto.CreateCoinRequest;
import com.danny.cathybank.dto.UpdateCoinRequest;
import com.danny.cathybank.model.Coin;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CoinService {

    List<Coin> getCoins(Sort sort);

    Coin getCoin(Long id);

    Coin createCoin(CreateCoinRequest request);

    Coin updateCoin(Coin existingCoin, UpdateCoinRequest request);

    void deleteCoin(Long id);
}
