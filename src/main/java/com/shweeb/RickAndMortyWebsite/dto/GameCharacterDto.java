package com.shweeb.RickAndMortyWebsite.dto;

import java.util.Map;

public class GameCharacterDto {
    private int nameLength;
    private Map<Integer, Character> revealedCharacters;
    private String image;
    private String species;
    private String status;
    private String gender;

    public GameCharacterDto(int nameLength, Map<Integer, Character> revealedCharacters, String image, String species, String status, String gender) {
        this.nameLength = nameLength;
        this.revealedCharacters = revealedCharacters;
        this.image = image;
        this.species = species;
        this.status = status;
        this.gender = gender;
    }

    // Getters and setters
    public int getNameLength() {
        return nameLength;
    }

    public Map<Integer, Character> getRevealedCharacters() {
        return revealedCharacters;
    }

    public String getImage() {
        return image;
    }

    public String getSpecies() {
        return species;
    }

    public String getStatus() {
        return status;
    }

    public String getGender() {
        return gender;
    }

    public void setNameLength(int nameLength) {
        this.nameLength = nameLength;
    }

    public void setRevealedCharacters(Map<Integer, Character> revealedCharacters) {
        this.revealedCharacters = revealedCharacters;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}