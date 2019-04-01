package com.duolingo.challenges.data.parsers;

import com.duolingo.challenges.data.models.Challenge;
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

    @Inject
    public TranslationParser() {

    }

    public Translation parseTranslation(String translationString) {
        Challenge challenge = new Gson().fromJson(translationString, Challenge.class);
        int gridSize = challenge.character_grid.size();
        List<String> characters = createCharacterList(challenge.character_grid, gridSize);

        List<WordLocation> locations = createLocationList(challenge.word_locations);

        return new Translation(
                challenge.source_language,
                challenge.target_language,
                challenge.word,
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