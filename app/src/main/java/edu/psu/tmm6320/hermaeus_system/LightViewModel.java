package edu.psu.tmm6320.hermaeus_system;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import androidx.lifecycle.AndroidViewModel;

public class LightViewModel extends AndroidViewModel{
   //private LiveData<List<LightStatus>> lights;
    private LiveData<List<Light>> lights;

    public LightViewModel (Application application){
        super(application);
        lights = LightDatabase.getDatabase(getApplication()).lightDAO().getAll();
    }

    public void filterLights(boolean onlyLiked){
        if(onlyLiked)
            lights = LightDatabase.getDatabase(getApplication()).lightDAO().getLiked(true);
        else
            lights = LightDatabase.getDatabase(getApplication()).lightDAO().getAll();
    }
    //public LiveData<List<LightStatus>> getAllLights(){return lights;}
    public LiveData<List<Light>> getAllLights(){return lights;}



}
