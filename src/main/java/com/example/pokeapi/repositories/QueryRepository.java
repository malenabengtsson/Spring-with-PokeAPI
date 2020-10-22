package com.example.pokeapi.repositories;

import com.example.pokeapi.entities.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueryRepository extends MongoRepository<Query, String> {
    Optional<Query> findByQueryString(String queryString);
}
