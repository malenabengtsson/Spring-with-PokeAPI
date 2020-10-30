package com.example.pokeapi.services;

import com.example.pokeapi.dto.PokemonDetailDtos.Abilities.AbilitiesDto;
import com.example.pokeapi.dto.PokemonDetailDtos.Abilities.AbilityDto;
import com.example.pokeapi.entities.Ability;
import com.example.pokeapi.repositories.AbilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
           var fetchedAbility = restTemplate.getForObject(abilityUrl, AbilitiesDto.class);
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
