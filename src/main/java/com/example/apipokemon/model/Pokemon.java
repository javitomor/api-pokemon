package com.example.apipokemon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Builder
@Entity
@Table(name = "pokemon")
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = true)
    private String name;

    @Column(name = "type1",nullable = true)
    private String type1;

    @Column(name = "type2",nullable = true)
    private String type2;

    @Column(name = "total",nullable = true)
    private int total;

    @Column(name = "hp",nullable = true)
    private int hp;

    @Column(name = "attack",nullable = true)
    private int attack;

    @Column(name = "defense",nullable = true)
    private int defense;

    @Column(name = "sp_atk",nullable = true)
    private int spAtk;

    @Column(name = "sp_def",nullable = true)
    private int spDef;

    @Column(name = "speed",nullable = true)
    private int speed;

    @Column(name = "generation",nullable = true)
    private int generation;

    @Column(name = "legendary",nullable = true)
    private boolean legendary;


}
