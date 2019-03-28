package com.duolingo.challenges.home.instructions.usecases;

import com.duolingo.challenges.data.local.TranslationsStore;
import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.data.parsers.TranslationParser;
import com.duolingo.challenges.data.remote.RemoteDataSource;
import com.duolingo.challenges.usecases.FetchTranslationsUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static com.duolingo.challenges.usecases.FetchTranslationsUseCase.BREAK_LINE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FetchTranslationsUseCaseTest {
    private static final String TRANSLATION_1 = "a";
    private static final String TRANSLATION_2 = "b";
    private static final String RESPONSE_STRING = TRANSLATION_1 + BREAK_LINE + TRANSLATION_2;
            
    @Mock
    private RemoteDataSource remoteDataSource;
    @Mock private TranslationsStore translationsStore;
    @Mock private TranslationParser translationParser;
    @Mock private ResponseBody responseBody;
    @Mock private Translation translation;
    @Mock private Throwable throwable;

    private FetchTranslationsUseCase useCase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        useCase = new FetchTranslationsUseCase(
                remoteDataSource,
                translationsStore,
                translationParser);
    }

    @Test
    public void translationsReceived_theyAreParsedAndStored() {
        when(remoteDataSource.getTranslations()).thenReturn(Observable.just(responseBody));
        try {
            when(responseBody.string()).thenReturn(RESPONSE_STRING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        when(translationParser.parseTranslation(any())).thenReturn(translation);

        useCase.fetchTranslationFromRemoteSource()
                .test()
                .assertNoErrors();

        verify(translationParser).parseTranslation(TRANSLATION_1);
        verify(translationParser).parseTranslation(TRANSLATION_2);
        verify(translationsStore, times(2)).addTranslation(any());
    }

    @Test
    public void translationsFailedToFetch_notifyError() {
        when(remoteDataSource.getTranslations()).thenReturn(Observable.error(throwable));

        useCase.fetchTranslationFromRemoteSource()
                .test()
                .assertError(throwable);
    }
}