package com.danny.cathybank.service;

import com.danny.cathybank.response.CoinDeskResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CoinDeskService {

    List<CoinDeskResponse.CoinDesk> getCoinDesks();
}
