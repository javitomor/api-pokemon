package com.example.apipokemon.configuration;

import com.example.apipokemon.model.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
@Slf4j
public class DBLogProcessor implements ItemProcessor<Pokemon,Pokemon> {

    public Pokemon process(Pokemon pokemon){
      log.info("Inserting Pokémon: "+pokemon);
      return pokemon;
    }
}
