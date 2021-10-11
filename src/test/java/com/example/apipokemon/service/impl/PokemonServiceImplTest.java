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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PokemonServiceImplTest {

    IPokemonRepository pokemonRepository;
    IPokemonService pokemonService;
    MessageResource messageResource;
    @BeforeEach
    void setUp() {
        pokemonRepository=mock(IPokemonRepository.class);
        messageResource=mock(MessageResource.class);
        pokemonService= new PokemonServiceImpl(pokemonRepository,messageResource);

        Pokemon pokemon1 = Pokemon.builder()
                .id(1L)
                .numero(1)
                .name("Bulbasaur")
                .type1("Grass")
                .type2("Poison")
                .total(318)
                .hp(45)
                .attack(49)
                .defense(49)
                .spAtk(65)
                .spDef(65)
                .speed(45)
                .generation(1)
                .legendary(false)
                .build();


        Pokemon pokemon2 = Pokemon.builder()
                .id(2L)
                .numero(2)
                .name("Ivysaur")
                .type1("Grass")
                .type2("Poison")
                .total(405)
                .hp(60)
                .attack(62)
                .defense(63)
                .spAtk(80)
                .spDef(80)
                .speed(60)
                .generation(1)
                .legendary(false)
                .build();

        Pokemon pokemonSansId2 = Pokemon.builder()
                .numero(2)
                .name("Ivysaur")
                .type1("Grass")
                .type2("Poison")
                .total(405)
                .hp(60)
                .attack(62)
                .defense(63)
                .spAtk(80)
                .spDef(80)
                .speed(60)
                .generation(1)
                .legendary(false)
                .build();


        when(messageResource.toLocale(any(String.class))).thenReturn("Test message");
        //le réponse est correcte
        when(pokemonRepository.findById(1L)).thenReturn(Optional.of(pokemon1));
        // trouvé aucun enregistrement
        when(pokemonRepository.findById(2L)).thenReturn(Optional.empty());

        //le réponse est correcte
        when(pokemonRepository.save(pokemonSansId2)).thenReturn(pokemon2);

    }


    @Test
    void deleteUnPokemonEchecTest() {
        Pokemon pokemon1 = Pokemon.builder()
                .id(1L)
                .numero(1)
                .name("Bulbasaur")
                .type1("Grass")
                .type2("Poison")
                .total(318)
                .hp(45)
                .attack(49)
                .defense(49)
                .spAtk(65)
                .spDef(65)
                .speed(45)
                .generation(1)
                .legendary(false)
                .build();

        //impossible de supprimer le pokémon
        doThrow(RuntimeException.class).when(pokemonRepository).delete(pokemon1);

        assertThrows(PokemonDeleteException.class,()->{
            pokemonService.delete(1L);
        });
    }

    @Test
    void deleteUnPokemonInexistantTest() {


        assertThrows(PokemonNotFoundException.class,()->{
            pokemonService.delete(2L);
        });

    }


    @Test
    void obtenirUnPokemonDtoSuccesTest() {

        PokemonResponseDTO pokemonExpected = PokemonResponseDTO.builder()
                .id(1L)
                .numero(1)
                .name("Bulbasaur")
                .type1("Grass")
                .type2("Poison")
                .total(318)
                .hp(45)
                .attack(49)
                .defense(49)
                .spAtk(65)
                .spDef(65)
                .speed(45)
                .generation(1)
                .legendary(false)
                .build();


        Pokemon pokemonActual = pokemonService.findById(1L);
        PokemonResponseDTO pokemonActualDto=pokemonService.convertToPokemonResponseDto(pokemonActual);

        assertEquals(pokemonExpected,pokemonActualDto);

    }

    @Test
    void obtenirUnPokemonInexistantTest() {
        assertThrows(PokemonNotFoundException.class,()->{
            pokemonService.findById(2L);
        });

    }

    @Test
    void ajouterUnPokemonSuccesTest() {

        Pokemon pokemonSend = Pokemon.builder()
                .numero(2)
                .name("Ivysaur")
                .type1("Grass")
                .type2("Poison")
                .total(405)
                .hp(60)
                .attack(62)
                .defense(63)
                .spAtk(80)
                .spDef(80)
                .speed(60)
                .generation(1)
                .legendary(false)
                .build();

        Pokemon pokemonExpected = Pokemon.builder()
                .id(2L)
                .numero(2)
                .name("Ivysaur")
                .type1("Grass")
                .type2("Poison")
                .total(405)
                .hp(60)
                .attack(62)
                .defense(63)
                .spAtk(80)
                .spDef(80)
                .speed(60)
                .generation(1)
                .legendary(false)
                .build();

        assertEquals(pokemonExpected,pokemonService.save(pokemonSend));

    }

    @Test
    void ajouterUnPokemonEchecTest() {
        Pokemon pokemon3 = Pokemon.builder()
                .numero(3)
                .name("Venusaur")
                .type1("Grass")
                .type2("Poison")
                .total(525)
                .hp(80)
                .attack(82)
                .defense(83)
                .spAtk(100)
                .spDef(100)
                .speed(80)
                .generation(1)
                .legendary(false)
                .build();
        PokemonRequestDTO pokemonRequestDto3 = PokemonRequestDTO.builder()
                .numero(3)
                .name("Venusaur")
                .type1("Grass")
                .type2("Poison")
                .total(525)
                .hp(80)
                .attack(82)
                .defense(83)
                .spAtk(100)
                .spDef(100)
                .speed(80)
                .generation(1)
                .legendary(false)
                .build();
        //n'a pas été enregistrée le request
        when(pokemonRepository.save(pokemon3)).thenThrow(new IllegalArgumentException());
        assertThrows(PokemonSaveException.class,()->{
            pokemonService.ajouterPokemon(pokemonRequestDto3);
        });
    }



    @Test
    void convertToPokemonResponseDtoSuccesTest() {
        Pokemon pokemon1 = Pokemon.builder()
                .id(1L)
                .numero(1)
                .name("Bulbasaur")
                .type1("Grass")
                .type2("Poison")
                .total(318)
                .hp(45)
                .attack(49)
                .defense(49)
                .spAtk(65)
                .spDef(65)
                .speed(45)
                .generation(1)
                .legendary(false)
                .build();
        PokemonResponseDTO pokemonResponseDTO1 = PokemonResponseDTO.builder()
                .id(1L)
                .numero(1)
                .name("Bulbasaur")
                .type1("Grass")
                .type2("Poison")
                .total(318)
                .hp(45)
                .attack(49)
                .defense(49)
                .spAtk(65)
                .spDef(65)
                .speed(45)
                .generation(1)
                .legendary(false)
                .build();

        assertEquals(pokemonResponseDTO1,pokemonService.convertToPokemonResponseDto(pokemon1));
    }

    @Test
    void convertToPokemonSuccesTest() {
        Pokemon pokemon1 = Pokemon.builder()
                .numero(1)
                .name("Bulbasaur")
                .type1("Grass")
                .type2("Poison")
                .total(318)
                .hp(45)
                .attack(49)
                .defense(49)
                .spAtk(65)
                .spDef(65)
                .speed(45)
                .generation(1)
                .legendary(false)
                .build();
        PokemonRequestDTO pokemonRequestDTO1 = PokemonRequestDTO.builder()
                .numero(1)
                .name("Bulbasaur")
                .type1("Grass")
                .type2("Poison")
                .total(318)
                .hp(45)
                .attack(49)
                .defense(49)
                .spAtk(65)
                .spDef(65)
                .speed(45)
                .generation(1)
                .legendary(false)
                .build();
        assertEquals(pokemon1,pokemonService.convertToPokemon(pokemonRequestDTO1));
    }
}