package com.example.apipokemon.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonsListResponseDTO implements Serializable {
    private Page<PokemonResponseDTO> pokemons;
}
