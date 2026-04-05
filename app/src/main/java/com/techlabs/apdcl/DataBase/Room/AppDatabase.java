package com.techlabs.apdcl.DataBase.Room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;

@Database(entities = {CustomerData.class}, version = 4, exportSchema = false)
@TypeConverters({JsonDBConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase db) {
            db.execSQL(
                    "ALTER TABLE customer_data ADD COLUMN year INTEGER NOT NULL DEFAULT 0"
            );
        }
    };
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase db) {
            db.execSQL(
                    "ALTER TABLE customer_data ADD COLUMN normalPriority INTEGER NOT NULL DEFAULT 0"
            );
            db.execSQL(
                    "ALTER TABLE customer_data ADD COLUMN emergPriority INTEGER NOT NULL DEFAULT 0"
            );
        }
    };
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase db) {
            db.execSQL(
                    "ALTER TABLE customer_data ADD COLUMN phase TEXT"
            );
        }
    };
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "UtilityNet_DB"
                            )
                            .addMigrations(
                                    MIGRATION_1_2,
                                    MIGRATION_2_3,
                                    MIGRATION_3_4
                            )
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract CustomerDataDao customerDataDao();
}

