package com.shweeb.RickAndMortyWebsite.dto;

public class CharacterResponseDto {

    private String name;
    private String status;
    private String species;
    private String gender;
    private String origin;
    private String location;
    private String image;

    public CharacterResponseDto(String name, String status, String species, String gender, String origin, String location, String image) {
        this.name = name;
        this.status = status;
        this.species = species;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getGender() {
        return gender;
    }

    public String getOrigin() {
        return origin;
    }

    public String getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }
}
