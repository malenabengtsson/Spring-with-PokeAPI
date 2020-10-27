package com.example.pokeapi.dto.PokemonDetailDtos.GameIndices;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameIndicesDto {

    @JsonProperty("version")
    private GameIndiceDto version;

    public GameIndicesDto() {
    }

    public GameIndicesDto(GameIndiceDto version) {
        this.version = version;
    }

    public GameIndiceDto getVersion() {
        return version;
    }

    public void setVersion(GameIndiceDto version) {
        this.version = version;
    }
}
