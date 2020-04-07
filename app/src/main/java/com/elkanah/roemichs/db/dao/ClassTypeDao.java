package com.elkanah.roemichs.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.elkanah.roemichs.db.models.ClassType;

import java.util.List;

@Dao
public interface ClassTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClassType> classTypes);

    @Query("SELECT * FROM classtype order by Text")
    LiveData<List<ClassType>> getAll();
}
