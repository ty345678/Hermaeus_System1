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

import edu.psu.tmm6320.hermaeus_system.Camera;
import edu.psu.tmm6320.hermaeus_system.CameraDatabase;
import edu.psu.tmm6320.hermaeus_system.R;

public class AddActivity extends AppCompatActivity {
        private int camera_id;
        private boolean liked;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add);
            setSupportActionBar(findViewById(R.id.my_toolbar));

            camera_id = getIntent().getIntExtra("camera_id", -1);

            // Note: that we do not want to lose the state if the activity is being
            // recreated
            if (savedInstanceState == null) {
                if (camera_id != -1) {
                    CameraDatabase.getcamera(camera_id, camera -> {
                        ((EditText) findViewById(R.id.txtEditTitle)).setText(camera.name);
                        ((EditText) findViewById(R.id.txtEditRed)).setText(camera.ipAddress);
                        
//                        ((EditText) findViewById(R.id.txtEditGreen)).setText(camera.green);
//                        ((EditText) findViewById(R.id.txtEditBlue)).setText(camera.blue);
                        liked = camera.liked;
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
            if (camera_id == -1) {
                menu.getItem(1).setIcon(R.drawable.ic_cancel);
                menu.getItem(1).setTitle(R.string.menu_cancel);
                setTitle("Add camera");
            }
            else {
                setTitle("Edit camera");
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
                    if (camera_id != -1) {
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
            Camera camera = new Camera(camera_id == -1?0:camera_id,
                    ((EditText) findViewById(R.id.txtEditName)).getText().toString(),
                    Integer.parseInt(((EditText)findViewById(R.id.txtEditRed)).getText().toString()),
//                    Integer.parseInt(((EditText)findViewById(R.id.txtEditGreen)).getText().toString()),
//                    Integer.parseInt(((EditText)findViewById(R.id.txtEditBlue)).getText().toString()),
                    liked);
            if (camera_id == -1) {
                CameraDatabase.insert(camera);
            } else {
                CameraDatabase.update(camera);
            }
            finish(); // Quit activity
        }

        public void deleteRecord() {
            CameraDatabase.delete(camera_id);
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

                builder.setTitle("Delete the camera?")
                        .setMessage("You will not be able to undo the deletion!")
                        .setPositiveButton("Delete",
                                (dialog,id) -> {
                                    ((AddActivity) getActivity()).deleteRecord();
                                    getActivity().finish();
                                })
                        .setNegativeButton("Return to camera list",
                                (dialog, id) -> getActivity().finish());
                return builder.create();
            }
        }

}


