package com.shweeb.RickAndMortyWebsite.service;

import com.shweeb.RickAndMortyWebsite.client.RickAndMortyApiClient;
import com.shweeb.RickAndMortyWebsite.dto.GameCharacterDto;
import com.shweeb.RickAndMortyWebsite.dto.RevealLetterDto;
import com.shweeb.RickAndMortyWebsite.dto.GuessResultDto;
import com.shweeb.RickAndMortyWebsite.dto.CharacterResultDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class GameService {

    private static final String SESSION_ANSWER = "answer";
    private static final String SESSION_REVEALED = "revealedIndexes";

    private final RickAndMortyApiClient apiClient;
    private final Random random = new Random();

    public GameService(RickAndMortyApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Starts a new game
     */
    public GameCharacterDto startGame(HttpSession session) {

        CharacterResultDto character = fetchRandomCharacter();

        session.setAttribute(SESSION_ANSWER, character.getName());
        session.setAttribute(SESSION_REVEALED, new HashSet<Integer>());

        return new GameCharacterDto(
                character.getName().length(),
                character.getImage(),
                character.getSpecies(),
                character.getStatus(),
                character.getGender()
        );
    }

    /**
     * Reveals one unrevealed letter
     */
    public RevealLetterDto revealLetter(HttpSession session) {

        String answer = (String) session.getAttribute(SESSION_ANSWER);
        Set<Integer> revealed =
                (Set<Integer>) session.getAttribute(SESSION_REVEALED);

        if (answer == null || revealed == null) {
            throw new IllegalStateException("Game not started");
        }

        List<Integer> availableIndexes = new ArrayList<>();

        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) != ' ' && !revealed.contains(i)) {
                availableIndexes.add(i);
            }
        }

        if (availableIndexes.isEmpty()) {
            throw new IllegalStateException("No letters left to reveal");
        }

        int index =
                availableIndexes.get(random.nextInt(availableIndexes.size()));

        revealed.add(index);
        session.setAttribute(SESSION_REVEALED, revealed);

        return new RevealLetterDto(index, answer.charAt(index));
    }

    /**
     * Checks the user's guess
     */
    public GuessResultDto checkGuess(String guess, HttpSession session) {

        String answer = (String) session.getAttribute(SESSION_ANSWER);

        if (answer == null) {
            throw new IllegalStateException("Game not started");
        }

        boolean correct =
                answer.equalsIgnoreCase(guess.trim());

        return new GuessResultDto(correct);
    }

    /**
     * Fetches a random character safely
     */
    private CharacterResultDto fetchRandomCharacter() {

        while (true) {
            int randomId = random.nextInt(826) + 1;

            try {
                return apiClient.getCharacterById(randomId);
            } catch (HttpClientErrorException.NotFound ignored) {
                // Retry on missing ID
            }
        }
    }
}
