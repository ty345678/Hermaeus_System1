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

import edu.psu.tmm6320.hermaeus_system.Camera;
import edu.psu.tmm6320.hermaeus_system.R;

public class MainActivity extends AppCompatActivity {



    //MjpegView viewer;

    String VideoURL = "http:192.168.0.100";
//    VideoView videoview;
    Camera cameraRightMirror;
    Camera cameraLeftMirror;
    Camera cameraBackup;
    //camera.createViewer? -- get ip/etc from database;
    //camera.startSteam--onBackgroundThread
    //callback to update iamge on forground
    //rightmirror.stopStream();
    //camera.startStream(r.id.RightMirror_rightTurn);








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        startService(new Intent(this, Service.class));


        //1 create cameras
            //done via database -- have to wait for callback/thread to create
        //2 start streams
            //w inital inputs
        //3 --callback update image
        //4 wait for button/bluetooth call to change views
        //  4.1 stop all streams
            //2 start all streams w new inputs
            //

        //Camera temp = new Camera();
        //

//        this.cameraBackup = new Camera();
//        this.cameraLeftMirror = new Camera();
//        this.cameraRightMirror = new Camera();








        StartAllStreams();
//
//        Toolbar myToolbar = findViewById(R.id.toolbar);

        //static SharedPreferences PreferenceManager.getDefaultSharedPreferences(content);
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        editor.putInt("Backup_Camera_Ip",-1);
//        editor.commit();

//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        String test = sharedPref.getString("Backup_Camera_Ip", "");

//        Log.d("streamMode", "BACKUPO CAMERA IP TETSST: "+test);

//        final String DEFAULT_VALUE = "0";
//        SharedPreferences shared = getSharedPreferences("Backup_Camera_Ip", 0);
//        String channel = shared.getString("Backup_Camera_Ip", "");
//        Log.d("streamMode", "SHARED GET ALLL: "+shared.getAll().get("Backup_Camera_Ip"));

//        long backupCameraIPValuesTest =
//        String backupCameraIPValue = sharedPref.getString("Backup_Camera_Ip",DEFAULT_VALUE);
//        String leftCameraIPValue = sharedPref.getString("Left_Camera_Ip",DEFAULT_VALUE);
//        String rightCameraIPValue = sharedPref.getString("Right_Camera_Ip",DEFAULT_VALUE);

//        Log.d("streamMode", "cameraIPValue"+channel);




        /////////////FINAL VALUES//////////////
//        StartStream(findViewById(R.id.stream_Back),"http://192.168.0."+backupCameraIPValue+":8000/stream.mjpg" );
        //StartStream(findViewById(R.id.stream_RightMirror),"http://192.168.0."+rightCameraIPValue+":8000/stream.mjpg" );
       // StartStream(findViewById(R.id.stream_LeftMirror),"http://192.168.0."+leftCameraIPValue+":8000/stream.mjpg" );

//
//        Stream seeee = new Stream();
//        seeee.setUrl("hi");


//        StartStream(findViewById(R.id.stream_RightMirror),"http://192.168.0.100" );
        //StartStream(findViewById(R.id.stream_LeftMirror),"http://192.168.0.100" );



//        Button btn = (Button)findViewById(R.id.button4);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, camerasActivity.class));
//            }
//        });




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
//    private int [] getStreams(){
//
//        //return []
//        return null;
//    }


    private void StartAllStreams() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);


        final String DEFAULT_VALUE = "0";
        String backupCameraIPValue = sharedPref.getString("Backup_Camera_Ip",DEFAULT_VALUE);
        String leftCameraIPValue = sharedPref.getString("Left_Camera_Ip",DEFAULT_VALUE);
        String rightCameraIPValue = sharedPref.getString("Right_Camera_Ip",DEFAULT_VALUE);





        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////
        /////////////FINAL VALUES//////////////

        StartStream(findViewById(R.id.stream_Back_Driving),"http://192.168.0."+backupCameraIPValue+":8000/stream.mjpg" );
        StartStream(findViewById(R.id.stream_RightMirror_Driving),"http://192.168.0."+rightCameraIPValue+":8000/stream.mjpg" );
        StartStream(findViewById(R.id.stream_LeftMirror_Driving),"http://192.168.0."+leftCameraIPValue+":8000/stream.mjpg" );




    }
//    public void pauseStream(){
//
//        //viewer.stopStream();
//
//
//
//    }




    public void StartStream(MjpegView viewer, String url) {

        viewer.setMode(MjpegView.MODE_FIT_WIDTH);
        //viewer.setAdjustHeight(true);
        //viewer.setSupportPinchZoomAndPan(true);
        //viewer.setUrl("https://bma-itic1.iticfoundation.org/mjpeg2.php?camid=test");
//        viewer.setUrl("http://192.168.0.100");
        viewer.setUrl(url);
       // viewer.setMode(MjpegView.MODE_BEST_FIT);

        viewer.startStream();
       // viewer.
        //Log.d("streamMode", "starting stream+ stream time:"+viewer.getDrawingTime());
    }




//    public void EndStream(MjpegView viewer){
//
//        viewer.stopStream();
//        //viewer.clearAnimation();
//        //viewer.setAlpha(0);
//        viewer.setBitmap(null);
//        viewer.setMode(MjpegView.MODE_BEST_FIT);
//
//
//
//    }
    @Override
    public void onStop(){
        super.onStop();
        EndAllStreams();
    }

    public void Reverse(View view) {
        ///////////////////////////////////////////////////////replace w camera dao
        Log.d("Reverse", "starting BackupMode");

        //EndStream(findViewById(R.id.stream_Back));
        //EndStream(findViewById(R.id.stream_LeftMirror));
        //EndStream(findViewById(R.id.stream_RightMirror));


        //update imageView
        //ConstraintLayout constraintLayout = findViewById(R.id.parentLayout_all3);
        //start new layout???
//        ConstraintLayout constraintLayout = findViewById(R.id.rootLayout);

//        View vieeew = findViewById(R.id.stream_LeftMirror);
//        vieeew.addChildrenForAccessibility();
//        MjpegView ppp = findViewById(R.id.stream_LeftMirror);
//        ppp.addChildrenForAccessibility();


//        ConstraintSet constraintSet = new ConstraintSet();
//        constraintSet.clone(constraintLayout);
//        constraintSet.connect(R.id.stream_Back, ConstraintSet.START, R.id.guidelineV01, ConstraintSet.END, 0);
//        constraintSet.connect(R.id.stream_Back, ConstraintSet.END, R.id.guidelineV99, ConstraintSet.START, 0);
//
//        constraintSet.connect(R.id.stream_Back, ConstraintSet.TOP, R.id.guidelineH01, ConstraintSet. BOTTOM, 0);
//        constraintSet.connect(R.id.stream_Back, ConstraintSet.BOTTOM, R.id.guidelineH99, ConstraintSet.TOP, 0);
//        constraintSet.applyTo(constraintLayout);
//       MjpegView viewer = (MjpegView) findViewById(R.id.stream_Back);
//       viewer.setMode(MjpegView.MODE_BEST_FIT);
        //StartStream(findViewById(R.id.stream_Back),"http://192.168.0.100" );



        //stop all streams
        //restart all streams w new params
        Log.d("Reverse", "Ending Streams");

        EndAllStreams();
        //start new stream w backup_reverse size image
        Log.d("Reverse", "Starting Streams w new Params");

        StartStream(cameraBackup, R.id.stream_Back_Reverse);
        StartStream(cameraLeftMirror, R.id.stream_LeftMirror_Reverse);
        StartStream(cameraRightMirror, R.id.stream_RightMirror_Reverse);

    }
    public void LeftTurn(View view){
        EndAllStreams();
        //start new stream w backup_reverse size image

        StartStream(cameraBackup, R.id.stream_Back_LeftTurn);
        StartStream(cameraLeftMirror, R.id.stream_LeftMirror_LeftTurn);
        //StartStream(cameraRightMirror, R.id.stream_RightMirror_LeftTurn);
    }
    public void RightTurn(View view){
        EndAllStreams();
        //start new stream w backup_reverse size image

        StartStream(cameraBackup, R.id.stream_Back_RightTurn);
        //StartStream(cameraLeftMirror, R.id.stream_LeftMirror_RightTurn);
        StartStream(cameraRightMirror, R.id.stream_RightMirror_RightTurn);
    }
    public void Driving(View view){
        EndAllStreams();
        //start new stream w backup_reverse size image

        StartStream(cameraBackup, R.id.stream_Back_Driving);
        StartStream(cameraLeftMirror, R.id.stream_LeftMirror_Driving);
        StartStream(cameraRightMirror, R.id.stream_RightMirror_Driving);
    }




    public void StartStream(Camera camera, int viewer){
        camera.startStream(viewer);

    }





    public void EndAllStreams(){
            //get all 3 cameras;
        cameraRightMirror.EndStream();
        cameraLeftMirror.EndStream();
        cameraBackup.EndStream();

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
                Log.d("penis", "starting cameraActivity" );
                startActivity(new Intent(MainActivity.this, camerasActivity.class));
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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putString(GAME_STATE_KEY, gameState);
        //outState.putString(TEXT_VIEW_KEY, textView.getText());
        //pauseStream();
        // Call superclass to save any view hierarchy.
        super.onSaveInstanceState(outState);
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