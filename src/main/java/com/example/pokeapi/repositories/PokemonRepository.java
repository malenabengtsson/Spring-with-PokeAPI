package com.example.pokeapi.repositories;

import com.example.pokeapi.entities.PokemonEntities.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends MongoRepository<Pokemon, String> {

    Pokemon findByName(String name);
}
