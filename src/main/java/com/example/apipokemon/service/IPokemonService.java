package com.example.apipokemon.service;

import com.example.apipokemon.model.Pokemon;
import com.example.apipokemon.payload.response.PokemonResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPokemonService {
    Page<Pokemon> findAllPaginated(Pageable pageable);
    Pokemon findById(Long id);
    Pokemon save(Pokemon pokemon);
    void delete(Pokemon pokemon);

    List<PokemonResponseDTO> getAllPaginated(Pageable pageable);
}
