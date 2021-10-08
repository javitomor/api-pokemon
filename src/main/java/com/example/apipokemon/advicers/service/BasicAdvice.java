package com.example.apipokemon.advicers.service;

import com.example.apipokemon.lang.ApiError;
import com.example.apipokemon.payload.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class BasicAdvice {
    public ResponseEntity<ApiError> error(HttpStatus status, Exception e){
        log.error("Exception: ", e);
        return ResponseEntity.status(status).body(getApiError(e));
    }
    private ApiError getApiError(Exception e){
        return ApiError.builder()
                .message(e.getMessage())
                .build();
    }

}
