package com.example.apipokemon.service.impl;

import com.example.apipokemon.exception.PokemonDeleteException;
import com.example.apipokemon.exception.PokemonNotFoundException;
import com.example.apipokemon.exception.PokemonSaveException;
import com.example.apipokemon.lang.MessageResource;
import com.example.apipokemon.model.Pokemon;
import com.example.apipokemon.payload.request.PokemonRequestDTO;
import com.example.apipokemon.payload.response.PokemonResponseDTO;
import com.example.apipokemon.repository.IPokemonRepository;
import com.example.apipokemon.service.IPokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PokemonServiceImpl implements IPokemonService {


    @Autowired
    IPokemonRepository pokemonRepository;
    @Autowired
    MessageResource messageResource;

    public PokemonServiceImpl(IPokemonRepository pokemonRepository,MessageResource messageResource) {
        this.pokemonRepository = pokemonRepository;
        this.messageResource=messageResource;
    }

    @Override
    public void delete(Long id) {
        Pokemon pokemon = findById(id);
        try {
            pokemonRepository.delete(pokemon);
        }catch (Exception e){
            throw new PokemonDeleteException(messageResource.toLocale("error.pokemon.deleted"));
        }
    }

    @Override
    public Page<PokemonResponseDTO> getAllPaginated(Pageable pageable) {
        List<PokemonResponseDTO> listPokemonResponseDto = new ArrayList<>();

        findAllPaginated(pageable).forEach(pokemon -> {
            listPokemonResponseDto.add(convertToPokemonResponseDto(pokemon));
        });
        return new PageImpl<>(listPokemonResponseDto);
    }

    @Override
    public PokemonResponseDTO getPokemon(Long id) {
        Pokemon pokemon = findById(id);
        return convertToPokemonResponseDto(pokemon);
    }


    @Override
    public void ajouterPokemon(PokemonRequestDTO pokemonDTO) {
        Pokemon pokemon = convertToPokemon(pokemonDTO);

        save(pokemon);
    }

    @Override
    public void updatePokemon(Long id, PokemonRequestDTO pokemonDTO) {

        Pokemon pokemon = findById(id);

        pokemon.updateValues(pokemonDTO);

        save(pokemon);
    }

    @Override
    public Page<Pokemon> findAllPaginated(Pageable pageable) {
        return pokemonRepository.findAll(pageable);
    }

    @Override
    public Pokemon findById(Long id) {
        return pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException(
                String.format(messageResource.toLocale("error.pokemon.not.found"), id)
        ));
    }

    @Override
    public Pokemon save(Pokemon pokemon) {
        try {
            return pokemonRepository.save(pokemon);
        } catch (Exception e) {
            throw new PokemonSaveException(messageResource.toLocale("error.pokemon.created"));
        }

    }

    @Override
    public PokemonResponseDTO convertToPokemonResponseDto(Pokemon pokemon) {
        return PokemonResponseDTO.builder()
                .id(pokemon.getId())
                .numero(Optional.of(pokemon.getNumero()).orElse(null))
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

    @Override
    public Pokemon convertToPokemon(PokemonRequestDTO pokemonDTO) {
        return Pokemon.builder()
                .numero(Optional.of(pokemonDTO.getNumero()).orElse(null))
                .name(Optional.of(pokemonDTO.getName()).orElse(null))
                .type1(Optional.of(pokemonDTO.getType1()).orElse(null))
                .type2(Optional.of(pokemonDTO.getType2()).orElse(null))
                .total(Optional.of(pokemonDTO.getTotal()).orElse(null))
                .hp(Optional.of(pokemonDTO.getHp()).orElse(null))
                .attack(Optional.of(pokemonDTO.getAttack()).orElse(null))
                .defense(Optional.of(pokemonDTO.getDefense()).orElse(null))
                .spAtk(Optional.of(pokemonDTO.getSpAtk()).orElse(null))
                .spDef(Optional.of(pokemonDTO.getSpDef()).orElse(null))
                .speed(Optional.of(pokemonDTO.getSpeed()).orElse(null))
                .generation(Optional.of(pokemonDTO.getGeneration()).orElse(null))
                .legendary(Optional.of(pokemonDTO.isLegendary()).orElse(null))
                .build();
    }


}
