package com.example.moviesearcher;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    // Pattern
    private static AppExecutors instance;

    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    // убюираю задание на задний фон, 1 поток для соединения с retrofit, 2 для закрытия
    private final ScheduledExecutorService mNetWorkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO() {
        return mNetWorkIO;
    }
}
