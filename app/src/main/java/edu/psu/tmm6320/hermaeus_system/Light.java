package edu.psu.tmm6320.hermaeus_system;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "lights")
public class Light {

    public Light(int id, String name, int red, int green, int blue, boolean liked ) {
        this.id =id;
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.liked = liked;


    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "red")
    public int red;
    @ColumnInfo(name = "green")
    public int green;
    @ColumnInfo(name = "blue")
    public int blue;
    @ColumnInfo(name = "liked")
    public boolean liked;














}
