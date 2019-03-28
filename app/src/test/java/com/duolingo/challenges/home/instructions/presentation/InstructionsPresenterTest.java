package com.duolingo.challenges.home.instructions.presentation;

import com.duolingo.challenges.R;
import com.duolingo.challenges.common.schedulers.TestSchedulerProvider;
import com.duolingo.challenges.contract.InstructionsContract;
import com.duolingo.challenges.data.local.TranslationsStore;
import com.duolingo.challenges.presentation.InstructionsPresenter;
import com.duolingo.challenges.usecases.ConnectionUseCase;
import com.duolingo.challenges.usecases.FetchTranslationsUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InstructionsPresenterTest {

    @Mock
    private TranslationsStore translationsStore;
    @Mock private FetchTranslationsUseCase fetchTranslationsUseCase;
    @Mock private ConnectionUseCase connectionUseCase;
    @Mock private InstructionsContract.View view;

    private TestSchedulerProvider schedulerProvider;
    private InstructionsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        schedulerProvider = new TestSchedulerProvider();
        presenter = new InstructionsPresenter(
                translationsStore,
                fetchTranslationsUseCase,
                connectionUseCase,
                schedulerProvider);
        presenter.setView(view);
    }

    @Test
    public void startAndNoConnection_showsMessage() {
        when(connectionUseCase.isDeviceConnected()).thenReturn(false);
        fetchTranslationSuccessfully();

        presenter.start();

        verify(view).showMessage(R.string.instruction_check_connection);
    }

    @Test
    public void startAndConnected_disableContinueButton() {
        when(connectionUseCase.isDeviceConnected()).thenReturn(true);
        fetchTranslationSuccessfully();

        presenter.start();

        verify(view).setContinueButtonEnabled(false);
    }

    @Test
    public void startAndConnected_clearTranslationInStore() {
        when(connectionUseCase.isDeviceConnected()).thenReturn(true);
        fetchTranslationSuccessfully();

        presenter.start();

        verify(translationsStore).clearTranslations();
    }

    @Test
    public void startAndConnected_fetchTranslations() {
        when(connectionUseCase.isDeviceConnected()).thenReturn(true);
        fetchTranslationSuccessfully();

        presenter.start();

        verify(fetchTranslationsUseCase).fetchTranslationFromRemoteSource();
    }

    @Test
    public void startAndTranslationsFetchedSuccessfully_enableButton() {
        when(connectionUseCase.isDeviceConnected()).thenReturn(true);
        fetchTranslationSuccessfully();

        presenter.start();

        verify(view).setContinueButtonEnabled(true);
    }

    @Test
    public void startAndTranslationsFetchedWithError_showErrorMessage() {
        when(connectionUseCase.isDeviceConnected()).thenReturn(true);
        fetchTranslationWithError();

        presenter.start();

        verify(view).showMessage(R.string.instruction_error_while_fetching);
    }

    private void fetchTranslationSuccessfully() {
        when(fetchTranslationsUseCase.fetchTranslationFromRemoteSource())
                .thenReturn(Completable.complete());
    }


    private void fetchTranslationWithError() {
        when(fetchTranslationsUseCase.fetchTranslationFromRemoteSource())
                .thenReturn(Completable.error(new Throwable()));
    }
}