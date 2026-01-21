package com.shweeb.RickAndMortyWebsite.controller;

import com.shweeb.RickAndMortyWebsite.client.RickAndMortyApiClient;
import com.shweeb.RickAndMortyWebsite.dto.CharacterResponseDto;
import com.shweeb.RickAndMortyWebsite.dto.CharacterResultDto;
import com.shweeb.RickAndMortyWebsite.service.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final RickAndMortyApiClient apiClient;
    private final CharacterService characterService;

    public CharacterController(RickAndMortyApiClient apiClient, CharacterService characterService) {
        this.apiClient = apiClient;
        this.characterService = characterService;
    }

    @GetMapping
    public List<CharacterResponseDto> getCharacters(
            @RequestParam String name) {

        return characterService.getCharactersByName(name);
    }

    @GetMapping("/{id}")
    public CharacterResultDto getCharacterById(@PathVariable int id) {
        return apiClient.getCharacterById(id);
    }
}

