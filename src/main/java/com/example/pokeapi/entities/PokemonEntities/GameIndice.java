package com.example.pokeapi.entities.PokemonEntities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "GameIndice")
public class GameIndice {

    @Id
    private String id;
    private String gameVersion;
    private String gameUrl;

    public GameIndice() {
    }

    public GameIndice(String gameVersion, String gameUrl) {
        this.gameVersion = gameVersion;
        this.gameUrl = gameUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }
}
