package edu.psu.tmm6320.hermaeus_system.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import edu.psu.tmm6320.hermaeus_system.Light;
import edu.psu.tmm6320.hermaeus_system.LightDatabase;
import edu.psu.tmm6320.hermaeus_system.LightStatus;
import edu.psu.tmm6320.hermaeus_system.R;

public class AddActivity extends AppCompatActivity {
        private int light_id;
        private boolean liked;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add);
            setSupportActionBar(findViewById(R.id.my_toolbar));

            light_id = getIntent().getIntExtra("light_id", -1);

            // Note: that we do not want to lose the state if the activity is being
            // recreated
            if (savedInstanceState == null) {
                if (light_id != -1) {
                    LightDatabase.getLight(light_id, light -> {
                        ((EditText) findViewById(R.id.txtEditTitle)).setText(light.name);
                        ((EditText) findViewById(R.id.txtEditRed)).setText(light.red);
                        ((EditText) findViewById(R.id.txtEditGreen)).setText(light.green);
                        ((EditText) findViewById(R.id.txtEditBlue)).setText(light.blue);
                        liked = light.liked;
                    });
                }
            }
            else {
                liked = savedInstanceState.getBoolean("liked");
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.activity_add, menu);
            if (light_id == -1) {
                menu.getItem(1).setIcon(R.drawable.ic_cancel);
                menu.getItem(1).setTitle(R.string.menu_cancel);
                setTitle("Add light");
            }
            else {
                setTitle("Edit light");
            }
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_save:
                    updateDatabase();
                    return true;
                case R.id.menu_delete:
                    if (light_id != -1) {
                        ConfirmDeleteDialog confirmDialog = new ConfirmDeleteDialog();
                        confirmDialog.show(getSupportFragmentManager(), "deletionConfirmation");
                    }
                    else {
                        finish();
                    }
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        private void updateDatabase() {
            Light light = new Light(light_id == -1?0:light_id,
                    ((EditText) findViewById(R.id.txtEditName)).getText().toString(),
                    Integer.parseInt(((EditText)findViewById(R.id.txtEditRed)).getText().toString()),
                    Integer.parseInt(((EditText)findViewById(R.id.txtEditGreen)).getText().toString()),
                    Integer.parseInt(((EditText)findViewById(R.id.txtEditBlue)).getText().toString()),
                    liked);
            if (light_id == -1) {
                LightDatabase.insert(light);
            } else {
                LightDatabase.update(light);
            }
            finish(); // Quit activity
        }

        public void deleteRecord() {
            LightDatabase.delete(light_id);
        }

        @Override
        protected void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putBoolean("liked", liked);
        }

        public static class ConfirmDeleteDialog extends DialogFragment {

            @Override
            public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Delete the light?")
                        .setMessage("You will not be able to undo the deletion!")
                        .setPositiveButton("Delete",
                                (dialog,id) -> {
                                    ((AddActivity) getActivity()).deleteRecord();
                                    getActivity().finish();
                                })
                        .setNegativeButton("Return to light list",
                                (dialog, id) -> getActivity().finish());
                return builder.create();
            }
        }

}


