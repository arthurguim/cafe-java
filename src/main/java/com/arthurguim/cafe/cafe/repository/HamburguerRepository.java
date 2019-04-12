package com.arthurguim.cafe.cafe.repository;

import com.arthurguim.cafe.cafe.model.Hamburguer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HamburguerRepository extends JpaRepository<Hamburguer, Long> {

    // Find the hamburguer by it's name
    Hamburguer findByName(String name);
}