package com.arthurguim.cafe.cafe.repository;

import com.arthurguim.cafe.cafe.model.sales.ProportionalSale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProportionalSaleRepository extends JpaRepository<ProportionalSale, Long> {
}