package com.example.pokeapi.services.PokemonServices.Dtos;

import com.example.pokeapi.dto.PokemonDetailDtos.Abilities.AbilitiesDto;
import com.example.pokeapi.dto.PokemonDetailDtos.Abilities.AbilityDto;
import com.example.pokeapi.entities.PokemonEntities.Ability;
import com.example.pokeapi.repositories.AbilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@ConfigurationProperties(value = "pokemon.api", ignoreUnknownFields = false)

public class AbilityDtoService {

    private final RestTemplate restTemplate;
    private String url;

    @Autowired
    private AbilityRepository abilityRepository;

    public AbilityDtoService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    public Ability getAbility(String name){
       var correctAbility = name.replace(" ", "-");
       var abilityUrl = url + "ability/" + correctAbility;
       var abilityExists = abilityRepository.findByName(name);
       if(abilityExists == null){
           var newAbility = new Ability();
           List<String> linkedPokemons = new ArrayList<>();
           AbilitiesDto fetchedAbility = null;
           try{
               fetchedAbility = restTemplate.getForObject(abilityUrl, AbilitiesDto.class);
           } catch(Exception exception){
               throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No ability found with name: " + name);
           }

           newAbility.setName(fetchedAbility.getName().replace("-", " "));
           for(AbilityDto abilityName : fetchedAbility.getPokemon()){
               linkedPokemons.add(abilityName.getPokemon().getName());

           }
           newAbility.setLinkedPokemons(linkedPokemons);
           return newAbility;
       }
       return abilityExists;

    }

    public void setUrl(String url) {
        this.url = url;
    }
}
