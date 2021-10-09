package com.example.apipokemon.service;

import com.example.apipokemon.payload.request.PokemonRequestDTO;
import com.example.apipokemon.payload.response.PokemonResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IPokemonService {
    Page<PokemonResponseDTO> getAllPaginated(Pageable pageable);
    PokemonResponseDTO getPokemon(Long id);
    void ajouterPokemon(PokemonRequestDTO pokemonRequestDTO);
    void updatePokemon(Long id, PokemonRequestDTO pokemonRequestDTO);
    void delete(Long id);

}
