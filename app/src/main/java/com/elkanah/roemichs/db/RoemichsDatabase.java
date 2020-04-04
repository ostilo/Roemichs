package com.elkanah.roemichs.db;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.elkanah.roemichs.db.converter.DateConverter;
import com.elkanah.roemichs.db.dao.ClassDao;
import com.elkanah.roemichs.db.dao.SubjectDao;
import com.elkanah.roemichs.db.models.ClassModel;
import com.elkanah.roemichs.db.models.SubjectDaoModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ClassModel.class, SubjectDaoModel.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class RoemichsDatabase extends RoomDatabase {
  private static final String DATABASE_NAME="Roemichs";
  private static volatile RoemichsDatabase ourInstance;
  public abstract ClassDao sessionDao();
  public abstract SubjectDao subjectDao();

  private static  final int NUMBER_OF_THREADS = 4;
  public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
  public static RoemichsDatabase getInstance(Application application){
    if (ourInstance == null){
      synchronized (RoemichsDatabase.class) {
        if (ourInstance == null) {
          ourInstance = Room.databaseBuilder(application.getApplicationContext(),
                  RoemichsDatabase.class, DATABASE_NAME)
                  .build();
        }
      }
    }
    return ourInstance;
  }
}
