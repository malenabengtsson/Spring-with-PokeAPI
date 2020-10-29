package com.example.pokeapi.dto.PokemonDetailDtos.Abilities;

public class AbilitiesPlaceholderDto {

    private AbilityPlaceholderDto ability;

    public AbilitiesPlaceholderDto() {
    }

    public AbilitiesPlaceholderDto(AbilityPlaceholderDto ability) {
        this.ability = ability;
    }

    public AbilityPlaceholderDto getAbility() {
        return ability;
    }

    public void setAbility(AbilityPlaceholderDto ability) {
        this.ability = ability;
    }
}
