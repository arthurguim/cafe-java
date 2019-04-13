package com.arthurguim.cafe.cafe.repository;

import com.arthurguim.cafe.cafe.model.sales.QuantitativeSale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantitativeSaleRepository extends JpaRepository<QuantitativeSale, Long> {
}