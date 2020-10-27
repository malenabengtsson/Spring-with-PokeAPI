package com.example.pokeapi.services;

import com.example.pokeapi.dto.PokemonDetailDtos.Abilities.AbilitiesDto;
import com.example.pokeapi.dto.PokemonDetailDtos.Abilities.AbilityDto;
import com.example.pokeapi.dto.PokemonDto;
import com.example.pokeapi.entities.Ability;
import com.example.pokeapi.entities.Pokemon;
import com.example.pokeapi.repositories.AbilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class AbilityService {

     @Autowired
     private AbilityRepository abilityRepository;


    public List<Ability> getAbility(PokemonDto pokemon){
        List<Ability> chosenAbility = new ArrayList<>();

        for(AbilitiesDto abilities : pokemon.getAbilities()){
            var abilityName = abilities.getAbility().getName().replace("-", " ");
            var abilityExist = abilityRepository.findByName(abilityName);
            if(abilityExist == null){
                var newAbility = new Ability(abilityName, abilities.getAbility().getUrl());
                this.saveAbility(newAbility);
                var fetchedAbility = abilityRepository.findByName(abilityName);
                chosenAbility.add(fetchedAbility);
        }
            else{
                chosenAbility.add(abilityExist);
            }
        }

        return chosenAbility;
    }

    public void saveAbility(Ability newAbility){
        abilityRepository.save(newAbility);
    }

}
