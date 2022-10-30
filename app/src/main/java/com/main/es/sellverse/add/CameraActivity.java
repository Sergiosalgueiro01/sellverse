package com.main.es.sellverse.add;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;

import com.main.es.sellverse.R;
import com.main.es.sellverse.util.camera.ShowCamera;
import com.main.es.sellverse.util.cropper.AddPublicationCropper;
import com.main.es.sellverse.util.cropper.CropUtil;
import com.main.es.sellverse.util.datasavers.TemporalUriSaver;
import com.main.es.sellverse.util.images.GalleryUtil;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
private ShowCamera showCamera;
private Camera camera;
private ConstraintLayout relativeLayout;
private  ImageButton b;
private ImageButton b2;
private int positionOfCamera;
private static final String TAG="AppDebug";
private int counter=0;
private boolean isOnGallery=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setUp();
        setUpSelfieButton();
        setUpGalleryButton();
        setUpCaptureButton();

    }

    


    private void setUp() {
        camera=Camera.open(0);
        positionOfCamera=0;

        relativeLayout=findViewById(R.id.fmCamera);
        showCamera=new ShowCamera(this,camera);
        relativeLayout.addView(showCamera);
        Display display = getWindowManager().getDefaultDisplay();
        int height = display.getHeight();

        relativeLayout.setMaxHeight((int) (height/1.1));
        relativeLayout.setMinHeight((int) (height/1.1 ));
         b = findViewById(R.id.ibX);
         b2 = findViewById(R.id.btnCapturar);
        loadButtonsInCamera();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void setUpGalleryButton() {
        ImageButton button = findViewById(R.id.btnGaleria);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImageUsingGallery();
            }
        });
    }
    
    private void setUpCaptureButton() {
        ImageButton button = findViewById(R.id.btnCapturar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });
    }
    private Camera.PictureCallback mPictureCallBack =new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {

            File file = getOutPutMediaFile();
            if(file==null)
                return;
            else{
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                toCrop(file);

            }

        }
    };

    private void toCrop(File file) {
        Uri uri = Uri.fromFile(file);
        CropUtil.launchImageCrop(uri, new AddPublicationCropper(this),this);
    }

    private File getOutPutMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "Sellverse");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Sellverse", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }


    private void captureImage() {
        if (camera != null) {
            try {
                camera.takePicture(null, null, mPictureCallBack);
            } catch (Exception e) {

            }
        }
    }

    private void captureImageUsingGallery() {
        isOnGallery=true;
        GalleryUtil.pickFromGallery(this);

    }


    @Override
    protected void onStop() {
        camera=null;
        super.onStop();
    }

    private void loadButtonsInCamera() {
        relativeLayout.removeView(b);
        relativeLayout.removeView(b2);
        relativeLayout.addView(b);
        relativeLayout.addView(b2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isOnGallery){
        if (counter == 1) {
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
            finish();

        }
        }
        isOnGallery=false;
        counter++;
    }



    private void setUpSelfieButton() {
        ImageButton selfieButton = findViewById(R.id.btnSelfie);
        selfieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                camera.stopPreview();
                camera.release();
                if(positionOfCamera==0){
                    positionOfCamera=1;
                    camera=Camera.open(1);}
                else{
                    positionOfCamera=0;
                    camera=Camera.open(0);}
                setUpCamera();
            }
        });
    }

    private void setUpCamera() {
        showCamera=new ShowCamera(this,camera);
        relativeLayout.addView(showCamera);
        loadButtonsInCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case GalleryUtil.GALLERY_REQUEST_CODE:
                if(resultCode==Activity.RESULT_OK){
                    Uri uri = data.getData();
                    CropUtil.launchImageCrop(uri,new AddPublicationCropper(this),this);
                }
                else{
                    Log.e(TAG, "Image selection error: Couldn't select that image from memory." );
                }
                break;
            case CropUtil.CROP_REQUEST_CODE:
                CropImage.ActivityResult result= CropImage.getActivityResult(data);
                if(resultCode==Activity.RESULT_OK){
                    TemporalUriSaver.getInstance().temporalUri=result.getUri();
                    TemporalUriSaver.getInstance().hasChange=true;
                    finish();
                }
                else if(resultCode ==CropUtil.CROP_ERROR_CODE){
                    Log.e(TAG, "Crop error: ${result.error}");

                }
                }



        }
    }
