package com.elkanah.roemichs.db.converter;

import androidx.room.TypeConverter;

import java.math.BigDecimal;


public class BigDecimalConverter {
  @TypeConverter
  public static Double to(BigDecimal bigDecimal){
    return bigDecimal == null ? null : bigDecimal.doubleValue();
  }

  @TypeConverter
  public static BigDecimal fromString(String value){
    try{
      return value == null ? null : new BigDecimal(value);
    } catch (Exception ex){
      return null;
    }
  }
}
