package edu.psu.tmm6320.hermaeus_system.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.longdo.mjpegviewer.MjpegView;

import edu.psu.tmm6320.hermaeus_system.R;

public class MainActivity extends AppCompatActivity {





    String VideoURL = "http:192.168.0.100";
//    VideoView videoview;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        startService(new Intent(this, Service.class));

//
//        Toolbar myToolbar = findViewById(R.id.toolbar);

        //static SharedPreferences PreferenceManager.getDefaultSharedPreferences(content);
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        editor.putInt("Backup_Camera_Ip",-1);
//        editor.commit();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        String test = sharedPref.getString("Backup_Camera_Ip", "");

//        Log.d("streamMode", "BACKUPO CAMERA IP TETSST: "+test);

        final String DEFAULT_VALUE = "0";
//        SharedPreferences shared = getSharedPreferences("Backup_Camera_Ip", 0);
//        String channel = shared.getString("Backup_Camera_Ip", "");
//        Log.d("streamMode", "SHARED GET ALLL: "+shared.getAll().get("Backup_Camera_Ip"));

//        long backupCameraIPValuesTest =
        String backupCameraIPValue = sharedPref.getString("Backup_Camera_Ip",DEFAULT_VALUE);
        String leftCameraIPValue = sharedPref.getString("Left_Camera_Ip",DEFAULT_VALUE);
        String rightCameraIPValue = sharedPref.getString("Right_Camera_Ip",DEFAULT_VALUE);

//        Log.d("streamMode", "cameraIPValue"+channel);




        /////////////FINAL VALUES//////////////
        StartStream(findViewById(R.id.stream_Back),"http://192.168.0."+backupCameraIPValue+":8000/stream.mjpg" );
        //StartStream(findViewById(R.id.stream_RightMirror),"http://192.168.0."+rightCameraIPValue+":8000/stream.mjpg" );
       // StartStream(findViewById(R.id.stream_LeftMirror),"http://192.168.0."+leftCameraIPValue+":8000/stream.mjpg" );





//        StartStream(findViewById(R.id.stream_RightMirror),"http://192.168.0.100" );
        //StartStream(findViewById(R.id.stream_LeftMirror),"http://192.168.0.100" );



        Button btn = (Button)findViewById(R.id.button4);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LightsActivity.class));
            }
        });




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
        ConstraintLayout constraintLayout = findViewById(R.id.rootLayout);

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_lights:
                startActivity(new Intent(MainActivity.this, LightsActivity.class));
                //updateDatabase();
                return true;
            case R.id.menu_settings:
                //startActivity(new Intent(MainActivity.this, LightsActivity.class));
                //updateDatabase();
                return true;
            case R.id.menu_preferences:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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