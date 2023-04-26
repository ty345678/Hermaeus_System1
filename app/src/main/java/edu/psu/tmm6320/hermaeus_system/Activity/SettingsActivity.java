package edu.psu.tmm6320.hermaeus_system.Activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import edu.psu.tmm6320.hermaeus_system.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.frag_settings, new SettingsFragment())
//                .commit();

    }



    /**
     * Called when the user has clicked on a preference that has a fragment class name
     * associated with it. The implementation should instantiate and switch to an instance
     * of the given fragment.
     *
     * @param caller The fragment requesting navigation
     * @param pref   The preference requesting the fragment
     * @return {@code true} if the fragment creation has been handled
     */
//    @Override
//    public boolean onPreferenceStartFragment(@NonNull PreferenceFragmentCompat caller, @NonNull Preference pref) {
//        // Instantiate the new Fragment
//        final Bundle args = pref.getExtras();
//        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
//                getClassLoader(),
//                pref.getFragment());
//        fragment.setArguments(args);
//        fragment.setTargetFragment(caller, 0);
//        // Replace the existing Fragment with the new Fragment
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.camera_Ip_List_id, fragment)
//                .addToBackStack(null)
//                .commit();
//        return true;
//    }





    public static class SettingsFragment extends PreferenceFragmentCompat{
//        @Override
//        public void onCreate(Bundle savedInstanceState){
////            Log.d("poop", "Starting settingsFragment");
//
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.preferences);
//            //PreferenceManager.setDefaultValues(this, R.xml.advanced_preferences, false);
//        }


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey){
            setPreferencesFromResource(R.xml.preferences,rootKey);

        }

    }



}
