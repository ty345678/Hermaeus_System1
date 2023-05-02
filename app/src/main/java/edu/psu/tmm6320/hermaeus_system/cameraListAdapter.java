//package edu.psu.tmm6320.hermaeus_system;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import edu.psu.tmm6320.hermaeus_system.Activity.AddActivity;
//import edu.psu.tmm6320.hermaeus_system.Activity.camerasActivity;
//
//public class cameraListAdapter {
//    // If the cameraListAdapter were an outer class, the cameraViewHolder could be
//    // a static class.  We want to be able to get access to the MainActivity instance,
//    // so we want it to be an inner class
////    class cameraViewHolder extends RecyclerView.ViewHolder {
////        private final TextView nameView;
////        private final TextView ipAddress;
////        //            private final TextView redView;
//////            private final TextView greenView;
//////            private final TextView blueView;
////        private final ImageView likedView;
////
////        //private cameraStatus camera;
////        private CameraStatus camera;
////
////        // Note that this view holder will be used for different items -
////        // The callbacks though will use the currently stored item
////        private cameraViewHolder(View itemView) {
////            super(itemView);
////            Log.d("cameras","cameraViewHolder");
////            Log.d("penis", "ON camera VIew HOLDer start?" );
////
////            nameView = itemView.findViewById(R.id.txtName);
////            ipAddress = itemView.findViewById(R.id.txtRed);
//////                redView = itemView.findViewById(R.id.txtRed);
//////                greenView = itemView.findViewById(R.id.txtGreen);
//////                blueView = itemView.findViewById(R.id.txtBlue);
////            likedView = itemView.findViewById(R.id.imgLiked);
////
////            itemView.setOnLongClickListener(view -> {
////                // Note that we need a reference to the MainActivity instance
////                Intent intent = new Intent(camerasActivity.this, AddActivity.class);
////                // Note getItemId will return the database identifier
////                intent.putExtra("camera_id", camera.id);
////                // Note that we are calling a method of the MainActivity object
////                startActivity(intent);
////                return true;
////            });
////
////            itemView.setOnClickListener(view -> camerasActivity.displaySetup(camera.id));
////
////            likedView.setOnClickListener(view -> {
////                //if(camera.liked == null) camera.liked = true;
////                //else
////                if (camera.liked) camera.liked = false;
////                else camera.liked = true;
////
////                //camera.liked = !camera.liked;
////                CameraDatabase.update(camera.id, camera.liked);
////            });
////        }
////    }
//
//    private final LayoutInflater layoutInflater;
//    // private List<cameraStatus> cameras; // Cached copy of cameras
//    private List<CameraStatus> cameras; // Cached copy of cameras
//
//    public cameraListAdapter(Context context) {
//        layoutInflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public cameraListAdapter.cameraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d("penis", "on create view holder -- cameraViewHOlder" );
//
//        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
//        return new camerasActivity.cameraViewHolder(itemView);
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(@NonNull camerasActivity.cameraViewHolder holder, int position) {
////            Log.d("camerasName", "OnBindViewHolder");
//        Log.d("penis", "onBindVIewHolder start!!!!!!!!!!!!!!" );
//
//        if (cameras != null) {
//            //Log.d("penis", "onBindVIewHOlder cameras!=null start" );
//
////                Log.d("cameras", "cameras != Null");
//
//
//
//            //cameraStatus current = cameras.get(position);
//            CameraStatus current = cameras.get(position);
//
////                Log.d("camerasName", "Position:" +
////                        position);
////                Log.d("camerasName", "get Position:" +
////                        cameras.get(position));
////                Log.d("camerasName", "getPosition NAMEEEEEE:" +
////                        cameras.get(position).name);
////                Log.d("camerasName", "CURRENT:" +
////                        current);
////
////                Log.d("camerasName", "CURRENT.name: " +
////                        current.name);
////
////                Log.d("camerasName", "CURRENT.red toString: " +
////                        Integer.toString(current.red));
//
//            holder.camera = current;
//
//            holder.nameView.setText("Camera: "+current.name);
//            //holder.redView.setText(current.red);
//
////                holder.txtLevel.setText(String.format("Current Level: %.2f    Ideal Level: %.1f\nRange: %.1f-%.1f ",
////                        current.heightFt, current.idealLevel, current.lowLevel, current.highLevel));
////                holder.txtTemp.setText(String.format("Temperature %.1f C",current.tempC));
////                holder.txtRiver.setText(current.river);
////                Log.d("cameras", "About to set text redView...");
//
//
//            holder.ipAddress.setText("IP Address: 192.168.0."+Integer.toString(current.ipAddress));
////                holder.redView.setText(Integer.toString(current.red));
////                holder.greenView.setText(Integer.toString(current.green));
////                holder.blueView.setText(Integer.toString(current.blue));
//            /////////////////////////////////////////////////////////////////
//            /////////////////////////////////////////////////////////////////
//            /////////////////////////////////////////////////////////////////
//            /////////////////////////////////////////////////////////////////
////                Log.d("cameras", "SET REDVIEW");
//
//
//            if (current.liked) {
//                holder.likedView.setImageResource(R.drawable.ic_thumb_up);
//            }
//            else {
//                holder.likedView.setImageResource(R.drawable.ic_thumb_down);
//            }
//        } else {
////                Log.d("camerasName", "setting Null Values" );
//
//            // Covers the case of data not being ready yet.
//            holder.nameView.setText("...intializing...");
//            holder.ipAddress.setText("ipAddress");
////                holder.redView.setText("Red");
////                holder.greenView.setText("Green");
////                holder.blueView.setText("Blue");
//
//            holder.likedView.setImageResource(R.drawable.ic_thumb_down);
//            holder.likedView.setTag("N");
////                Log.d("camerasName", "set Null Values setted" );
//
//        }
//    }
//
//    public void setcameras(List<CameraStatus> cameras){
//        Log.d("penis", "setCameras" );
//
//        this.cameras = cameras;
//        for(int i =0; i<cameras.size();i++) {
//            Log.d("camerasName", "cameras Name in setCameras:" + cameras.get(i).name);
//        }
//
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemCount() {
//        Log.d("penis", "getItemCount" );
//
//        if (cameras != null)
//            return cameras.size();
//        else return 0;
//    }
//
//
//}
