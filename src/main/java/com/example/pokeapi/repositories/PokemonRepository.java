package com.example.pokeapi.repositories;

import com.example.pokeapi.entities.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepository extends MongoRepository<Pokemon, String> {
Pokemon findByName(String name);
}
