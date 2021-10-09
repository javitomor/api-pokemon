package com.example.apipokemon.rest;

import com.example.apipokemon.configuration.ApiVersion;
import com.example.apipokemon.enums.SortFieldsPokemonEnum;
import com.example.apipokemon.lang.ApiError;
import com.example.apipokemon.lang.MessageResource;
import com.example.apipokemon.payload.request.PokemonRequestDTO;
import com.example.apipokemon.payload.response.MessageResponse;
import com.example.apipokemon.payload.response.PokemonResponseDTO;
import com.example.apipokemon.payload.response.PokemonsListResponseDTO;
import com.example.apipokemon.service.IPokemonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {
    //TODO traducir todos los mensajes de messages.properties
    @Autowired
    IPokemonService pokemonService;
    @Autowired
    MessageResource messageResource;

    @GetMapping(value = ApiVersion.V1)
    @ApiOperation(value = "Renvoie tous les Pokémon dans une liste paginée", response = PokemonsListResponseDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Tous les Pokémon ont été obtenus correctement", response = PokemonsListResponseDTO.class)})
    public ResponseEntity<PokemonsListResponseDTO> getAll(
            @ApiParam(value = "Numéro de page") @RequestParam Integer numeroPage,
            @ApiParam(value = "Nombre de Elements") @RequestParam Integer nombreElements,
            @ApiParam(value = "Champ de tri") @RequestParam SortFieldsPokemonEnum champTri
    ) {
        //DONE getAll paginado
        //TODO test


        PokemonsListResponseDTO pokemonsListResponseDTO = PokemonsListResponseDTO.builder().build();
        Pageable pageable = PageRequest.of(numeroPage, nombreElements, Sort.by(champTri.getColumn()));

        pokemonsListResponseDTO.setPokemons(pokemonService.getAllPaginated(pageable));

        return ResponseEntity.ok(pokemonsListResponseDTO);
    }

    @GetMapping(value = ApiVersion.V1 + "/{id}")
    @ApiOperation(value = "Renvoie un Pokémon", response = PokemonResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pokémon a été obtenu avec succès", response = PokemonResponseDTO.class),
            @ApiResponse(code = 404, message = "Pokémon introuvable", response = ApiError.class)
    })
    public ResponseEntity<PokemonResponseDTO> get(
            @ApiParam(value = "ID du Pokémon que nous voulons") @PathVariable("id") Long id
    ) {
        //DONE get
        //TODO test

        return ResponseEntity.ok(pokemonService.getPokemon(id));
    }

    @PostMapping(value = ApiVersion.V1)
    @ApiOperation(value = "Ajouter un Pokémon à la liste", response = MessageResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pokémon a été creéé avec succès", response = MessageResponse.class),
            @ApiResponse(code = 422, message = "Erreur de données de request", response = ApiError.class)
    })
    public ResponseEntity<MessageResponse> post(
            @ApiParam("Les données du Pokémon") @RequestBody PokemonRequestDTO pokemonRequestDTO
    ) {
        //DONE post
        //TODO test
        pokemonService.ajouterPokemon(pokemonRequestDTO);

        return ResponseEntity.ok(new MessageResponse(messageResource.toLocale("success.pokemon.created")));
    }

    @PutMapping(value = ApiVersion.V1 + "/{id}")
    @ApiOperation(value = "Mettre à jour les données d'un Pokémon", response = MessageResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Le Pokémon a été mis à jour avec succès", response = MessageResponse.class)})
    public ResponseEntity<MessageResponse> put(
            @ApiParam("Les données du Pokémon") @RequestBody PokemonRequestDTO pokemonRequestDTO,
            @ApiParam(value = "ID du Pokémon que nous voulons") @PathVariable("id") Long id
    ) {
        //DONE put
        //TODO test
        pokemonService.updatePokemon(id, pokemonRequestDTO);
        return ResponseEntity.ok(new MessageResponse(messageResource.toLocale("success.pokemon.update")));

    }

    @DeleteMapping(value = ApiVersion.V1 + "/{id}")
    @ApiOperation(value = "Supprimer un Pokémon de la liste", response = MessageResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pokémon a été supprimé avec succès", response = MessageResponse.class),
            @ApiResponse(code = 422, message = "Pokémon n'a pas pu être supprimé", response = ApiError.class)
    })
    public ResponseEntity<MessageResponse> delete(
            @ApiParam(value = "ID du Pokémon que nous voulons") @PathVariable("id") Long id
    ) {
        //DONE delete
        //TODO test
        pokemonService.delete(id);
        return ResponseEntity.ok(new MessageResponse(messageResource.toLocale("success.pokemon.deleted")));
    }
}
