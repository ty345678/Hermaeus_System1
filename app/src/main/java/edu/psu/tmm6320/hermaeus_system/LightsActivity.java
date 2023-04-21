package edu.psu.tmm6320.hermaeus_system;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

        // Set the action bar
        setSupportActionBar(findViewById(R.id.toolbar));

        RecyclerView recyclerView = findViewById(R.id.lstLights);
        LightListAdapter adapter = new LightListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lightViewModel = new ViewModelProvider(this).get(LightViewModel.class);
        //lightViewModel.filterlights(filtered);

        //lightViewModel.getAlllights().observe(this, new Observer<List<light>>() {
        //    public void onChanged(@Nullable final List<light> lights) {
        //        // Update the cached copy of the words in the adapter.
        //        adapter.setLights(lights);
        //    }
        //});
        // OR As a lambda expression:
        //lightViewModel.getAlllights().observe(this, lights -> {adapter.setLights(lights);});
        // As a function reference
        // ContainingClass::staticMethodName
        // containingObject::instanceMethodName
        lightViewModel.getAllLights().observe(this, adapter::setLights);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);

        if (filtered) {
            menu.getItem(1).setIcon(R.drawable.ic_thumbs_up_down);
        } else {
            menu.getItem(1).setIcon(R.drawable.ic_thumb_up);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                lightViewModel = new ViewModelProvider(this).get(lightViewModel.class);
                lightViewModel.filterLights(filtered);

                lightViewModel.getAllLights().observe(this, adapter::setLights);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("filtered", filtered);
    }

    public void displaySetup(int id) {
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

    public void displayPunchline(int id) {
        LightDatabase.getLight(id, light -> {
            Bundle args = new Bundle();
            args.putInt("light_id", light.id);
            args.putString("name", light.name);
            args.putInt("red", light.red);
            args.putInt("green", light.green);
            args.putInt("blue", light.blue);

            DisplayPunchlineDialog punchlineDialog = new DisplayPunchlineDialog();
            punchlineDialog.setArguments(args);
            punchlineDialog.show(getSupportFragmentManager(), "punchlineDialog");
        });
    }


    // Notes: This can be an outer class or a static nested class. We will make an inner class
    // since it is only used in the MainActivity _and_ we would like to simplify communication
    // with the activity
    public class LightListAdapter extends RecyclerView.Adapter<LightListAdapter.LightViewHolder> {
        // If the lightListAdapter were an outer class, the lightViewHolder could be
        // a static class.  We want to be able to get access to the MainActivity instance,
        // so we want it to be an inner class
        class LightViewHolder extends RecyclerView.ViewHolder {
            private final TextView titleView;
            private final ImageView likedView;
            private Light light;

            // Note that this view holder will be used for different items -
            // The callbacks though will use the currently stored item
            private LightViewHolder(View itemView) {
                super(itemView);
                titleView = itemView.findViewById(R.id.txtTitle);
                likedView = itemView.findViewById(R.id.imgLiked);

                itemView.setOnLongClickListener(view -> {
                    // Note that we need a reference to the MainActivity instance
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    // Note getItemId will return the database identifier
                    intent.putExtra("light_id", light.id);
                    // Note that we are calling a method of the MainActivity object
                    startActivity(intent);
                    return true;
                });

                itemView.setOnClickListener(view -> displaySetup(light.id));

                likedView.setOnClickListener(view -> {
                    light.liked = !light.liked;
                    LightDatabase.update(light);
                });
            }
        }

        private final LayoutInflater layoutInflater;
        private List<Light> lights; // Cached copy of lights

        LightListAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public LightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new LightViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(LightViewHolder holder, int position) {
            if (lights != null) {
                Light current = lights.get(position);
                holder.light = current;
                holder.titleView.setText(current.name);
                if (current.liked) {
                    holder.likedView.setImageResource(R.drawable.ic_thumb_up);
                }
                else {
                    holder.likedView.setImageResource(R.drawable.ic_thumb_down);
                }
            } else {
                // Covers the case of data not being ready yet.
                holder.titleView.setText("...intializing...");
                holder.likedView.setImageResource(R.drawable.ic_thumb_down);
                holder.likedView.setTag("N");
            }
        }

        void setLights(List<Light> lights){
            this.lights = lights;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (lights != null)
                return lights.size();
            else return 0;
        }


    }


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
                    .setMessage("Red:"+red+" Green:"+green+" Blue:"+blue)
                    .setPositiveButton("Enable",
                            (dialog, id) -> ((MainActivity) getActivity()).displayPunchline(light_id))
                    .setNegativeButton("Cancel",
                            (dialog, id) -> {});
            return builder.create();
        }

        @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("JJB", "tester");
        }
    }

    public static class DisplayPunchlineDialog extends DialogFragment {
        int light_id;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            light_id = getArguments().getInt("light_id");
            String title = getArguments().getString("title");
            String punchline = getArguments().getString("punchline");

            builder.setTitle(title)
                    .setMessage(punchline)
                    .setPositiveButton("Liked", (dialog, id) -> {
                        LightDatabase.getLight(light_id, light -> {
                            light.liked = true;
                            LightDatabase.update(light);
                        });
                    })
                    .setNeutralButton("Setup", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ((MainActivity) getActivity()).displaySetup(light_id);
                        }
                    })
                    .setNegativeButton("Disiked", (dialog, id) -> {
                        LightDatabase.getLight(light_id, light -> {
                            light.liked = false;
                            LightDatabase.update(light);
                        });
                    });

            return builder.create();
        }
    }

}