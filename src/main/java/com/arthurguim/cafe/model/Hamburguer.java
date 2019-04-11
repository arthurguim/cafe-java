package com.arthurguim.cafe.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HAMBURGUERS")
public class Hamburguer {

    // ID for the database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long Id;

    // Hamburguer name
    @Column(name = "NAME", nullable = false)
    private String name;

    // Hamburguer ingredients
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "HAMBURGUER_INGREDIENTS",
               joinColumns = @JoinColumn(name = "HAMBURGUER_ID", referencedColumnName = "ID"),
               inverseJoinColumns = @JoinColumn(name = "INGREDIENT_ID", referencedColumnName = "ID"))
    private List<Ingredient> ingredients;
}