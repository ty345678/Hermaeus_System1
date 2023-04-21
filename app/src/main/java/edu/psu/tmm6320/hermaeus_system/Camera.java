package edu.psu.tmm6320.hermaeus_system;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "cameras")
public class Camera {

    public Camera(int id, String name, int width, int height ) {
        this.id =id;
        this.name = name;
        this.width = width;
        this.height = height;


    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "width")
    public int width;
    @ColumnInfo(name = "height")
    public int height;















}
