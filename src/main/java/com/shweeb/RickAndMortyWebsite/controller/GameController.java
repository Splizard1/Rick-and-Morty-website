package com.shweeb.RickAndMortyWebsite.controller;

import com.shweeb.RickAndMortyWebsite.dto.GameCharacterDto;
import com.shweeb.RickAndMortyWebsite.dto.GuessRequest;
import com.shweeb.RickAndMortyWebsite.dto.RevealLetterDto;
import com.shweeb.RickAndMortyWebsite.dto.GuessResultDto;
import com.shweeb.RickAndMortyWebsite.service.GameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Starts a new game and stores the answer in session
     */
    @GetMapping("/start")
    public GameCharacterDto startGame(HttpSession session) {
        return gameService.startGame(session);
    }

    /**
     * Reveals one random unrevealed letter
     */
    @GetMapping("/reveal-letter")
    public RevealLetterDto revealLetter(HttpSession session) {
        return gameService.revealLetter(session);
    }

    /**
     * Submits a guess
     */
    @PostMapping("/guess")
    public GuessResultDto guess(
            @RequestBody GuessRequest request,
            HttpSession session) {

        return gameService.checkGuess(request.getGuess(), session);
    }
}
