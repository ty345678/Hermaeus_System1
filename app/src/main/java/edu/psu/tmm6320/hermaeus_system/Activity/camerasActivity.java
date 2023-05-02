package edu.psu.tmm6320.hermaeus_system.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.psu.tmm6320.hermaeus_system.Camera;
import edu.psu.tmm6320.hermaeus_system.CameraDatabase;
import edu.psu.tmm6320.hermaeus_system.CameraStatus;
import edu.psu.tmm6320.hermaeus_system.CameraViewModel;
import edu.psu.tmm6320.hermaeus_system.R;
//import edu.psu.tmm6320.hermaeus_system.cameraListAdapter;

public class camerasActivity extends AppCompatActivity {


    public camerasActivity.cameraListAdapter cameraListAdapter;
    private boolean filtered = false;  // Are results filtered by likess
    private CameraViewModel cameraViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights_list);
        if (savedInstanceState != null) {
            filtered = savedInstanceState.getBoolean("filtered");
        }









        Log.d("cameras", "Starting cameraS");
        Log.d("penis", "ONCREATE STARTED" );
        Log.d("pooper", "CAMERA ACTIVITY ONCREATE STARTED" );

        // Set the action bar
        setSupportActionBar(findViewById(R.id.toolbar));

        RecyclerView recyclerView = findViewById(R.id.lstLights);
        cameraListAdapter adapter = new cameraListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("cameras", "RECYCLER START");

        cameraViewModel = new ViewModelProvider(this).get(CameraViewModel.class);

        cameraViewModel.getAllcameras().observe(this, adapter::setcameras);
        Log.d("camerasName", "cameraViewModel onCreate:" + cameraViewModel.getAllcameras());
        //Log.d("camerasName", "cameraViewModel onCreate.getvalue.get(0).name:" + cameraViewModel.getAllcameras().getValue().get(0).name);
        //Log.d("camerasName", "cameraViewModel onCreate.getvalue.get(0).name:" + cameraViewModel.getAllcameras().getValue().get(0).name);
        Log.d("penis", "ONCREATE FINISHED" );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("cameras", "onCreateOptionsMenu" );
        Log.d("penis", "ONCREATE OPTIONS MENU START" );

        cameraViewModel.getAllcameras();

        getMenuInflater().inflate(R.menu.activity_lights_list, menu);

        if (filtered) {
            menu.getItem(1).setIcon(R.drawable.ic_thumbs_up_down);
        } else {
            menu.getItem(1).setIcon(R.drawable.ic_thumb_up);
        }
        Log.d("penis", "ONCREATEOPTIONS MENU STOP" );

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("cameras", "onOptionsItemSelected" );
        Log.d("penis", "ONOPTIONS START" );

        switch (item.getItemId()) {
            case R.id.menu_add:
                startActivity(new Intent(this, AddActivity.class));
                return true;
            case R.id.menu_filter:
                filtered = !filtered;
                if (filtered) {
                    item.setIcon(R.drawable.ic_thumbs_up_down);
                } else {
                    item.setIcon(R.drawable.ic_thumb_up);
                }
                RecyclerView recyclerView = findViewById(R.id.lstLights);
                cameraListAdapter adapter = new cameraListAdapter(this);
                recyclerView.setAdapter(adapter);
                //cameraViewModel = new ViewModelProvider(this).get(cameraViewModel.class);
                cameraViewModel.filtercameras(filtered);
                Log.d("camerasName", "cameraViewModel onOptionSelected:" + cameraViewModel.getAllcameras());

                cameraViewModel.getAllcameras().observe(this, adapter::setcameras);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("cameras", "onSaveInstanceState" );

        super.onSaveInstanceState(outState);
        outState.putBoolean("filtered", filtered);
    }

    public void displaySetup(int id) {
        Log.d("penis", "displaySetup, ID: "+id );

        CameraDatabase.getcamera(id, camera -> {
            Log.d("penis", "DISPLAY setup getCmera" );

            Bundle args = new Bundle();
            args.putInt("camera_id", camera.id);
            args.putString("name", camera.name);
            args.putInt("ipAddress", camera.ipAddress);
//            args.putInt("red", camera.red);
//            args.putInt("green", camera.green);
//            args.putInt("blue", camera.blue);

            DisplaySetupDialog setupDialog = new DisplaySetupDialog();
            setupDialog.setArguments(args);
            setupDialog.show(getSupportFragmentManager(), "setupDialog");
        });
    }

//    public void displayPunchline(int id) {
//        cameraDatabase.getcamera(id, camera -> {
//            Bundle args = new Bundle();
//            args.putInt("camera_id", camera.id);
//            args.putString("name", camera.name);
//            args.putInt("red", camera.red);
//            args.putInt("green", camera.green);
//            args.putInt("blue", camera.blue);
//
//            DisplayPunchlineDialog punchlineDialog = new DisplayPunchlineDialog();
//            punchlineDialog.setArguments(args);
//            punchlineDialog.show(getSupportFragmentManager(), "punchlineDialog");
//        });
//    }


    // Notes: This can be an outer class or a static nested class. We will make an inner class
    // since it is only used in the MainActivity _and_ we would like to simplify communication
    // with the activity















    public class cameraListAdapter extends RecyclerView.Adapter<cameraListAdapter.cameraViewHolder> {
        // If the cameraListAdapter were an outer class, the cameraViewHolder could be
        // a static class.  We want to be able to get access to the MainActivity instance,
        // so we want it to be an inner class
        public class cameraViewHolder extends RecyclerView.ViewHolder {
            private final TextView nameView;
            private final TextView ipAddress;
//            private final TextView redView;
//            private final TextView greenView;
//            private final TextView blueView;
            private final ImageView likedView;

            //private cameraStatus camera;
            private CameraStatus camera;

            // Note that this view holder will be used for different items -
            // The callbacks though will use the currently stored item
            private cameraViewHolder(View itemView) {
                super(itemView);
                Log.d("cameras","cameraViewHolder");
                Log.d("penis", "ON camera VIew HOLDer start?" );

                nameView = itemView.findViewById(R.id.txtName);
                ipAddress = itemView.findViewById(R.id.txtRed);
//                redView = itemView.findViewById(R.id.txtRed);
//                greenView = itemView.findViewById(R.id.txtGreen);
//                blueView = itemView.findViewById(R.id.txtBlue);
                likedView = itemView.findViewById(R.id.imgLiked);

                itemView.setOnLongClickListener(view -> {
                    // Note that we need a reference to the MainActivity instance
                    Intent intent = new Intent(camerasActivity.this, AddActivity.class);
                    // Note getItemId will return the database identifier
                    intent.putExtra("camera_id", camera.id);
                    // Note that we are calling a method of the MainActivity object
                    startActivity(intent);
                    return true;
                });

                itemView.setOnClickListener(view -> displaySetup(camera.id));

                likedView.setOnClickListener(view -> {
                    //if(camera.liked == null) camera.liked = true;
                    //else
                    if (camera.liked) camera.liked = false;
                    else camera.liked = true;

                    //camera.liked = !camera.liked;
                    CameraDatabase.update(camera.id, camera.liked);
                });
            }
        }

        private final LayoutInflater layoutInflater;
       // private List<cameraStatus> cameras; // Cached copy of cameras
        private List<CameraStatus> cameras; // Cached copy of cameras

        cameraListAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public cameraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("penis", "on create view holder -- cameraViewHOlder" );

            View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new cameraViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull cameraViewHolder holder, int position) {
//            Log.d("camerasName", "OnBindViewHolder");
            Log.d("penis", "onBindVIewHolder start!!!!!!!!!!!!!!" );

            if (cameras != null) {
                //Log.d("penis", "onBindVIewHOlder cameras!=null start" );

//                Log.d("cameras", "cameras != Null");



                //cameraStatus current = cameras.get(position);
                CameraStatus current = cameras.get(position);

//                Log.d("camerasName", "Position:" +
//                        position);
//                Log.d("camerasName", "get Position:" +
//                        cameras.get(position));
//                Log.d("camerasName", "getPosition NAMEEEEEE:" +
//                        cameras.get(position).name);
//                Log.d("camerasName", "CURRENT:" +
//                        current);
//
//                Log.d("camerasName", "CURRENT.name: " +
//                        current.name);
//
//                Log.d("camerasName", "CURRENT.red toString: " +
//                        Integer.toString(current.red));

                holder.camera = current;

                holder.nameView.setText("Camera: "+current.name);
                //holder.redView.setText(current.red);

//                holder.txtLevel.setText(String.format("Current Level: %.2f    Ideal Level: %.1f\nRange: %.1f-%.1f ",
//                        current.heightFt, current.idealLevel, current.lowLevel, current.highLevel));
//                holder.txtTemp.setText(String.format("Temperature %.1f C",current.tempC));
//                holder.txtRiver.setText(current.river);
//                Log.d("cameras", "About to set text redView...");


                holder.ipAddress.setText("IP Address: 192.168.0."+Integer.toString(current.ipAddress));
//                holder.redView.setText(Integer.toString(current.red));
//                holder.greenView.setText(Integer.toString(current.green));
//                holder.blueView.setText(Integer.toString(current.blue));
                /////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////
//                Log.d("cameras", "SET REDVIEW");


                if (current.liked) {
                    holder.likedView.setImageResource(R.drawable.ic_thumb_up);
                }
                else {
                    holder.likedView.setImageResource(R.drawable.ic_thumb_down);
                }
            } else {
//                Log.d("camerasName", "setting Null Values" );

                // Covers the case of data not being ready yet.
                holder.nameView.setText("...intializing...");
                holder.ipAddress.setText("ipAddress");
//                holder.redView.setText("Red");
//                holder.greenView.setText("Green");
//                holder.blueView.setText("Blue");

                holder.likedView.setImageResource(R.drawable.ic_thumb_down);
                holder.likedView.setTag("N");
//                Log.d("camerasName", "set Null Values setted" );

            }
        }

        void setcameras(List<CameraStatus> cameras){
            Log.d("penis", "setCameras" );
            Log.d("pooper", "setCameras function called" );

            this.cameras = cameras;
            for(int i =0; i<cameras.size();i++) {
                Log.d("camerasName", "cameras Name in setCameras:" + cameras.get(i).name);
            }

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            Log.d("penis", "getItemCount" );

            if (cameras != null)
                return cameras.size();
            else return 0;
        }


    }























    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class DisplaySetupDialog extends DialogFragment {
        int camera_id;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            camera_id = getArguments().getInt("camera_id");
            final String title = getArguments().getString("name");
            final Integer ipAddress = getArguments().getInt("ipAddress");
//            final Integer green = getArguments().getInt("green");
//            final Integer blue = getArguments().getInt("blue");
            builder.setTitle("Camera: "+title)
                    .setMessage("IP Address: 192.168.0."+ipAddress)
                    .setNeutralButton("Enable",(dialog, id) -> ((camerasActivity) getActivity()).EnablecameraColor(camera_id))
                    .setPositiveButton("Liked",(dialog, id) -> {
                        CameraDatabase.getcamera(camera_id, camera -> {
                            camera.liked = true;
                            CameraDatabase.update(camera);
                        });
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> {});
            return builder.create();
        }

        @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("JJB", "tester");
        }
    }

    public static void EnablecameraColor(int cameraID){
        //todo update camera colors ?
    }


//    public static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
//
//        static class ViewHolder extends RecyclerView.ViewHolder {
//            private final View viewGroup;
//            private final TextView name;
//            private final TextView red;
//            private final TextView blue;
//            private final TextView green;
//            private final ImageView likedView;
//           private cameraStatus cameraspot;
//
//            private ViewHolder(View itemView) {
//                super(itemView);
//                viewGroup = itemView;
//                name = itemView.findViewById(R.id.txtName);
//                red = itemView.findViewById(R.id.txtRed);
//                blue = itemView.findViewById(R.id.txtBlue);
//                green = itemView.findViewById(R.id.txtGreen);
//                likedView = itemView.findViewById(R.id.imgLiked);
//
//                likedView.setOnClickListener(view -> {
//                    if (cameraspot.liked == null) cameraspot.liked = true;
//                    else if (cameraspot.liked) cameraspot.liked = false;
//                    else cameraspot.liked = null;
//                });
//            }
//        }
//
//        private final LayoutInflater layoutInflater;
//       private List<cameraStatus> cameraspots;
//
//        ListAdapter(Context context) {
//            layoutInflater = LayoutInflater.from(context);
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
//            return new ViewHolder(itemView);
//        }
//
//        @SuppressLint("DefaultLocale")
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            if (cameraspots != null) {
//                cameraStatus current = cameraspots.get(position);
//                holder.cameraspot = current;
////                holder.txtcameraspot.setText(current.cameraspot);
////                holder.txtLevel.setText(String.format("Current Level: %.2f    Ideal Level: %.1f\nRange: %.1f-%.1f ",
////                        current.heightFt, current.idealLevel, current.lowLevel, current.highLevel));
////                holder.txtTemp.setText(String.format("Temperature %.1f C",current.tempC));
////                holder.txtRiver.setText(current.river);
//
////                if (current.ideal != null && current.ideal) {
////                    holder.viewGroup.setBackgroundColor(0xFF80C080);
////                }
////                else if (current.inPlay != null && current.inPlay) {
////                    holder.viewGroup.setBackgroundColor(0xFFAFFFAF);
////                }
////                else {
////                    holder.viewGroup.setBackgroundColor(Color.LTGRAY);
////                }
//
//                if (current.liked == null) {
//                    holder.likedView.setImageResource(R.drawable.ic_question);
//                }
//                else if (current.liked) {
//                    holder.likedView.setImageResource(R.drawable.ic_thumb_up);
//                }
//                else {
//                    holder.likedView.setImageResource(R.drawable.ic_thumb_down);
//                }
//            } else {
//                // Covers the case of data not being ready yet.
//                holder.cameraspot = null;
//                holder.name.setText("... initializing ...");
//                holder.red.setText("");
//                holder.green.setText("");
//                holder.blue.setText("");
//                holder.likedView.setImageResource(R.drawable.ic_question);
//            }
//        }
//
//        void setData(List<cameraStatus> cameraspots){
//            this.cameraspots = cameraspots;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public int getItemCount() {
//            if (cameraspots != null)
//                return cameraspots.size();
//            else return 0;
//        }
//    }


























}