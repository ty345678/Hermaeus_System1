//package edu.psu.tmm6320.hermaeus_system;
//
//import android.content.Context;
//import android.os.Looper;
//import android.os.Message;
//
//import androidx.annotation.NonNull;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//import java.util.logging.Handler;
//import java.util.logging.LogRecord;
//
//
//@Database(entities = {Camera.class},version = 1,exportSchema = false)
//public class CameraDatabase extends RoomDatabase {
//    public interface CameraListener{
//        void onCameraReturn(Camera camera);
//    }
//
//    public abstract CameraDAO cameraDAO();
//    private static CameraDatabase INSTANCE;
//
//    public static CameraDatabase getDatabase(final Context context){
//        if(INSTANCE==null){
//            synchronized (CameraDatabase.class){
//                if(INSTANCE==null){
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),CameraDatabase.class,"camera_database")
//                            .addCallback(createCameraDatabaseCallback)
//                            .build();
//
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//private static RoomDatabase.Callback createCameraDatabaseCallback =
//        new RoomDatabase.Callback(){
//            @Override
//            public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                super.onCreate(db);
//                for(int i =0;i<DefaultContent.TITLE.length;i++){
//                    insert(new Camera(0,DefaultContent.NAME[i],DefaultContent.WIDTH[i],
//                            DefaultContent.HEIGHT[i]));
//                }
//            }
//        };
//    public static void insert(Camera camera){
//        new Thread(()-> getDatabase(Context).cameraDAO().insert(camera)).start();
//
//    }
//
//    public static void getCamera(int id,final CameraListener listener){
//        Handler handler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message message){
//                super.handleMessage(message);
//                listener.onCameraReturn();
//
//
//            }
//
//
//        };
//        (new Thread(()->{Message message = handler.obtainMessage();
//                        message.obj = INSTANCE.cameraDAO().getById(id);
//                        handler.sendMessage(message);})).start();
//
//
//    }
//    public static void delete(Camera camera){
//        (new Thread(()->
//                INSTANCE.cameraDAO().delete(camera))).start();
//    }
//    public static void update(Camera camera){
//        (new Thread(()->INSTANCE.cameraDAO().update(camera))).start();
//    }
//
//
//
//
//}
