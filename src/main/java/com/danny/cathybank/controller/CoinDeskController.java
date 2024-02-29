package com.danny.cathybank.controller;

import com.danny.cathybank.model.Coin;
import com.danny.cathybank.response.CoinDeskResponse;
import com.danny.cathybank.service.CoinDeskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoinDeskController {

    private final CoinDeskService coinDeskService;

    public CoinDeskController(CoinDeskService coinDeskService) {
        this.coinDeskService = coinDeskService;
    }

    @GetMapping("/coinDesks")
    public ResponseEntity<List<CoinDeskResponse.CoinDesk>> getCoinDesk(){
        return ResponseEntity.ok().body(coinDeskService.getCoinDesks());
    }

}
