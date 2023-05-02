package edu.psu.tmm6320.hermaeus_system.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.ScaleGestureDetector;

import com.longdo.mjpegviewer.MjpegView;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stream {
    public static final int MODE_ORIGINAL = 0;
    public static final int MODE_FIT_WIDTH = 1;
    public static final int MODE_FIT_HEIGHT = 2;
    public static final int MODE_BEST_FIT = 3;
    public static final int MODE_STRETCH = 4;
    private static final int WAIT_AFTER_READ_IMAGE_ERROR_MSEC = 5000;
    private static final int CHUNK_SIZE = 4096;
    private final String tag = this.getClass().getSimpleName();
//    private final Context context;
    private String url;
    private Bitmap lastBitmap;
//    private MjpegView.MjpegDownloader downloader;
    private final Object lockBitmap = new Object();
    private Paint paint;
    private Rect dst;
    private Rect noScaleDst;
    private int mode = 0;
    private int drawX;
    private int drawY;
    private int vWidth = -1;
    private int vHeight = -1;
    private int lastImgWidth;
    private int lastImgHeight;
    private boolean adjustWidth;
    private boolean adjustHeight;
    private int msecWaitAfterReadImageError = 5000;
    private boolean isRecycleBitmap;
    private boolean isUserForceConfigRecycle;
    private boolean isSupportPinchZoomAndPan;
//    private final ScaleGestureDetector.OnScaleGestureListener scaleGestureListener;
//    private final ScaleGestureDetector scaleGestureDetector;
//    private boolean isTouchDown;
//    private final PointF touchStart;
//    private final Rect stateStart;


 


    private boolean run = true;
    private Context context;

    public Stream() {
    }


    public void cancel() {
        this.run = false;
    }

    public boolean isRunning() {
        return this.run;
    }

    public void run() {
        while(this.run) {
            HttpURLConnection connection = null;
            BufferedInputStream bis = null;

            try {
                URL serverUrl = new URL(this.url);
                connection = (HttpURLConnection)serverUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                String headerBoundary = "[_a-zA-Z0-9]*boundary";

                int readByte;
                String trimmedCt;
                try {
                    String contentType = connection.getHeaderField("Content-Type");
                    if (contentType == null) {
                        throw new Exception("Unable to get content type");
                    }

                    String[] types = contentType.split(";");
                    if (types.length == 0) {
                        throw new Exception("Content type was empty");
                    }

                    String extractedBoundary = null;
                    String[] var8 = types;
                    int var9 = types.length;

                    for(readByte = 0; readByte < var9; ++readByte) {
                        String ct = var8[readByte];
                        trimmedCt = ct.trim();
                        if (trimmedCt.startsWith("boundary=")) {
                            extractedBoundary = trimmedCt.substring(9);
                        }
                    }

                    if (extractedBoundary == null) {
                        throw new Exception("Unable to find mjpeg boundary");
                    }

                    headerBoundary = extractedBoundary;
                } catch (Exception var19) {
                    Log.w(this.tag, "Cannot extract a boundary string from HTTP header with message: " + var19.getMessage() + ". Use a default value instead.");
                }

                Pattern pattern = Pattern.compile("--" + headerBoundary + "\\s+(.*)\\r\\n\\r\\n", 32);
                bis = new BufferedInputStream(connection.getInputStream());
                byte[] image = new byte[0];
                byte[] read = new byte[4096];

                while(this.run) {
                    try {
                        readByte = bis.read(read);
                        if (readByte == -1) {
                            break;
                        }

                        byte[] tmpCheckBoundry = this.addByte(image, read, 0, readByte);
                        trimmedCt = new String(tmpCheckBoundry, "ASCII");
                        Matcher matcher = pattern.matcher(trimmedCt);
                        if (matcher.find()) {
                            String boundary = matcher.group(0);
                            int boundaryIndex = trimmedCt.indexOf(boundary);
                            boundaryIndex -= image.length;
                            if (boundaryIndex > 0) {
                                image = this.addByte(image, read, 0, boundaryIndex);
                            } else {
                                image = this.delByte(image, -boundaryIndex);
                            }

                            Bitmap outputImg = BitmapFactory.decodeByteArray(image, 0, image.length);
                            if (outputImg != null) {
                                if (this.run) {
                                    this.newFrame(outputImg);
                                }
                            } else {
                                Log.e(this.tag, "Read image error");
                            }

                            int headerIndex = boundaryIndex + boundary.length();
                            image = this.addByte(new byte[0], read, headerIndex, readByte - headerIndex);
                        } else {
                            image = this.addByte(image, read, 0, readByte);
                        }
                    } catch (Exception var18) {
                        if (var18.getMessage() != null) {
                            Log.e(this.tag, var18.getMessage());
                        }
                        break;
                    }
                }
            } catch (Exception var20) {
                if (var20.getMessage() != null) {
                    Log.e(this.tag, var20.getMessage());
                }
            }

            try {
                bis.close();
                connection.disconnect();
                Log.i(this.tag, "disconnected with " + this.url);
            } catch (Exception var17) {
                if (var17.getMessage() != null) {
                    Log.e(this.tag, var17.getMessage());
                }
            }

            if (this.msecWaitAfterReadImageError > 0) {
                try {
                    Thread.sleep((long)this.msecWaitAfterReadImageError);
                } catch (InterruptedException var16) {
                    if (var16.getMessage() != null) {
                        Log.e(this.tag, var16.getMessage());
                    }
                }
            }
        }

    }

    private byte[] addByte(byte[] base, byte[] add, int addIndex, int length) {
        byte[] tmp = new byte[base.length + length];
        System.arraycopy(base, 0, tmp, 0, base.length);
        System.arraycopy(add, addIndex, tmp, base.length, length);
        return tmp;
    }

    private byte[] delByte(byte[] base, int del) {
        byte[] tmp = new byte[base.length - del];
        System.arraycopy(base, 0, tmp, 0, tmp.length);
        return tmp;
    }

    private void newFrame(Bitmap bitmap) {
        this.setBitmap(bitmap);
    }

    public void setBitmap(Bitmap bm) {
        synchronized(this.lockBitmap) {
            if (this.lastBitmap != null && this.isUserForceConfigRecycle && this.isRecycleBitmap) {
                this.lastBitmap.recycle();
            }

            this.lastBitmap = bm;
        }

        if (this.context instanceof Activity) {
            ((Activity)this.context).runOnUiThread(new Runnable() {
                public void run() {
//                    this.invalidate();
//                    this.requestLayout();
                }
            });
        } else {
            Log.e(this.tag, "Can not request Canvas's redraw. Context is not an instance of Activity");
        }

    }
    public void setUrl(String url) {
        this.url = url;
    }
    
}

    
    
    
    
    
    
