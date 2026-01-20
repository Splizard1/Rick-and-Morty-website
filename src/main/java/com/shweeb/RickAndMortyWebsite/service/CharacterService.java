package com.shweeb.RickAndMortyWebsite.service;

import com.shweeb.RickAndMortyWebsite.client.RickAndMortyApiClient;
import com.shweeb.RickAndMortyWebsite.dto.CharacterResponseDto;
import com.shweeb.RickAndMortyWebsite.dto.CharacterResultDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterService {

    private final RickAndMortyApiClient apiClient;

    public CharacterService(RickAndMortyApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<CharacterResponseDto> getCharactersByName(String name) {
        List<CharacterResultDto> characters =
                apiClient.searchAllCharactersByName(name);

        return characters.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private CharacterResponseDto mapToResponseDto(CharacterResultDto character) {
        return new CharacterResponseDto(
                character.getName(),
                character.getStatus(),
                character.getSpecies(),
                character.getGender(),
                character.getOrigin().getName(),
                character.getLocation().getName(),
                character.getImage()
        );
    }
}