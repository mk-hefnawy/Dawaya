package com.example.dawaya.Local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dawaya.models.CartProductsTable;
import com.example.dawaya.models.OrdersTable;

@Database(entities = CartProductsTable.class, version = 2, exportSchema = false)
public abstract class  LocalDataBase extends RoomDatabase {

    private static LocalDataBase instance;

    public abstract CartProductDao mainDao();


    public LocalDataBase() {
    }

    public static synchronized LocalDataBase getInstance(Context context){  // Synchronized, so that its impossible for two or more threads to access the db
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), LocalDataBase.class, "LocalDataBase")
                    .fallbackToDestructiveMigration() // da kda m3na anny lamma a3addel 3la el database msh h destroy el 2demaa
                    .build();
        }
        return instance;
    }
}
