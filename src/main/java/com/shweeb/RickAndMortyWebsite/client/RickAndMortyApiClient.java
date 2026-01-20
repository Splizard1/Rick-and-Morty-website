package com.shweeb.RickAndMortyWebsite.client;

import com.shweeb.RickAndMortyWebsite.dto.CharacterResultDto;
import com.shweeb.RickAndMortyWebsite.dto.RickAndMortyApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class RickAndMortyApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<CharacterResultDto> searchAllCharactersByName(String name) {

        List<CharacterResultDto> allCharacters = new ArrayList<>();

        String url = UriComponentsBuilder
                .fromUriString("https://rickandmortyapi.com/api/character/")
                .queryParam("name", name)
                .toUriString();

        while (url != null) {
            RickAndMortyApiResponse response =
                    restTemplate.getForObject(url, RickAndMortyApiResponse.class);

            if (response == null) {
                break;
            }

            allCharacters.addAll(response.getResults());
            url = response.getInfo().getNext();
        }

        return allCharacters;
    }

    public CharacterResultDto getCharacterById(int id) {
        String url = "https://rickandmortyapi.com/api/character/" + id;
        return restTemplate.getForObject(url, CharacterResultDto.class);
    }

}
