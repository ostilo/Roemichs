package com.elkanah.roemichs.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.elkanah.roemichs.db.models.ClassModel;

import java.util.List;

@Dao
public interface ClassDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAll(List<ClassModel> branchCodeList);

  @Query("SELECT * FROM ClassModel WHERE value = :value")
  ClassModel getBranchCode(String value);

  @Query("SELECT * FROM ClassModel")
  LiveData<List<ClassModel>> getAll();

  @Query("SELECT Value FROM ClassModel WHERE Text=:text")
  String getSingular(String text);
}
