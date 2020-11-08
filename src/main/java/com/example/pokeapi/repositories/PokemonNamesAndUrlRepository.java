package com.example.pokeapi.repositories;

import com.example.pokeapi.entities.PokemonEntities.PokemonNamesAndUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonNamesAndUrlRepository extends MongoRepository<PokemonNamesAndUrl, String> {

    Optional<PokemonNamesAndUrl> findByName(String name);


}
