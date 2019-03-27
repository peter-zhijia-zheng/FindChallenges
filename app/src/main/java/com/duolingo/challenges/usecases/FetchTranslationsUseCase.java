package com.duolingo.challenges.usecases;

import com.duolingo.challenges.data.local.TranslationsStore;
import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.data.parsers.TranslationParser;
import com.duolingo.challenges.data.remote.RemoteDataSource;

import javax.inject.Inject;

import io.reactivex.Completable;

public class FetchTranslationsUseCase {
    private static final String BREAK_LINE = "\n";

    private RemoteDataSource remoteDataSource;
    private TranslationsStore translationsStore;
    private TranslationParser translationParser;

    @Inject
    public FetchTranslationsUseCase(
            RemoteDataSource remoteDataSource,
            TranslationsStore translationsStore,
            TranslationParser translationParser) {
        this.remoteDataSource = remoteDataSource;
        this.translationsStore = translationsStore;
        this.translationParser = translationParser;
    }

    public Completable fetchTranslationFromRemoteSource() {
        return remoteDataSource.getTranslations()
                .flatMapCompletable(it ->
                        Completable.fromAction(() -> {
                            String responseBodyString = it.string();
                            String[] lines = responseBodyString.split(BREAK_LINE);
                            for (String line : lines) {
                                Translation translation = translationParser.parseTranslation(line);
                                translationsStore.addTranslation(translation);
                            }
                        })
                );
    }
}