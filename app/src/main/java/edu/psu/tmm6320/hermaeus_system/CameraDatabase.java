package edu.psu.tmm6320.hermaeus_system;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;



@Database(entities = {Camera.class},version = 1,exportSchema = false)
public abstract class CameraDatabase extends RoomDatabase {
    public interface cameraListener{
        void oncameraReturned(Camera camera);
    }

    public abstract CameraDAO cameraDAO();
    private static CameraDatabase INSTANCE;

    public static CameraDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (CameraDatabase.class){
                Log.d("cameras", "Instance is null??"+ INSTANCE);
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CameraDatabase.class,"camera_database")
                            .addCallback(createcameraDatabaseCallback)
                            .build();
                    Log.d("cameras", "CREATEING INSTANCE OF DB");


                }
            }
        }
        return INSTANCE;
    }
    private final static RoomDatabase.Callback createcameraDatabaseCallback =
            new RoomDatabase.Callback() {
            //@Override
            public void onCreate (@NonNull SupportSQLiteDatabase db){
                super.onCreate(db);
                for(int i=0;i<DefaultContent.NAME.length;i++){

//                    Log.d("databaseCreation", "database value entry: " +
//                            DefaultContent.NAME[i]+DefaultContent.RED[i]+DefaultContent.GREEN[i]+DefaultContent.BLUE[i]);

                     Log.d("cameras", "database value entry: " +
                            DefaultContent.NAME[i]+Integer.toString(DefaultContent.IPADDRESS[i]));

//                    insert(new Camera(0,DefaultContent.NAME[i],DefaultContent.RED[i],DefaultContent.GREEN[i],DefaultContent.BLUE[i],false));
                    insert(new Camera(0,DefaultContent.NAME[i],DefaultContent.IPADDRESS[i],false));

                }
                //createcameraTable();
        }};



//private static RoomDatabase.Callback createcameraDatabaseCallback =
//        new RoomDatabase.Callback(){
//            @Override
//            public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                super.onCreate(db);
//                for(int i =0;i<DefaultContent.TITLE.length;i++){
//                    insert(new camera(0,DefaultContent.NAME[i],DefaultContent.WIDTH[i],
//                            DefaultContent.HEIGHT[i]));
//                }
//            }
//        };
//    private static void createcameraTable(){
//        for(int i=0;i<DefaultContent.NAME.length;i++){
//            insert(new camera(0,DefaultContent.NAME[i],DefaultContent.RED[i],DefaultContent.GREEN[i],DefaultContent.BLUE[i],false));
//
//        }
//    }


    public static void getcamera(int id,cameraListener listener){
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message){
                super.handleMessage(message);
                listener.oncameraReturned((Camera) message.obj);
            }
        };
        (new Thread(()->{Message message = handler.obtainMessage();
                        message.obj = INSTANCE.cameraDAO().getById(id);
                        handler.sendMessage(message);
        })).start();


    }


    public static void insert(Camera camera){
        (new Thread(()->INSTANCE.cameraDAO().insert(camera))).start();
//        new Thread(()-> getDatabase(Context).cameraDAO().insert(camera)).start();

    }
    public static void delete(int cameraId){
        (new Thread(()->
                INSTANCE.cameraDAO().delete(cameraId))).start();
    }
    public static void update(Camera camera){
        (new Thread(()->INSTANCE.cameraDAO().update(camera))).start();
    }

    public static void update(int cameraID, boolean cameraLiked){
        (new Thread(()->INSTANCE.cameraDAO().update(cameraID,cameraLiked))).start();
    }


}
