package com.example.pokeapi.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class TypeDtoService {

    private final RestTemplate restTemplate;

    public TypeDtoService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


}
