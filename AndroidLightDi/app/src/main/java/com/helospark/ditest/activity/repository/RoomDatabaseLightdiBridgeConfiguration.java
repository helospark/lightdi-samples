package com.helospark.ditest.activity.repository;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.Configuration;

@Configuration
public class RoomDatabaseLightdiBridgeConfiguration {
    private static final String DATABASE_NAME = "lightdiquiz-database-2";
    private Context context;

    public RoomDatabaseLightdiBridgeConfiguration(Context context) {
        this.context = context;
    }

    @Bean
    public RoomDatabase roomDatabase() {
        return Room.databaseBuilder(context, RoomDatabase.class, DATABASE_NAME)
                .build();
    }

}
