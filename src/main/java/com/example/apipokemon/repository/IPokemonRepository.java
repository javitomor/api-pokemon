package com.example.apipokemon.repository;

import com.example.apipokemon.model.Pokemon;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPokemonRepository extends PagingAndSortingRepository<Pokemon, Long> {

}
