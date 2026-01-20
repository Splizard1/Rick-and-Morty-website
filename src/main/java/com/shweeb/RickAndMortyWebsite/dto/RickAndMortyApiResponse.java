package com.shweeb.RickAndMortyWebsite.dto;

import java.util.List;

public class RickAndMortyApiResponse {

    private InfoDto info;
    private List<CharacterResultDto> results;

    public InfoDto getInfo() {
        return info;
    }

    public void setInfo(InfoDto info) {
        this.info = info;
    }

    public List<CharacterResultDto> getResults() {
        return results;
    }

    public void setResults(List<CharacterResultDto> results) {
        this.results = results;
    }
}
