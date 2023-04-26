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



@Database(entities = {Light.class},version = 1,exportSchema = false)
public abstract class LightDatabase extends RoomDatabase {
    public interface LightListener{
        void onLightReturned(Light light);
    }

    public abstract LightDAO lightDAO();
    private static LightDatabase INSTANCE;

    public static LightDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (LightDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    LightDatabase.class,"light_database")
                            .addCallback(createLightDatabaseCallback)
                            .build();
                    Log.d("lightss", "CREATEING INSTANCE OF DB");


                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback createLightDatabaseCallback = new RoomDatabase.Callback() {
            //@Override
            public void OnCreate (@NonNull SupportSQLiteDatabase db){
                super.onCreate(db);
                for(int i=0;i<DefaultContent.NAME.length;i++){

                    Log.d("databaseCreation", "database value entry: " +
                            DefaultContent.NAME[i]+DefaultContent.RED[i]+DefaultContent.GREEN[i]+DefaultContent.BLUE[i]);

                    insert(new Light(0,DefaultContent.NAME[i],DefaultContent.RED[i],DefaultContent.GREEN[i],DefaultContent.BLUE[i],false));

                }
                //createLightTable();
        }
    };



//private static RoomDatabase.Callback createLightDatabaseCallback =
//        new RoomDatabase.Callback(){
//            @Override
//            public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                super.onCreate(db);
//                for(int i =0;i<DefaultContent.TITLE.length;i++){
//                    insert(new Light(0,DefaultContent.NAME[i],DefaultContent.WIDTH[i],
//                            DefaultContent.HEIGHT[i]));
//                }
//            }
//        };
//    private static void createLightTable(){
//        for(int i=0;i<DefaultContent.NAME.length;i++){
//            insert(new Light(0,DefaultContent.NAME[i],DefaultContent.RED[i],DefaultContent.GREEN[i],DefaultContent.BLUE[i],false));
//
//        }
//    }


    public static void getLight(int id,LightListener listener){
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message){
                super.handleMessage(message);
                listener.onLightReturned((Light) message.obj);
            }
        };
        (new Thread(()->{Message message = handler.obtainMessage();
                        message.obj = INSTANCE.lightDAO().getById(id);
                        handler.sendMessage(message);
        })).start();


    }


    public static void insert(Light light){
        (new Thread(()->INSTANCE.lightDAO().insert(light))).start();
//        new Thread(()-> getDatabase(Context).cameraDAO().insert(light)).start();

    }
    public static void delete(int lightId){
        (new Thread(()->
                INSTANCE.lightDAO().delete(lightId))).start();
    }
    public static void update(Light light){
        (new Thread(()->INSTANCE.lightDAO().update(light))).start();
    }

    public static void update(int lightID, boolean lightLiked){
        (new Thread(()->INSTANCE.lightDAO().update(lightID,lightLiked))).start();
    }


}
