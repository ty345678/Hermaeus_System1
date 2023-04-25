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
    LiveData<List<LightStatus>> getAll();

    @Query("SELECT * FROM lights WHERE liked = :onlyLiked " +
            "ORDER BY name COLLATE NOCASE, id")
    LiveData<List<LightStatus>> getLiked(boolean onlyLiked);

    @Query("SELECT * FROM lights WHERE id = :lightsId")
    Light getById(int lightsId);
    @Insert
    void insert(Light... lights);
    @Update
    void update(Light... lights);
    @Delete
    void delete(Light... lights);
    @Query("DELETE FROM lights WHERE id=:lightId")
    void delete(int lightId);

    @Query("UPDATE lights SET liked = :likeValue WHERE rowid = :id")
    void update(int id, Boolean likeValue);

}
