package com.example.apipokemon.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonRequestDTO {
    private int numero;
    private String name;
    private String type1;
    private String type2;
    private int total;
    private int hp;
    private int attack;
    private int defense;
    private int spAtk;
    private int spDef;
    private int speed;
    private int generation;
    private boolean legendary;
}
