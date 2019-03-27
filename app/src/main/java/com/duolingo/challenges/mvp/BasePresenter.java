package com.duolingo.challenges.mvp;

public interface BasePresenter<V> {

    void setView(V view);
    
}