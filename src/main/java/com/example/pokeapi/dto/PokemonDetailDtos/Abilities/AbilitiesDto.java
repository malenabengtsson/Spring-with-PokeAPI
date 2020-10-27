package com.example.pokeapi.dto.PokemonDetailDtos.Abilities;

import java.util.List;

public class AbilitiesDto {

    private AbilityDto ability;

    public AbilitiesDto() {
    }

    public AbilitiesDto(AbilityDto ability) {
        this.ability = ability;
    }

    public AbilityDto getAbility() {
        return ability;
    }

    public void setAbility(AbilityDto ability) {
        this.ability = ability;
    }
}
