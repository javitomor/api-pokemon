package com.example.apipokemon.service.impl;

import com.example.apipokemon.exception.PokemonNotFoundException;
import com.example.apipokemon.lang.MessageResource;
import com.example.apipokemon.model.Pokemon;
import com.example.apipokemon.payload.response.PokemonResponseDTO;
import com.example.apipokemon.repository.IPokemonRepository;
import com.example.apipokemon.service.IPokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PokemonServiceImpl implements IPokemonService {

    //TODO cargar datos desde el csv

    @Autowired
    IPokemonRepository pokemonRepository;
    @Autowired
    MessageResource messageResource;

    @Override
    public Page<Pokemon> findAllPaginated(Pageable pageable) {
        return pokemonRepository.findAll(pageable);
    }

    @Override
    public Pokemon findById(Long id) {
        return pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException(messageResource.toLocale("error.pokemon.not.found")));
    }

    @Override
    public Pokemon save(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    @Override
    public void delete(Pokemon pokemon) {
        pokemonRepository.delete(pokemon);
    }

    @Override
    public List<PokemonResponseDTO> getAllPaginated(Pageable pageable) {
        List<PokemonResponseDTO> listPokemonResponseDto = new ArrayList<>();

        findAllPaginated(pageable).forEach(pokemon -> {
            listPokemonResponseDto.add(convertToPokemonResponseDto(pokemon));
        });
        return listPokemonResponseDto;
    }

    public PokemonResponseDTO convertToPokemonResponseDto(Pokemon pokemon) {
        return PokemonResponseDTO.builder()
                .id(pokemon.getId())
                .name(Optional.of(pokemon.getName()).orElse(null))
                .type1(Optional.of(pokemon.getType1()).orElse(null))
                .type2(Optional.of(pokemon.getType2()).orElse(null))
                .total(Optional.of(pokemon.getTotal()).orElse(null))
                .hp(Optional.of(pokemon.getHp()).orElse(null))
                .attack(Optional.of(pokemon.getAttack()).orElse(null))
                .defense(Optional.of(pokemon.getDefense()).orElse(null))
                .spAtk(Optional.of(pokemon.getSpAtk()).orElse(null))
                .spDef(Optional.of(pokemon.getSpDef()).orElse(null))
                .speed(Optional.of(pokemon.getSpeed()).orElse(null))
                .generation(Optional.of(pokemon.getGeneration()).orElse(null))
                .legendary(Optional.of(pokemon.isLegendary()).orElse(null))
                .build();
    }
}
