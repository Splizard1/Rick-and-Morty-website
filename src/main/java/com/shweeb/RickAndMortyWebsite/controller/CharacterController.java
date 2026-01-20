package com.shweeb.RickAndMortyWebsite.controller;

import com.shweeb.RickAndMortyWebsite.dto.CharacterResponseDto;
import com.shweeb.RickAndMortyWebsite.service.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public List<CharacterResponseDto> getCharacters(
            @RequestParam String name) {

        return characterService.getCharactersByName(name);
    }
}
