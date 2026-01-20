package com.shweeb.RickAndMortyWebsite.dto;

public class GameCharacterDto {
    private final int nameLength;
    private final String image;
    private final String species;
    private final String status;
    private final String gender;

    public GameCharacterDto(int nameLength,
                            String image,
                            String species,
                            String status,
                            String gender) {

        this.nameLength = nameLength;
        this.image = image;
        this.species = species;
        this.status = status;
        this.gender = gender;
    }

    public int getNameLength() {
        return nameLength;
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
}
