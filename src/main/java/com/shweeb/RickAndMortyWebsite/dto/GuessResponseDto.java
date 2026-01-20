package com.shweeb.RickAndMortyWebsite.dto;

public class GuessResponseDto {

    private boolean correct;
    private String correctAnswer;

    public GuessResponseDto(boolean correct, String correctAnswer) {
        this.correct = correct;
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
