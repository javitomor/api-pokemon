package com.example.apipokemon.advicers.service;

import com.example.apipokemon.exception.PokemonDeleteException;
import com.example.apipokemon.exception.PokemonNotFoundException;
import com.example.apipokemon.exception.PokemonSaveException;
import com.example.apipokemon.lang.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PokemonServiceAdvice extends BasicAdvice {

    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<ApiError> handlePokemonNotFoundException(PokemonNotFoundException exception)
    {
        return error(HttpStatus.NOT_FOUND,exception);
    }

    @ExceptionHandler(PokemonSaveException.class)
    public ResponseEntity<ApiError> handlePokemonSaveException(PokemonSaveException exception){
        return error(HttpStatus.UNPROCESSABLE_ENTITY,exception);
    }

    @ExceptionHandler(PokemonDeleteException.class)
    public ResponseEntity<ApiError> handlePokemonDeleteException(PokemonDeleteException exception){
        return error(HttpStatus.UNPROCESSABLE_ENTITY,exception);
    }



}
