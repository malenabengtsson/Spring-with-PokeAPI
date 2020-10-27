package com.example.pokeapi.repositories;

import com.example.pokeapi.entities.GameIndice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameIndiceRepository extends MongoRepository<GameIndice, String> {
    GameIndice findByGameVersion(String gameVersion);
}
