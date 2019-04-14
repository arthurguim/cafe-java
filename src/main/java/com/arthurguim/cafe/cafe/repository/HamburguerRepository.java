package com.arthurguim.cafe.cafe.repository;

import java.util.Optional;

import com.arthurguim.cafe.cafe.model.Hamburguer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HamburguerRepository extends JpaRepository<Hamburguer, Long> {

    // Find the hamburguer by it's name
    Optional<Hamburguer> findByName(String name);
}