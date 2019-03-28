package com.duolingo.challenges.data.parsers;

import com.duolingo.challenges.data.models.Puzzle;
import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.data.models.WordCoordinate;
import com.duolingo.challenges.data.models.WordLocation;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class TranslationParser {
    private static final String COMMA = ",";
    private static final String SOURCE_LANGUAGE = "source_language";
    private static final String TARGET_LANGUAGE = "target_language";
    private static final String WORD = "word";
    private static final String CHARACTER_GRID = "character_grid";
    private static final String WORD_LOCATIONS = "word_locations";

    @Inject
    public TranslationParser() {

    }

    public Translation parseTranslation(String translationString) {
        Puzzle puzzle = new Gson().fromJson(translationString, Puzzle.class);
        int gridSize = puzzle.character_grid.size();
        List<String> characters = createCharacterList(puzzle.character_grid, gridSize);

        List<WordLocation> locations = createLocationList(puzzle.word_locations);

        return new Translation(
                puzzle.source_language,
                puzzle.target_language,
                puzzle.word,
                gridSize,
                characters,
                locations);
    }

    private List<String> createCharacterList(List<List<String>> characterGrid, int gridSize) {
        List<String> characters = new ArrayList<>();
        List<String> row;
        for (int i = 0; i < gridSize; i++) {
            row = characterGrid.get(i);
            for (int j = 0; j < gridSize; j++) {
                characters.add(row.get(j));
            }
        }
        return characters;
    }

    private List<WordLocation> createLocationList(HashMap<String, String> wordLocations) {
        List<WordLocation> wordLocationsList = new ArrayList<>();
        String key;
        String value;
        List<WordCoordinate> wordCoordinates;
        for (String s : wordLocations.keySet()) {
            key = s;
            value = wordLocations.get(key);
            String[] coordinates = key.split(COMMA);
            wordCoordinates = new ArrayList<>();
            for (int j = 0; j < coordinates.length; j += 2) {
                int x = Integer.parseInt(coordinates[j + 1]);
                int y = Integer.parseInt(coordinates[j]);
                wordCoordinates.add(new WordCoordinate(x, y));
            }
            wordLocationsList.add(new WordLocation(wordCoordinates, value));
        }
        return wordLocationsList;
    }
}