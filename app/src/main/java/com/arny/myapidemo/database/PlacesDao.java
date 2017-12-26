package com.arny.myapidemo.database;

import android.arch.persistence.room.*;
import com.arny.myapidemo.api.jsongenerator.Place;
import io.reactivex.Flowable;

import java.util.List;

@Dao
public interface PlacesDao {

    @Query("SELECT name FROM sqlite_master WHERE type='table'")
    String getSchema();

    @Query("SELECT DISTINCT tbl_name FROM sqlite_master")
    List<String> getDbTables();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Place place);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Place> places);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Place place);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(List<Place> places);

    @Delete()
    void delete(Place place);

    @Query("SELECT * FROM places WHERE _id IS :id")
    Flowable<Place> getPlace(long id);

    @Query("SELECT * FROM places ORDER BY title")
    List<Place> getPlaces();
}
