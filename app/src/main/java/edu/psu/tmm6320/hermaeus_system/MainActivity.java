package edu.psu.tmm6320.hermaeus_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.longdo.mjpegviewer.MjpegView;

public class MainActivity extends AppCompatActivity {





    String VideoURL = "http:192.168.0.100";
//    VideoView videoview;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, service.class));

//
//        Toolbar myToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(myToolbar);




        StartStream(findViewById(R.id.stream_Back),"http://192.168.0.150:8000/stream.mjpg" );
        StartStream(findViewById(R.id.stream_RightMirror),"http://192.168.0.100" );
        //StartStream(findViewById(R.id.stream_LeftMirror),"http://192.168.0.100" );


//when user leaves application
        //viewer.stopStream();



//        try {
//
//            Uri video = Uri.parse(VideoURL);
//
//            videoview.setVideoPath(VideoURL);
//            videoview.start();
//
//        } catch (Exception e) {
//            Log.e("Error", e.getMessage());
//            e.printStackTrace();
//        }

    }

    public void StartStream(MjpegView viewer, String url) {

        viewer.setMode(MjpegView.MODE_FIT_WIDTH);
        //viewer.setAdjustHeight(true);
        //viewer.setSupportPinchZoomAndPan(true);
        //viewer.setUrl("https://bma-itic1.iticfoundation.org/mjpeg2.php?camid=test");
//        viewer.setUrl("http://192.168.0.100");
        viewer.setUrl(url);
       // viewer.setMode(MjpegView.MODE_BEST_FIT);

        viewer.startStream();
        Log.d("streamMode", "starting stream"+viewer.getDrawingTime());
    }
    public void EndStream(MjpegView viewer){

        viewer.stopStream();
        //viewer.clearAnimation();
        //viewer.setAlpha(0);
        viewer.setBitmap(null);
        viewer.setMode(MjpegView.MODE_BEST_FIT);



    }
    public void BackupMode(View view) {
        ///////////////////////////////////////////////////////replace w camera dao
        Log.d("backupMode", "starting BackupMode");

        //EndStream(findViewById(R.id.stream_Back));
        //EndStream(findViewById(R.id.stream_LeftMirror));
        //EndStream(findViewById(R.id.stream_RightMirror));

        Log.d("backupMode", "Ending Streams");

        //update imageView
        //ConstraintLayout constraintLayout = findViewById(R.id.parentLayout_all3);
        ConstraintLayout constraintLayout = findViewById(R.id.parentLayout_all3);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.stream_Back, ConstraintSet.START, R.id.guidelineV01, ConstraintSet.END, 0);
        constraintSet.connect(R.id.stream_Back, ConstraintSet.END, R.id.guidelineV99, ConstraintSet.START, 0);

        constraintSet.connect(R.id.stream_Back, ConstraintSet.TOP, R.id.guidelineH01, ConstraintSet. BOTTOM, 0);
        constraintSet.connect(R.id.stream_Back, ConstraintSet.BOTTOM, R.id.guidelineH99, ConstraintSet.TOP, 0);
        constraintSet.applyTo(constraintLayout);
        Log.d("backupMode", "SET CONSTRAINSTS");

       MjpegView viewer = (MjpegView) findViewById(R.id.stream_Back);
       viewer.setMode(MjpegView.MODE_BEST_FIT);
        //StartStream(findViewById(R.id.stream_Back),"http://192.168.0.100" );


    }













//request img streams/imgs from ip addresses of 4 raspis
//display images depending on selected views

//depending on external input (turn signal, reverse ...)
    //change view to show correct camera view
    //bluetooth device send input to cause changes
    //trigger buttons get trigged when turn signal activated?
    //physical buttons added to car


//External input when car is started, turn display on and open app
    //done via service that runs in background?
    //connected to bluetooth device that will send wake signal when connected to car
    //bluetooth device will turn off/on with car off/on causing display to turn on and off
    // when bluetooth device is disconencted -- turn display off? sleep?




}