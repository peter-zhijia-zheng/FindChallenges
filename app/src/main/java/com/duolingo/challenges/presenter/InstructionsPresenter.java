package com.duolingo.challenges.presenter;

import com.duolingo.challenges.R;
import com.duolingo.challenges.common.schedulers.SchedulerProvider;
import com.duolingo.challenges.contract.InstructionsContract;
import com.duolingo.challenges.data.local.TranslationsStore;
import com.duolingo.challenges.usecases.ConnectionUseCase;
import com.duolingo.challenges.usecases.FetchTranslationsUseCase;

import javax.inject.Inject;

public class InstructionsPresenter extends ReactivePresenter implements InstructionsContract.Presenter {
    private final TranslationsStore translationsStore;
    private final FetchTranslationsUseCase fetchTranslationsUseCase;
    private final ConnectionUseCase connectionUseCase;
    private final SchedulerProvider schedulerProvider;
    private InstructionsContract.View view;

    @Inject
    public InstructionsPresenter(TranslationsStore translationsStore,
                                 FetchTranslationsUseCase fetchTranslationsUseCase,
                                 ConnectionUseCase connectionUseCase,
                                 SchedulerProvider schedulerProvider) {
        this.translationsStore = translationsStore;
        this.fetchTranslationsUseCase = fetchTranslationsUseCase;
        this.connectionUseCase = connectionUseCase;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void setView(InstructionsContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

        if (!connectionUseCase.isDeviceConnected()) {
            view.showMessage(R.string.instruction_check_connection);
            return;
        }

        view.setContinueButtonEnabled(false);

        translationsStore.clearTranslations();

        addDisposable(
                fetchTranslationsUseCase.fetchTranslationFromRemoteSource()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(() -> view.setContinueButtonEnabled(true),
                                (t) -> view.showMessage(R.string.instruction_error_while_fetching))
        );
    }

}