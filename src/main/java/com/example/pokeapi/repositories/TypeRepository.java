package com.example.pokeapi.repositories;

import com.example.pokeapi.entities.PokemonEntities.Type;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends MongoRepository<Type, String> {
    Type findByName(String name);
}
