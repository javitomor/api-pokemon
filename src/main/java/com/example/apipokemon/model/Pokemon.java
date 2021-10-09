package com.example.apipokemon.model;

import com.example.apipokemon.payload.request.PokemonRequestDTO;
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

    @Column(name = "numero")
    private int numero;

    @Column(name = "name")
    private String name;

    @Column(name = "type1")
    private String type1;

    @Column(name = "type2")
    private String type2;

    @Column(name = "total")
    private int total;

    @Column(name = "hp")
    private int hp;

    @Column(name = "attack")
    private int attack;

    @Column(name = "defense")
    private int defense;

    @Column(name = "sp_atk")
    private int spAtk;

    @Column(name = "sp_def")
    private int spDef;

    @Column(name = "speed")
    private int speed;

    @Column(name = "generation")
    private int generation;

    @Column(name = "legendary")
    private boolean legendary;


    public void updateValues(PokemonRequestDTO dto) {
        this.numero=dto.getNumero();
        this.name=dto.getName();
        this.type1=dto.getType1();
        this.type2=dto.getType2();
        this.total=dto.getTotal();
        this.hp=dto.getHp();
        this.attack=dto.getAttack();
        this.defense=dto.getDefense();
        this.spAtk=dto.getSpAtk();
        this.spDef=dto.getSpDef();
        this.speed=dto.getSpeed();
        this.generation=dto.getGeneration();
        this.legendary=dto.isLegendary();
    }
}
