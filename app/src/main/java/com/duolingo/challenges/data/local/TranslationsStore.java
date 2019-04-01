package com.duolingo.challenges.data.local;

import com.duolingo.challenges.data.models.Translation;

import java.util.ArrayList;

public class TranslationsStore {

    private final ArrayList<Translation> translations = new ArrayList<>();

    public void addTranslation(Translation translation) {
        translations.add(translation);
    }

    public Boolean hasTranslations() {
        return !translations.isEmpty();
    }

    public int getTranslationCount() {
        return translations.size();
    }

    public void clearTranslations() {
        translations.clear();
    }

    public Translation fetchTranslationWithIndex(int index) {
        return index < translations.size() ? translations.get(index) : null;
    }
}