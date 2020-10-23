package com.example.pokeapi.repositories;

import com.example.pokeapi.entities.PokemonNamesAndUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PokemonNamesAndUrlRepository extends MongoRepository<PokemonNamesAndUrl, String> {

    Optional<PokemonNamesAndUrl> findByName(String name);


}
