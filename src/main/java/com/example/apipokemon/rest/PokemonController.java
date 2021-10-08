package com.example.apipokemon.rest;

import com.example.apipokemon.configuration.ApiVersion;
import com.example.apipokemon.enums.SortFieldsPokemonEnum;
import com.example.apipokemon.lang.MessageResource;
import com.example.apipokemon.payload.request.PaginatedRequest;
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
    @Autowired
    IPokemonService pokemonService;
    @Autowired
    MessageResource messageResource;

    @GetMapping(value = ApiVersion.V1)
    @ApiOperation(value = "Renvoie tous les Pokémon dans une liste paginée", response = PokemonsListResponseDTO.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Tous les Pokémon ont été obtenus correctement",response = PokemonsListResponseDTO.class)})
    public ResponseEntity<PokemonsListResponseDTO> getAll(
            @ApiParam(value = "Numéro de page") @RequestParam Integer numeroPage,
            @ApiParam(value = "Nombre de Elements") @RequestParam Integer nombreElements,
            @ApiParam(value = "Champ de tri") @RequestParam SortFieldsPokemonEnum champTri
            ) {
        //TODO getAll paginado

        PokemonsListResponseDTO pokemonsListResponseDTO = PokemonsListResponseDTO.builder().build();
        Pageable pageable = PageRequest.of(numeroPage,nombreElements, Sort.by(champTri.getColumn()));

        pokemonsListResponseDTO.setPokemons(pokemonService.getAllPaginated(pageable));

        return ResponseEntity.ok(pokemonsListResponseDTO);
    }

    @GetMapping(value=ApiVersion.V1+"/{id}")
    @ApiOperation(value = "Renvoie un Pokémon", response = PokemonResponseDTO.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Le Pokémon a été obtenu avec succès",response = PokemonResponseDTO.class)})
    public ResponseEntity<PokemonResponseDTO> get(
            @ApiParam(value = "ID du Pokémon que nous voulons") @PathVariable("id") Long id
    ) {
        //TODO get
        PokemonResponseDTO pokemonResponseDTO = PokemonResponseDTO.builder().build();
        return ResponseEntity.ok(pokemonResponseDTO);
    }

    @PostMapping(value=ApiVersion.V1)
    @ApiOperation(value = "Ajouter un Pokémon à la liste", response = MessageResponse.class)
    @ApiResponses(value={@ApiResponse(code=201,message = "Le Pokémon a été creéé avec succès",response = MessageResponse.class)})
    public ResponseEntity<MessageResponse> post(
            @ApiParam("Les données du Pokémon") @RequestBody PokemonRequestDTO pokemonResponseDTO
    ) {
        //TODO post
//TODO debo obtener el id de la lista, el ultimo disponible

        return ResponseEntity.ok(new MessageResponse(messageResource.toLocale("success.pokemon.created")));
    }

    @PutMapping(value=ApiVersion.V1+"/{id}")
    @ApiOperation(value = "Mettre à jour les données d'un Pokémon", response = MessageResponse.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Le Pokémon a été mis à jour avec succès",response = MessageResponse.class)})
    public ResponseEntity<MessageResponse> put(
            @ApiParam("Les données du Pokémon") @RequestBody PokemonRequestDTO pokemonResponseDTO,
            @ApiParam(value = "ID du Pokémon que nous voulons") @PathVariable("id") Long id
    ) {
//TODO put

        return ResponseEntity.ok(new MessageResponse(messageResource.toLocale("success.pokemon.update")));

    }

    @DeleteMapping(value=ApiVersion.V1+"/{id}")
    @ApiOperation(value="Supprimer un Pokémon de la liste",response = MessageResponse.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Le Pokémon a été supprimé avec succès",response = MessageResponse.class)})
    public ResponseEntity<MessageResponse>delete(
            @ApiParam(value = "ID du Pokémon que nous voulons") @PathVariable("id") Long id
    ){
        //TODO delete

        return ResponseEntity.ok(new MessageResponse(messageResource.toLocale("success.pokemon.deleted")));
    }
}
