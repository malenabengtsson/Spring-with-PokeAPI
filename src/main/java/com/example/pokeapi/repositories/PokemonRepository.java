package com.example.pokeapi.repositories;

import com.example.pokeapi.entities.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PokemonRepository extends MongoRepository<Pokemon, String> {

}
