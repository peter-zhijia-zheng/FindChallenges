package com.duolingo.challenges.usecases;

import com.duolingo.challenges.data.local.TranslationsStore;

import io.reactivex.Completable;

public class FetchTranslationsUseCase {
    private static final String BREAK_LINE = "\n";

    private RemoteDataSource remoteDataSource;
    private TranslationsStore  translationsStore;
    private TranslationParser translationParser;

    public FetchTranslationsUseCase(
            RemoteDataSource remoteDataSource,
            TranslationsStore  translationsStore,
            TranslationParser translationParser) {

    }

    public Completable fetchTranslationFromRemoteSource() {
        return remoteDataSource.getTranslations()
                .flatMapCompletable {
            Completable.fromAction {
                val responseBodyString = it.string()
                val lines = responseBodyString.split(BREAK_LINE);
                lines.map {
                    translationParser.parseTranslation(it);
                }.forEach {
                    translationsStore.addTranslation(it);
                }
            }
        }
    }
}