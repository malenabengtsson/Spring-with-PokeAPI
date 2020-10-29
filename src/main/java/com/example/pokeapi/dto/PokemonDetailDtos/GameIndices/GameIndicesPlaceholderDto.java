package com.example.pokeapi.dto.PokemonDetailDtos.GameIndices;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameIndicesPlaceholderDto {

    @JsonProperty("version")
    private GameIndicePlaceholderDto version;

    public GameIndicesPlaceholderDto() {
    }

    public GameIndicesPlaceholderDto(GameIndicePlaceholderDto version) {
        this.version = version;
    }

    public GameIndicePlaceholderDto getVersion() {
        return version;
    }

    public void setVersion(GameIndicePlaceholderDto version) {
        this.version = version;
    }
}
