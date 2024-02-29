package com.danny.cathybank.repository;

import com.danny.cathybank.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin,Long> {
}
