package com.example.recylerview;

import android.app.slice.SliceSpec;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
    //Insert query
@Insert
    void insert(Asia asia);
@Delete
    void delete(Asia asia);

//Delete ALL
    @Delete
    void reset(List<Asia>asia);
    //Get All DATA QUERY
    @Query("SELECT * FROM country_details")
    List<Asia>getAll();
}
