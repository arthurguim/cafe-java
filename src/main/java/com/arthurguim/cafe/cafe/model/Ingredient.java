package com.arthurguim.cafe.cafe.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"id", "hamburguers"})
@Entity
@Table(name = "INGREDIENTS")
public class Ingredient {

    // ID for the database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    // Ingredient name
    @Column(name = "NAME", nullable = false)
    private String name;

    // Ingredient price
    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Transient
    private int quantity;

    // List of hamburguers - used by JPA
    @ManyToMany
    public List<Hamburguer> hamburguers;
}