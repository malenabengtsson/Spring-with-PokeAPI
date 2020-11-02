package com.example.pokeapi.services.PokemonServices;

import com.example.pokeapi.entities.PokemonEntities.Pokemon;
import com.example.pokeapi.entities.PokemonEntities.PokemonNamesAndUrl;
import com.example.pokeapi.repositories.PokemonRepository;
import com.example.pokeapi.services.PokemonServices.Dtos.PokemonDtoService;
import com.example.pokeapi.services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonService {

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    private QueryService queryService;

    @Autowired
    private PokemonDtoService pokemonDtoService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private AbilityService abilityService;

    @Autowired
    private GameIndiceService gameIndiceService;

    public List<Pokemon> findPokemon(String combination, String name, String game, String type, String ability) {
        if (combination.equals("Name")) {
            return this.findPokemonByName(name);
        }
        if (combination.equals("NameGameTypeAbility")) {
            return this.findPokemonByNameGameTypeAndAbility(name, game, type, ability);
        }
        if (combination.equals("GameType")) {
            return this.findPokemonByGameAndType(game, type);
        }
        if (combination.equals("NameGameType")) {
            return this.findPokemonByNameGameAndType(name, game, type);
        }
        if (combination.equals("NameGame")) {
            return this.findPokemonByNameAndGame(name, game);
        }
        if (combination.equals("NameType")) {
            return this.findPokemonByNameAndType(name, type);
        }
        if (combination.equals("GameTypeAbility")) {
            return this.findPokemonByGameTypeAndAbility(game, type, ability);
        }
        if (combination.equals("NameTypeAbility")) {
            return this.findPokemonByNameTypeAndAbility(name, type, ability);
        }
        if(combination.equals("NameGameAbility")){
            return this.findPokemonByNameGameAndAbility(name, game, ability);
        }
        if(combination.equals("TypeAbility")){
            return this.findPokemonByTypeAndAbility(type, ability);
        }
        return null;

    }

    public List<Pokemon> findPokemonByNameGameTypeAndAbility(String name, String game, String type, String ability) {
        List<Pokemon> matchedPokemon = new ArrayList<>();
        var getPokemonNames = this.getPokemonNames(name);
        for (Pokemon pokemon : getPokemonNames) {
            var doesAbilityMatch = abilityService.doesAbilityEquals(ability, pokemon);
            var doesGameMatch = gameIndiceService.doesGameEquals(game, pokemon);
            var doesTypeMatch = typeService.doesTypeEquals(type, pokemon);
            if (doesAbilityMatch && doesGameMatch && doesTypeMatch) {
                matchedPokemon.add(this.checkIfPokemonExistsInDb(pokemon));
            }
        }
        if (matchedPokemon.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found with that combination");
        }
        return matchedPokemon;
    }

    public List<Pokemon> findPokemonByName(String name) {
        List<Pokemon> foundPokemons = new ArrayList<>();

        var fetchedPokemon = this.getPokemonNames(name);

        for (Pokemon pokemon : fetchedPokemon) {
            foundPokemons.add(this.checkIfPokemonExistsInDb(pokemon));
        }
        if (foundPokemons.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found with that name");
        }
        return foundPokemons;
    }

    public List<Pokemon> findPokemonByGameAndType(String game, String type) {
        List<Pokemon> matchedPokemons = new ArrayList<>();
        var pokemonsWithChosenType = typeService.getPokemonsWithType(type);

        for (Pokemon pokemon : pokemonsWithChosenType) {
            var doesGameMatch = gameIndiceService.doesGameEquals(game, pokemon);
            if (doesGameMatch) {
                matchedPokemons.add(this.checkIfPokemonExistsInDb(pokemon));
            }
        }
        if (matchedPokemons.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found with that combination");
        } else {
            return matchedPokemons;
        }
    }

    public List<Pokemon> findPokemonByNameGameAndType(String name, String game, String type) {
        var fetchedPokemons = this.getPokemonNames(name);
        List<Pokemon> matchedPokemon = new ArrayList<>();
        if (fetchedPokemons != null) {
            for (Pokemon pokemon : fetchedPokemons) {
                var doesTypeMatch = typeService.doesTypeEquals(type, pokemon);
                var doesGameMatch = gameIndiceService.doesGameEquals(game, pokemon);
                if (doesTypeMatch && doesGameMatch) {
                    matchedPokemon.add(this.checkIfPokemonExistsInDb(pokemon));
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found with that combination");
        }
        if (matchedPokemon.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found with that combination");
        } else {
            return matchedPokemon;
        }
    }

    public List<Pokemon> findPokemonByNameAndGame(String name, String game) {
        var fetchedPokemons = this.getPokemonNames(name);
        List<Pokemon> matchedPokemon = new ArrayList<>();
        if (fetchedPokemons != null) {
            for (Pokemon pokemon : fetchedPokemons) {
                var doesGameMatch = gameIndiceService.doesGameEquals(game, pokemon);
                if (doesGameMatch) {
                    matchedPokemon.add(this.checkIfPokemonExistsInDb(pokemon));
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found with that combination");
        }
        if (matchedPokemon.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found with that combination");
        } else {
            return matchedPokemon;
        }

    }

    public List<Pokemon> findPokemonByNameAndType(String name, String type) {
        var fetchedPokemons = this.getPokemonNames(name);
        List<Pokemon> matchedPokemon = new ArrayList<>();
        if (!fetchedPokemons.isEmpty()) {
            for (Pokemon pokemon : fetchedPokemons) {
                var typeEquals = typeService.doesTypeEquals(type, pokemon);
                if (typeEquals) {
                    matchedPokemon.add(this.checkIfPokemonExistsInDb(pokemon));
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found with that combination");
        }
        if (matchedPokemon.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found with that combination");
        } else {
            return matchedPokemon;
        }
    }

    public List<Pokemon> findPokemonByGameTypeAndAbility(String game, String type, String ability) {
        List<Pokemon> matchedPokemons = new ArrayList<>();
        var pokemonsWithType = typeService.getPokemonsWithType(type);
        for (Pokemon pokemon : pokemonsWithType) {
            var gameExists = gameIndiceService.doesGameEquals(game, pokemon);
            var abilityExist = abilityService.doesAbilityEquals(ability, pokemon);
            if (gameExists && abilityExist) {
                matchedPokemons.add(pokemon);
            }
        }
        return matchedPokemons;
    }

    public List<Pokemon> findPokemonByNameTypeAndAbility(String name, String type, String ability) {
        var fetchedPokemons = this.getPokemonNames(name);
        List<Pokemon> matchedPokemons = new ArrayList<>();
        for (Pokemon pokemon : fetchedPokemons) {
            var typeExist = typeService.doesTypeEquals(type, pokemon);
            var abilityExist = abilityService.doesAbilityEquals(ability, pokemon);
            if (abilityExist && typeExist) {
                matchedPokemons.add(pokemon);
            }
        }
        return matchedPokemons;
    }
    public List<Pokemon> findPokemonByNameGameAndAbility(String name, String game, String ability){
        var fetchedPokemons = this.getPokemonNames(name);
        List<Pokemon> matchedPokemons = new ArrayList<>();
        for (Pokemon pokemon : fetchedPokemons) {
            var gameExist = gameIndiceService.doesGameEquals(game, pokemon);
            var abilityExist = abilityService.doesAbilityEquals(ability, pokemon);
            if (abilityExist && gameExist) {
                matchedPokemons.add(pokemon);
            }
        }
        return matchedPokemons;
    }
    public List<Pokemon> findPokemonByTypeAndAbility(String type, String ability){
        List<Pokemon> matchedPokemons = new ArrayList<>();
        var pokemonsWithType = typeService.getPokemonsWithType(type);
        for (Pokemon pokemon : pokemonsWithType) {
            var abilityExist = abilityService.doesAbilityEquals(ability, pokemon);
            if (abilityExist) {
                matchedPokemons.add(pokemon);
            }
        }
        return matchedPokemons;
    }

    public Pokemon getByName(String name) {
        var foundPokemon = pokemonRepository.findByName(name);
        if (foundPokemon == null) {
            return null;
        } else {
            return foundPokemon;
        }
    }

    public List<Pokemon> getPokemonNames(String name) {
        List<Pokemon> foundPokemon = new ArrayList<>();
        var getPokemonNamesContainingName = pokemonDtoService.findAllPokemonContaining(name);
        if (getPokemonNamesContainingName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemons found containing " + name);
        } else {
            for (PokemonNamesAndUrl pokemon : getPokemonNamesContainingName) {
                var pokemonExistInDb = this.getByName(pokemon.getName());
                if (pokemonExistInDb == null) {
                    var fetchedPokemon = pokemonDtoService.findAllPokemonWith(pokemon.getName());
                    this.savePokemon(fetchedPokemon);
                    var savedPokemon = this.getByName(pokemon.getName());
                    foundPokemon.add(savedPokemon);
                } else {
                    foundPokemon.add(pokemonExistInDb);
                }
            }
        }
        return foundPokemon;
    }

    public Pokemon checkIfPokemonExistsInDb(Pokemon pokemon) {
        var pokemonExistInDB = this.getByName(pokemon.getName());
        if (pokemonExistInDB == null) {
            var fetchedPokemon = pokemonDtoService.findAllPokemonWith(pokemon.getName());
            this.savePokemon(fetchedPokemon);
            var savedPokemon = this.getByName(pokemon.getName());
            return savedPokemon;
        } else {
            return pokemonExistInDB;
        }

    }

    public void savePokemon(Pokemon pokemon) {
        var newPokemon = new Pokemon(pokemon.getIndexNumber(), pokemon.getName(), pokemon.getHeight(), pokemon.getWeight(), pokemon.getType(), pokemon.getAbilities(), pokemon.getGame_indices());
        pokemonRepository.save(newPokemon);
    }

    public List<Pokemon> findAll() {
        return pokemonRepository.findAll();
    }

}
