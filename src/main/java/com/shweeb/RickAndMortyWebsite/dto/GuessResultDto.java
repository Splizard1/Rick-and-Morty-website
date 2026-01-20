package com.shweeb.RickAndMortyWebsite.dto;

public class GuessResultDto {

    private boolean correct;

    public GuessResultDto(boolean correct) {
        this.correct = correct;
    }

    public boolean isCorrect() {
        return correct;
    }
}
