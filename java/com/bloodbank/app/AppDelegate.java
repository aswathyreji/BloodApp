package com.bloodbank.app;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.bloodbank.db.AppDatabase;

public class AppDelegate extends Application {
    public static AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();


        db = Room.databaseBuilder(this, AppDatabase.class, "app")
                .build();
    }
}
