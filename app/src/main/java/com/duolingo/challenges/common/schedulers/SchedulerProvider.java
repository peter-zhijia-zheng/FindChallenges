package com.duolingo.challenges.common.schedulers;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();

}