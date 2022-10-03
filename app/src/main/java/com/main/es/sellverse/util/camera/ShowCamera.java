package com.main.es.sellverse.util.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ZoomControls;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.List;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {
    Camera camera;
    SurfaceHolder holder;
    float mDist;
    public ShowCamera(Context context, Camera camera) {
        super(context);

        this.camera=camera;

        holder=getHolder();
        holder.addCallback(this);

    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Camera.Parameters params= camera.getParameters();

        Camera.Size msize = getBetterSize(params);

        controlRotation(surfaceHolder, params,msize);
    }


/*
Gets the best resolution of the phone or emulator
 */
    @Nullable
    private Camera.Size getBetterSize(Camera.Parameters params) {
        List<Camera.Size>sizes= params.getSupportedPictureSizes();
        Camera.Size msize=null;
        for(Camera.Size size:sizes){
            msize=size;
        }
        return msize;
    }

    private void controlRotation(@NonNull SurfaceHolder surfaceHolder, Camera.Parameters params, Camera.Size msize) {
        if(this.getResources().getConfiguration().orientation!= Configuration.ORIENTATION_LANDSCAPE)
            {

                params.set("orientation","portrait");
                camera.setDisplayOrientation(90);
                params.setRotation(90);

            }
        else{
            params.set("orientation","landscape");
            camera.setDisplayOrientation(0);
            params.setRotation(0);
        }
        camera.setParameters(params);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            params.setPictureSize(msize.width,msize.height);
            camera.startPreview();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Get the pointer ID
        Camera.Parameters params = camera.getParameters();
        int action = event.getAction();


        if (event.getPointerCount() > 1) {
            // handle multi-touch events
            if (action == MotionEvent.ACTION_POINTER_DOWN) {
                mDist = getFingerSpacing(event);
            } else if (action == MotionEvent.ACTION_MOVE && params.isZoomSupported()) {
                camera.cancelAutoFocus();
                handleZoom(event, params);
            }
        } else {
            // handle single touch events
            if (action == MotionEvent.ACTION_UP) {
                handleFocus(event, params);
            }
        }
        return true;
    }
    private void handleZoom(MotionEvent event, Camera.Parameters params) {
        int maxZoom = params.getMaxZoom();
        int zoom = params.getZoom();

        float newDist = getFingerSpacing(event);
        if (newDist > mDist) {
            //zoom in
            if (zoom < maxZoom)
                zoom++;
        } else if (newDist < mDist) {
            //zoom out
            if (zoom > 0)
                zoom--;
        }
        mDist = newDist;
        params.setZoom(zoom);
        camera.setParameters(params);
    }
    public void handleFocus(MotionEvent event, Camera.Parameters params) {
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        // Get the pointer's current position
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        List<String> supportedFocusModes = params.getSupportedFocusModes();
        if (supportedFocusModes != null && supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    // currently set to auto-focus on single touch
                }
            });
        }
    }

    /** Determine the space between the first two fingers */
    private float getFingerSpacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        //camera.release();
    }
}
