package com.duolingo.challenges.common;

import com.duolingo.challenges.common.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class SchedulerProviderTest implements SchedulerProvider {
    @Inject
    public SchedulerProviderTest() {

    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }

}