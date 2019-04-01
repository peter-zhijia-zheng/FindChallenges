package com.duolingo.challenges.presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

abstract class ReactivePresenter {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void destroy() {
        compositeDisposable.clear();
    }
}