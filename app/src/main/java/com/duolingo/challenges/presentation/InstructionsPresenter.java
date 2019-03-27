package com.duolingo.challenges.presentation;

import com.duolingo.challenges.R;
import com.duolingo.challenges.contract.InstructionsContract;
import com.duolingo.challenges.data.local.TranslationsStore;
import com.duolingo.challenges.mvp.ReactivePresenter;
import com.duolingo.challenges.usecases.ConnectionUseCase;
import com.duolingo.challenges.usecases.FetchTranslationsUseCase;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InstructionsPresenter extends ReactivePresenter implements InstructionsContract.Presenter {
    private TranslationsStore translationsStore;
    private FetchTranslationsUseCase fetchTranslationsUseCase;
    private ConnectionUseCase connectionUseCase;

    @Inject
    public InstructionsPresenter(TranslationsStore translationsStore,
                                 FetchTranslationsUseCase fetchTranslationsUseCase,
                                 ConnectionUseCase connectionUseCase) {
        this.translationsStore = translationsStore;
        this.fetchTranslationsUseCase = fetchTranslationsUseCase;
        this.connectionUseCase = connectionUseCase;
    }

    private InstructionsContract.View view;

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
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> view.setContinueButtonEnabled(true),
                                (t) -> view.showMessage(R.string.instruction_error_while_fetching))
        );
    }

}