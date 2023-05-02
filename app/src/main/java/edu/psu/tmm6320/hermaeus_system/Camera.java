package edu.psu.tmm6320.hermaeus_system;


import android.os.Message;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.os.Handler;

import com.longdo.mjpegviewer.MjpegView;

//import java.util.logging.Handler;


@Entity(tableName = "camera")
public class Camera {

//    public Camera(int id, String name, int red, int green, int blue, boolean liked ) {
    public Camera(int id, String name,int ipAddress, boolean liked ) {
        this.id =id;
        this.name = name;
        this.ipAddress = ipAddress;
//        this.red = red;
//        this.green = green;
//        this.blue = blue;
        this.liked = liked;
        //this.runningThread = null;
//        this.handler = null;
        //this.viewer = null;


    }
//    private Thread runningThread;
//    private MjpegView viewer; //findViewByID
//    public Handler handler;


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "ipAddress")
    public int ipAddress;
//    @ColumnInfo(name = "red")
//    public int red;
//    @ColumnInfo(name = "green")
//    public int green;
//    @ColumnInfo(name = "blue")
//    public int blue;
    @ColumnInfo(name = "liked")
    public boolean liked;



    public void setStream(MjpegView view){

//        this.viewer = (MjpegView) view;//findViewById(R.id.mjpegview);



    }

    public void EndStream(){
        //TODO change to correct viewer-- created in new? class to run on background thread
//        viewer.stopStream();
//        viewer.setBitmap(null);
//        viewer.setMode(MjpegView.MODE_BEST_FIT);

    }



    public void startStream(int view){

        //TODO change to correct view???





//        this.runningThread = new Thread(()->{
//        MjpegView viewer
        String url = "192.168.0."+this.ipAddress;
//        Message msg = handler.obtainMessage();

//            viewer.setMode(MjpegView.MODE_FIT_WIDTH);
            //viewer.setAdjustHeight(true);
            //viewer.setSupportPinchZoomAndPan(true);
            //viewer.setUrl("https://bma-itic1.iticfoundation.org/mjpeg2.php?camid=test");
//        viewer.setUrl("http://192.168.0.100");
//            viewer.setUrl(url);
            //viewer.getHandler().handleMessage();
            //viewer.setBitmap();
            //viewer.ondraw

            // viewer.setMode(MjpegView.MODE_BEST_FIT);

//            viewer.startStream();



//         });
//        this.runningThread.start();



    }















}
