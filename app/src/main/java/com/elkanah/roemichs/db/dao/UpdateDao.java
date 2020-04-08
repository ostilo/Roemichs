package com.elkanah.roemichs.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.elkanah.roemichs.db.models.ForceUpdate_entity;


@Dao
public interface UpdateDao {}
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    //void InsertForceUpdate(ForceUpdate_entity entity);
//    @Query("SELECT  * FROM ForceUpdate_entity WHERE UpdateID = 1")
//    //LiveData<ForceUpdate_entity> getForceUpdateDetails();
//
//    @Query("SELECT * FROM ForceUpdate_entity WHERE UpdateID = 1")
//    ForceUpdate_entity entity();
//}
