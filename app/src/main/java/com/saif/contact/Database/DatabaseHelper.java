package com.saif.contact.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContactModel.class,RecentModel.class},exportSchema = false,version = 1)
public abstract class DatabaseHelper extends RoomDatabase {

    private static final String DATABASE_NAME = "contactdb";
    private  static DatabaseHelper instance;

    public static synchronized DatabaseHelper getDB(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, DatabaseHelper.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract ContactDao contactDao();
    public abstract RecentDao recentDao();
}
