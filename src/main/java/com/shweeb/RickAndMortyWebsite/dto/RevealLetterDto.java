package com.shweeb.RickAndMortyWebsite.dto;

public class RevealLetterDto {

    private int index;
    private char letter;

    public RevealLetterDto(int index, char letter) {
        this.index = index;
        this.letter = letter;
    }

    public int getIndex() {
        return index;
    }

    public char getLetter() {
        return letter;
    }
}
