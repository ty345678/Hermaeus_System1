package edu.psu.tmm6320.hermaeus_system;

public class CameraStatus {

//    public CameraStatus(int id, String name, int red, int green, int blue, Boolean liked) {
    public CameraStatus(int id, String name, int ipAddress, Boolean liked) {
        this.id = id;
        this.name = name;
        this.ipAddress = ipAddress;
//        this.red = red;
//        this.green = green;
//        this.blue = blue;
        this.liked = liked;
    }
    public int id;
    public String name;
    public int ipAddress;
//    public int red;
//    public int green;
//    public int blue;
    public Boolean liked;





}
