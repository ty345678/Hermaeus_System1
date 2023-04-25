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

import edu.psu.tmm6320.hermaeus_system.LightDatabase;
import edu.psu.tmm6320.hermaeus_system.LightStatus;
import edu.psu.tmm6320.hermaeus_system.LightViewModel;
import edu.psu.tmm6320.hermaeus_system.R;

public class LightsActivity extends AppCompatActivity {



    private boolean filtered = false;  // Are results filtered by likess
    private LightViewModel lightViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights_list);
        if (savedInstanceState != null) {
            filtered = savedInstanceState.getBoolean("filtered");
        }
        Log.d("lights", "Starting LIGHTS");
        // Set the action bar
        setSupportActionBar(findViewById(R.id.toolbar));

        RecyclerView recyclerView = findViewById(R.id.lstLights);
        LightListAdapter adapter = new LightListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("lights", "RECYCLER START");

        lightViewModel = new ViewModelProvider(this).get(LightViewModel.class);

        lightViewModel.getAllLights().observe(this, adapter::setLights);
        Log.d("lightsName", "lightViewModel onCreate:" + lightViewModel.getAllLights());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("lights", "onCreateOptionsMenu" );

        getMenuInflater().inflate(R.menu.activity_lights_list, menu);

        if (filtered) {
            menu.getItem(1).setIcon(R.drawable.ic_thumbs_up_down);
        } else {
            menu.getItem(1).setIcon(R.drawable.ic_thumb_up);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("lights", "onOptionsItemSelected" );

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
                LightListAdapter adapter = new LightListAdapter(this);
                recyclerView.setAdapter(adapter);
                //lightViewModel = new ViewModelProvider(this).get(LightViewModel.class);
                lightViewModel.filterLights(filtered);
                Log.d("lightsName", "lightViewModel onOptionSelected:" + lightViewModel.getAllLights());

                lightViewModel.getAllLights().observe(this, adapter::setLights);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("lights", "onSaveInstanceState" );

        super.onSaveInstanceState(outState);
        outState.putBoolean("filtered", filtered);
    }

    public void displaySetup(int id) {
        Log.d("lights", "displaySetup, ID: "+id );

        LightDatabase.getLight(id, light -> {
            Bundle args = new Bundle();
            args.putInt("light_id", light.id);
            args.putString("name", light.name);
            args.putInt("red", light.red);
            args.putInt("green", light.green);
            args.putInt("blue", light.blue);

            DisplaySetupDialog setupDialog = new DisplaySetupDialog();
            setupDialog.setArguments(args);
            setupDialog.show(getSupportFragmentManager(), "setupDialog");
        });
    }

//    public void displayPunchline(int id) {
//        LightDatabase.getLight(id, light -> {
//            Bundle args = new Bundle();
//            args.putInt("light_id", light.id);
//            args.putString("name", light.name);
//            args.putInt("red", light.red);
//            args.putInt("green", light.green);
//            args.putInt("blue", light.blue);
//
//            DisplayPunchlineDialog punchlineDialog = new DisplayPunchlineDialog();
//            punchlineDialog.setArguments(args);
//            punchlineDialog.show(getSupportFragmentManager(), "punchlineDialog");
//        });
//    }


    // Notes: This can be an outer class or a static nested class. We will make an inner class
    // since it is only used in the MainActivity _and_ we would like to simplify communication
    // with the activity
    public class LightListAdapter extends RecyclerView.Adapter<LightListAdapter.LightViewHolder> {
        // If the lightListAdapter were an outer class, the lightViewHolder could be
        // a static class.  We want to be able to get access to the MainActivity instance,
        // so we want it to be an inner class
        class LightViewHolder extends RecyclerView.ViewHolder {
            private final TextView nameView;
            private final TextView redView;
            private final TextView greenView;
            private final TextView blueView;
            private final ImageView likedView;
            private LightStatus light;

            // Note that this view holder will be used for different items -
            // The callbacks though will use the currently stored item
            private LightViewHolder(View itemView) {
                super(itemView);
                nameView = itemView.findViewById(R.id.txtName);
                redView = itemView.findViewById(R.id.txtRed);
                greenView = itemView.findViewById(R.id.txtGreen);
                blueView = itemView.findViewById(R.id.txtBlue);
                likedView = itemView.findViewById(R.id.imgLiked);

                itemView.setOnLongClickListener(view -> {
                    // Note that we need a reference to the MainActivity instance
                    Intent intent = new Intent(LightsActivity.this, AddActivity.class);
                    // Note getItemId will return the database identifier
                    intent.putExtra("light_id", light.id);
                    // Note that we are calling a method of the MainActivity object
                    startActivity(intent);
                    return true;
                });

                itemView.setOnClickListener(view -> displaySetup(light.id));

                likedView.setOnClickListener(view -> {
                    if(light.liked == null) light.liked = true;
                    else if (light.liked) light.liked = false;
                    else light.liked = false;

                    //light.liked = !light.liked;
                    LightDatabase.update(light.id, light.liked);
                });
            }
        }

        private final LayoutInflater layoutInflater;
        private List<LightStatus> lights; // Cached copy of lights

        LightListAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public LightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new LightViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LightViewHolder holder, int position) {
            Log.d("lights", "OnBindViewHolder");

            if (lights != null) {
                Log.d("lights", "Lights != Null");



                LightStatus current = lights.get(position);

                Log.d("lightsName", "Position:" +
                        position);
                Log.d("lightsName", "get Position:" +
                        lights.get(position));
                Log.d("lightsName", "getPosition NAMEEEEEE:" +
                        lights.get(position).name);
                Log.d("lightsName", "CURRENT:" +
                        current);

                Log.d("lightsName", "CURRENT: " +
                        current.name);

                holder.light = current;

                holder.nameView.setText(current.name);
                //holder.redView.setText(current.red);

//                holder.txtLevel.setText(String.format("Current Level: %.2f    Ideal Level: %.1f\nRange: %.1f-%.1f ",
//                        current.heightFt, current.idealLevel, current.lowLevel, current.highLevel));
//                holder.txtTemp.setText(String.format("Temperature %.1f C",current.tempC));
//                holder.txtRiver.setText(current.river);
                Log.d("lights", "About to set text redView...");

//                holder.redView.setText(current.red);
//                holder.greenView.setText(current.green);
//                holder.blueView.setText(current.blue);
                Log.d("lights", "SET REDVIEW");


                if (current.liked) {
                    holder.likedView.setImageResource(R.drawable.ic_thumb_up);
                }
                else {
                    holder.likedView.setImageResource(R.drawable.ic_thumb_down);
                }
            } else {
                Log.d("lightsName", "setting Null Values" );

                // Covers the case of data not being ready yet.
                holder.nameView.setText("...intializing...");
                holder.redView.setText("Red");
                holder.greenView.setText("Green");
                holder.blueView.setText("Blue");

                holder.likedView.setImageResource(R.drawable.ic_thumb_down);
                holder.likedView.setTag("N");
                Log.d("lightsName", "set Null Values setted" );

            }
        }

        void setLights(List<LightStatus> lights){
            this.lights = lights;
            Log.d("lightsName", "lights Name in setData:" + lights);

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (lights != null)
                return lights.size();
            else return 0;
        }


    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class DisplaySetupDialog extends DialogFragment {
        int light_id;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            light_id = getArguments().getInt("light_id");
            final String title = getArguments().getString("name");
            final Integer red = getArguments().getInt("red");
            final Integer green = getArguments().getInt("green");
            final Integer blue = getArguments().getInt("blue");
            builder.setTitle(title)
                    .setMessage("Red: "+red+"\nGreen: "+green+"\nBlue: "+blue)
                    .setNeutralButton("Enable",(dialog, id) -> ((LightsActivity) getActivity()).EnableLightColor(light_id))
                    .setPositiveButton("Liked",(dialog, id) -> {
                        LightDatabase.getLight(light_id, light -> {
                            light.liked = true;
                            LightDatabase.update(light);
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

    public static void EnableLightColor(int lightID){
        //todo update light colors ?
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
//           private LightStatus lightspot;
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
//                    if (lightspot.liked == null) lightspot.liked = true;
//                    else if (lightspot.liked) lightspot.liked = false;
//                    else lightspot.liked = null;
//                });
//            }
//        }
//
//        private final LayoutInflater layoutInflater;
//       private List<LightStatus> lightspots;
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
//            if (lightspots != null) {
//                LightStatus current = lightspots.get(position);
//                holder.lightspot = current;
////                holder.txtlightspot.setText(current.lightspot);
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
//                holder.lightspot = null;
//                holder.name.setText("... initializing ...");
//                holder.red.setText("");
//                holder.green.setText("");
//                holder.blue.setText("");
//                holder.likedView.setImageResource(R.drawable.ic_question);
//            }
//        }
//
//        void setData(List<LightStatus> lightspots){
//            this.lightspots = lightspots;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public int getItemCount() {
//            if (lightspots != null)
//                return lightspots.size();
//            else return 0;
//        }
//    }
}