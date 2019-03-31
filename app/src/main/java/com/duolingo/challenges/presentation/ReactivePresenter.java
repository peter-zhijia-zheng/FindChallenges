package com.duolingo.challenges.presentation;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class ReactivePresenter {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void destroy() {
        compositeDisposable.clear();
    }
}