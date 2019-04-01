package com.duolingo.challenges.common.schedulers;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler io();

    Scheduler ui();

}