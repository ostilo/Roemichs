package com.elkanah.roemichs.db.converter;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

  @TypeConverter
  public static String toDate(Date dateLong){
    return dateLong == null ? null: new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(dateLong);
  }

  @TypeConverter
  public static Date fromDate(String date){
    try{
      return date == null ? null : new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
    } catch (Exception ex){
      return null;
    }
  }
}
