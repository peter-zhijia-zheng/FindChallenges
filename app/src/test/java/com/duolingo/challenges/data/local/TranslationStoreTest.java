package com.duolingo.challenges.data.local;

import com.duolingo.challenges.data.models.Translation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TranslationStoreTest {

    @Mock
    private Translation translation;
    @Mock
    private Translation secondTranslation;

    private TranslationsStore store;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        store = new TranslationsStore();
    }

    @Test
    public void default_returnFalse() {
        boolean hasTranslations = store.hasTranslations();
        assertFalse(hasTranslations);
    }

    @Test
    public void add_returnTrue() {
        store.addTranslation(translation);

        boolean hasTranslations = store.hasTranslations();

        assertTrue(hasTranslations);
    }

    @Test
    public void count_returnExpectValue() {
        store.addTranslation(translation);
        store.addTranslation(translation);

        int count = store.getTranslationCount();

        assertEquals(count, 2);
    }

    @Test
    public void clear_returnZero() {
        store.addTranslation(translation);
        store.addTranslation(translation);

        store.clearTranslations();
        int count = store.getTranslationCount();

        assertEquals(count, 0);
    }

    @Test
    public void index_returnExpectedTranslation() {
        store.addTranslation(translation);
        store.addTranslation(secondTranslation);

        Translation translation = store.fetchTranslationWithIndex(1);

        assertEquals(translation, secondTranslation);
    }

    @Test
    public void indexOutOfBounds_returnsNullTranslation() {
        store.addTranslation(translation);
        store.addTranslation(secondTranslation);

        Translation translation = store.fetchTranslationWithIndex(3);

        assertNull(translation);
    }
}