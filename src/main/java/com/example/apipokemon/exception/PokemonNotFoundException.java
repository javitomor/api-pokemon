package com.example.apipokemon.exception;

public class PokemonNotFoundException extends RuntimeException{

    public PokemonNotFoundException(String message){
        super(message);
    }
}
