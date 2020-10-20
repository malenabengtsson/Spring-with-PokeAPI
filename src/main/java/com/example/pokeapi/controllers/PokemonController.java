package com.example.pokeapi.controllers;

import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/{name}")
    public ResponseEntity<List<Pokemon>> findAllPokemons(@PathVariable String name){
        var pokemons = pokemonService.findAllPokemonWith(name);
         return ResponseEntity.ok(pokemons);
    }
}
