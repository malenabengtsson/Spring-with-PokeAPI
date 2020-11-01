package com.example.pokeapi.dto;

import com.example.pokeapi.dto.PokemonDetailDtos.ResultDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeNamesAndUrlDto {
    @JsonProperty("results")
    private ResultDto types;

    public TypeNamesAndUrlDto() {
    }

    public TypeNamesAndUrlDto(ResultDto types) {
        this.types = types;
    }

    public ResultDto getTypes() {
        return types;
    }

    public void setTypes(ResultDto types) {
        this.types = types;
    }
}
