package com.example.pokeapi.repositories;

import com.example.pokeapi.entities.Ability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbilityRepository extends MongoRepository<Ability, String> {

    Ability findByName(String name);
}
