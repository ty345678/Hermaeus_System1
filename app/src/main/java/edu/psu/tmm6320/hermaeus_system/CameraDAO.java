//package edu.psu.tmm6320.hermaeus_system;
//
//
//import androidx.room.Dao;
//import java.util.List;
//import androidx.lifecycle.LiveData;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//@Dao
//public interface CameraDAO {
//
//    @Query("SELECT * FROM cameras ORDER BY name")
//    LiveData<List<Camera>> getAll();
//
//    @Query("SELECT * FROM cameras WHERE ip = :CameraIP")
//    Camera getById(int CameraIP);
//    @Insert
//    void insert(Camera... cameras);
//    @Update
//    void update(Camera... cameras);
//    @Delete
//    void delete(Camera... cameras);
//
//
//
//
//}
