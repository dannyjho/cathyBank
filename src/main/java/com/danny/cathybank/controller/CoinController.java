package com.danny.cathybank.controller;

import com.danny.cathybank.dto.CreateCoinRequest;
import com.danny.cathybank.dto.UpdateCoinRequest;
import com.danny.cathybank.model.Coin;
import com.danny.cathybank.service.CoinService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class CoinController {
    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping("/coins")
    public ResponseEntity<List<Coin>> getCoins(@RequestParam(defaultValue = "asc") Sort.Direction direction) {
        Sort sort = Sort.by(direction, "code");
        return ResponseEntity.ok().body(coinService.getCoins(sort));
    }

    @GetMapping("/coins/{id}")
    public ResponseEntity<Coin> getCoinById(@PathVariable Long id) {
        Coin existingCoin = coinService.getCoin(id);

        if (existingCoin == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(existingCoin);
    }

    @PostMapping("/coins")
    public ResponseEntity<Coin> createCoin(@RequestBody @Valid CreateCoinRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(coinService.createCoin(request));
    }

    @PutMapping("/coins/{id}")
    public ResponseEntity<Coin> updateCoinById(@PathVariable Long id, @RequestBody @Valid UpdateCoinRequest request) {
        Coin existingCoin = coinService.getCoin(id);

        if (existingCoin == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(coinService.updateCoin(existingCoin, request));
    }

    @DeleteMapping("/coins/{id}")
    public ResponseEntity<HttpStatus> deleteCoinById(@PathVariable Long id) {
        coinService.deleteCoin(id);
        return ResponseEntity.noContent().build();
    }
}
