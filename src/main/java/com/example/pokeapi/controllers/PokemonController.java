package com.example.pokeapi.controllers;

import com.example.pokeapi.dto.PokemonNameAndUrlDto;
import com.example.pokeapi.entities.PokemonEntities.*;
import com.example.pokeapi.services.*;
import com.example.pokeapi.services.PokemonServices.Dtos.PokemonNamesAndUrlDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Get a pokemon by different attributes: name, type, ability and/or game")
    @ApiResponses(value = {@ApiResponse (responseCode = "200", description = "Found pokemon with chosen parameters",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Pokemon.class))}),
    @ApiResponse(responseCode = "404", description = "No pokemon found with chosen combination", content = @Content)})
    @GetMapping("/pokemon")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<List<Pokemon>> findPokemonByAttributes(@Parameter(description = "Name of pokemon") @RequestParam(required = false) String name,
                                                                 @Parameter(description = "Name of game")@RequestParam(required = false) String game,
                                                                 @Parameter(description = "Type of pokemon")@RequestParam(required = false) String type,
                                                                 @Parameter(description = "Name of ability")@RequestParam(required = false) String ability) {
        var getPokemon = queryService.findPokemonByAttributes(name, game, type, ability);
        return ResponseEntity.ok(getPokemon);
    }


    @Operation(summary = "Get all pokemon  names and url")
    @ApiResponses(value = {@ApiResponse (responseCode = "200", description = "Found pokemons",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PokemonNamesAndUrl.class))})})
    @GetMapping("/getAllpokemonNames")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<PokemonNameAndUrlDto> getAllPokemons() {
        var answer = pokemonNamesAndUrlDtoService.getAllPokemons();
        return ResponseEntity.ok(answer);

    }

    @Operation(summary = "Get a pokemongame")
    @ApiResponses(value = {@ApiResponse (responseCode = "200", description = "Found game with chosen name",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameIndice.class))}),
            @ApiResponse(responseCode = "404", description = "No games found with chosen name", content = @Content)})
    @GetMapping("/game")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<GameIndice> getGame(@RequestParam String name) {
        var answer = queryService.findGame(name);
        return ResponseEntity.ok(answer);
    }

    @Operation(summary = "Get ability and get a list of pokemons that has the chosen ability")
    @ApiResponses(value = {@ApiResponse (responseCode = "200", description = "Found ability with chosen name",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameIndice.class))}),
            @ApiResponse(responseCode = "404", description = "No ability found with chosen name", content = @Content)})
    @GetMapping("/ability")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Ability> getAbility(@RequestParam String name) {
        var answer = queryService.findAbility(name);
        return ResponseEntity.ok(answer);
    }
    @Operation(summary = "Get type and get a list of pokemons with chosen type")
    @ApiResponses(value = {@ApiResponse (responseCode = "200", description = "Found type with chosen name",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameIndice.class))}),
            @ApiResponse(responseCode = "404", description = "No type found with chosen name", content = @Content)})
    @GetMapping("/type")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Type> getType(@RequestParam String name) {
        var answer = queryService.findType(name);
        return ResponseEntity.ok(answer);

    }

}
