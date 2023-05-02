package edu.psu.tmm6320.hermaeus_system;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import androidx.lifecycle.AndroidViewModel;

public class CameraViewModel extends AndroidViewModel{
   //private LiveData<List<camerastatus>> cameras;
    private LiveData<List<CameraStatus>> cameras;

    public CameraViewModel(Application application){
        super(application);
        cameras = CameraDatabase.getDatabase(getApplication()).cameraDAO().getAll();
    }

    public void filtercameras(boolean onlyLiked){
        if(onlyLiked)
            cameras = CameraDatabase.getDatabase(getApplication()).cameraDAO().getLiked(true);
        else
            cameras = CameraDatabase.getDatabase(getApplication()).cameraDAO().getAll();
    }
    //public LiveData<List<camerastatus>> getAllcameras(){return cameras;}
    public LiveData<List<CameraStatus>> getAllcameras(){
        return cameras;
    }



}
