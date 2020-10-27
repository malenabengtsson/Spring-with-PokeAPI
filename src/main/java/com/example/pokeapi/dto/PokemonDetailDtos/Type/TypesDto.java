package com.example.pokeapi.dto.PokemonDetailDtos.Type;

import com.example.pokeapi.dto.PokemonDetailDtos.Type.TypeDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TypesDto {
    @JsonProperty("slot")
    private int slot;
    @JsonProperty("type")
    private TypeDto type;

    public TypesDto() {

    }

    public TypesDto(int slot, TypeDto type) {
        this.slot = slot;
        this.type = type;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public TypeDto getType() {
        return type;
    }

    public void setType(TypeDto type) {
        this.type = type;
    }
}
