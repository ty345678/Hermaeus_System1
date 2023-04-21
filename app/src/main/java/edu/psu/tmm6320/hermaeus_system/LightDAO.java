package edu.psu.tmm6320.hermaeus_system;


import androidx.room.Dao;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LightDAO {

    @Query("SELECT * FROM lights ORDER BY name")
    LiveData<List<Light>> getAll();

    @Query("SELECT * FROM lights WHERE liked = :onlyLiked " +
            "ORDER BY name COLLATE NOCASE, rowid")
    LiveData<List<Light>> getLiked(boolean onlyLiked);

    @Query("SELECT * FROM lights WHERE id = :lightsId")
    Light getById(int lightsId);
    @Insert
    void insert(Light... lights);
    @Update
    void update(Light... lights);
    @Delete
    void delete(Light... lights);




}
