package com.example.apipokemon.advicers.service;

import com.example.apipokemon.exception.PokemonNotFoundException;
import com.example.apipokemon.lang.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class PokemonServiceAdvice extends BasicAdvice {

    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<ApiError> handlePokemonNotFoundException(PokemonNotFoundException exception)
    {
        return error(HttpStatus.NOT_FOUND,exception);
    }



}
