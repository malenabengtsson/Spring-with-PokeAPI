package com.example.pokeapi.dto;

import com.example.pokeapi.dto.PokemonDetailDtos.ResultDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PokemonNameAndUrlDto {

    @JsonProperty("results")
    private List<ResultDto> resultDtos;


    public PokemonNameAndUrlDto() {
    }

    public PokemonNameAndUrlDto(List<ResultDto> resultDtos) {
        this.resultDtos = resultDtos;
    }

    public List<ResultDto> getResults() {
        return resultDtos;
    }

    public void setResults(List<ResultDto> resultDtos) {
        this.resultDtos = resultDtos;
    }
}
