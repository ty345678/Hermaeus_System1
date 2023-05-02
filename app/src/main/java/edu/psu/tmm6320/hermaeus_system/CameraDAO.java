package edu.psu.tmm6320.hermaeus_system;


import androidx.room.Dao;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CameraDAO {

    @Query("SELECT * FROM camera ORDER BY name")
    LiveData<List<CameraStatus>> getAll();

    @Query("SELECT * FROM camera WHERE liked = :onlyLiked " +
            "ORDER BY name COLLATE NOCASE, id")
    LiveData<List<CameraStatus>> getLiked(boolean onlyLiked);

    @Query("SELECT * FROM camera WHERE id = :camerasId")
    Camera getById(int camerasId);
    @Insert
    void insert(Camera... cameras);
    @Update
    void update(Camera... cameras);
    @Delete
    void delete(Camera... cameras);
    @Query("DELETE FROM Camera WHERE id=:cameraId")
    void delete(int cameraId);

    @Query("UPDATE Camera SET liked = :likeValue WHERE rowid = :id")
    void update(int id, Boolean likeValue);

}
