package com.example.pokeapi.dto.PokemonDetailDtos.GameIndices;

public class GameIndiceDto {

    private String name;
    private String url;

    public GameIndiceDto() {
    }

    public GameIndiceDto(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
