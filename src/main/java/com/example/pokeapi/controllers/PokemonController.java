package com.example.pokeapi.controllers;

import com.example.pokeapi.dto.PokemonNameAndUrlDto;
import com.example.pokeapi.entities.PokemonEntities.Ability;
import com.example.pokeapi.entities.PokemonEntities.GameIndice;
import com.example.pokeapi.entities.PokemonEntities.Pokemon;
import com.example.pokeapi.entities.PokemonEntities.Type;
import com.example.pokeapi.services.*;
import com.example.pokeapi.services.PokemonServices.Dtos.PokemonNamesAndUrlDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PokemonController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private PokemonNamesAndUrlDtoService pokemonNamesAndUrlDtoService;

    @GetMapping("/pokemon")
    public ResponseEntity<List<Pokemon>> findPokemonByAttributes(@RequestParam(required = false) String name, @RequestParam(required = false) String game, @RequestParam(required = false) String type, @RequestParam(required = false) String ability) {
        var getPokemon = queryService.findPokemonByAttributes(name, game, type, ability);
        return ResponseEntity.ok(getPokemon);
    }


    @GetMapping("/getAllpokemonNames")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<PokemonNameAndUrlDto> getAllPokemons() {
        var answer = pokemonNamesAndUrlDtoService.getAllPokemons();
        return ResponseEntity.ok(answer);

    }


    @GetMapping("/game")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<GameIndice> getGame(@RequestParam String name) {
        var answer = queryService.findGame(name);
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/ability")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Ability> getAbility(@RequestParam String name) {
        var answer = queryService.findAbility(name);
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/type")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Type> getType(@RequestParam String name) {
        var answer = queryService.findType(name);
        return ResponseEntity.ok(answer);

    }

}
