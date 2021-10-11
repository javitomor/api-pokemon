package com.example.apipokemon.enums;

import lombok.Getter;

@Getter
public enum SortFieldsPokemonEnum {

    ID("id", "id"),
    NUMERO("numero","numero"),
    NAME("name", "name"),
    TYPE1("type1", "type1"),
    TYPE2("type2", "type2"),
    TOTAL("total", "total"),
    HP("hp", "hp"),
    ATTACK("attack", "attack"),
    DEFENSE("defense", "defense"),
    SP_ATK("spAtk", "sp_atk"),
    SP_DEF("spDef","sp_def"),
    SPEED("speed","speed"),
    GENERATION("generation","generation"),
    LEGENDARY("legendary","legendary");

    private final String attribute;
    private final String column;

    SortFieldsPokemonEnum(String attribute, String column) {
        this.attribute = attribute;
        this.column = column;
    }

    }
