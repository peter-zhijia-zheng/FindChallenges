package com.duolingo.challenges.data.parsers;

import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.data.models.WordCoordinate;
import com.duolingo.challenges.data.models.WordLocation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class TranslationParserTest {
    private static final String SOURCE_LANGUAGE = "sourceLanguage";
    private static final String TARGET_LANGUAGE = "targetLanguage";
    private static final String WORD = "word";
    private static final String CHAR1 = "char1";
    private static final String CHAR2 = "char2";
    private static final String CHAR3 = "char3";
    private static final String CHAR4 = "char4";
    private static final int LOCATION_X = 1;
    private static final int LOCATION_Y = 0;
    private static final String LOCATION_WORD = "n";

    private static final String TRANSLATION_ITEM = "{" +
            "\"source_language\": " + SOURCE_LANGUAGE + ", " +
            "\"word\": " + WORD + ", " +
            "\"character_grid\": [[" + CHAR1 + ", " + CHAR2 + "], " + "[" + CHAR3 + ", " + CHAR4 + "]], " +
            "\"word_locations\": {\"0,1\": " + LOCATION_WORD + "}, " +
            "\"target_language\": " + TARGET_LANGUAGE +
            "}\n";

    private TranslationParser parser;

    @Before
    public void setUp() {
        parser = new TranslationParser();
    }

    @Test
    public void parseTranslation_returnsExpectedSourceLanguage() {
        Translation translation = parser.parseTranslation(TRANSLATION_ITEM);

        assertEquals(translation.sourceLanguage, SOURCE_LANGUAGE);
    }

    @Test
    public void parseTranslation_returnsExpectedTargetLanguage() {
        Translation translation = parser.parseTranslation(TRANSLATION_ITEM);

        assertEquals(translation.targetLanguage, TARGET_LANGUAGE);
    }

    @Test
    public void parseTranslation_returnsExpectedWord() {
        Translation translation = parser.parseTranslation(TRANSLATION_ITEM);

        assertEquals(translation.word, WORD);
    }

    @Test
    public void parseTranslation_returnsExpectedGridSize() {
        Translation translation = parser.parseTranslation(TRANSLATION_ITEM);

        assertEquals(translation.gridSize, 2);
    }

    @Test
    public void parseTranslation_returnsExpectedWordLocations() {
        Translation translation = parser.parseTranslation(TRANSLATION_ITEM);

        WordLocation location = translation.locations.get(0);
        WordCoordinate coordinates = location.coordinates.get(0);
        assertEquals(translation.locations.size(), 1);
        assertEquals(location.coordinates.size(), 1);
        assertEquals(location.word, LOCATION_WORD);
        assertEquals(coordinates.x, LOCATION_X);
        assertEquals(coordinates.y, LOCATION_Y);
    }
}