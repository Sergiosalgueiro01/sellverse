package com.main.es.sellverse.add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.main.es.sellverse.R;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.persistence.AuctionDataBase;
import com.main.es.sellverse.persistence.UserDataBase;
import com.main.es.sellverse.util.datasavers.TemporalBitmapSaver;
import com.main.es.sellverse.util.datasavers.TemporalStringSaver;
import com.main.es.sellverse.util.datasavers.TemporalTimeSaver;
import com.main.es.sellverse.util.datasavers.TemporalUriSaver;
import com.main.es.sellverse.util.date.DateConvertionUtil;
import com.main.es.sellverse.util.hash.HashGenerator;
import com.main.es.sellverse.util.pickers.DatePickerFragment;
import com.main.es.sellverse.util.pickers.TimePickerFragment;

import org.checkerframework.checker.units.qual.A;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;


public class AddPublicationActivity extends AppCompatActivity{
    private Auction auctionToAdd;
    private ImageButton ib1 ;
    private ImageButton ib2 ;
    private ImageButton ib3 ;
    private ImageButton ib4;
    private ImageButton ib5 ;
    private ImageButton ib6 ;
    private ImageView iv1;
    private ImageView iv2 ;
    private ImageView iv3 ;
    private ImageView iv4 ;
    private ImageView iv5 ;
    private ImageView iv6;
    private int firstDimensions;
    private  ConstraintLayout c;
    private int numberOfImages=0;
    private boolean hasChange=false;
    private int counter=0;

    private String[] permissions= new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static int REQUEST_CODE = 1;
    private int cont=0; //Just for testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);

        setUpMenu();
        setUpImageButton();
        setUpEditText();
        setUpRadioButton();
        setUpEditTextDatePicker();
        setUpEditTextTimePicker();

    }





    private void setUpEditTextTimePicker() {
        EditText et = findViewById(R.id.etTime);
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(et);
            }
        });
    }

    private void showTimePickerDialog(EditText et) {
        TimePickerFragment timePicker= new TimePickerFragment(et);
        timePicker.show(this.getSupportFragmentManager(),"timePicker");

    }

    private void setUpEditTextDatePicker() {
        EditText et = findViewById(R.id.etDay);
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(et);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showDatePickerDialog(EditText et) {
        DatePickerFragment datePickerFragment=new DatePickerFragment(et);
        datePickerFragment.show(this.getSupportFragmentManager(),"datePicker");


    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpRadioButton() {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        EditText tvTime = findViewById(R.id.etTime);
        EditText tvDay= findViewById(R.id.etDay);
        TextInputLayout tvTitle= findViewById(R.id.tilTitle);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               View radiobutton=radioGroup.findViewById(i);
               int index=radioGroup.indexOfChild(radiobutton);
               switch (index){
                   case 0:
                       tvTime.setVisibility(View.INVISIBLE);
                       tvDay.setVisibility(View.INVISIBLE);
                       break;
                   case 1:
                       tvTime.setVisibility(View.VISIBLE);
                       tvDay.setVisibility(View.VISIBLE);


                       break;
               }
            }
        });
    }

    /**
     * Set up max lines on a editText
     */
    private void setUpEditText() {
        final int[] lastSpecialRequestsCursorPosition = {0};
        final String[] specialRequest = {""};
        EditText et = findViewById(R.id.tietDescription);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lastSpecialRequestsCursorPosition[0] = et.getSelectionStart();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                et.removeTextChangedListener(this);
                if(et.getLineCount() >13){ //max of lines of editText
                    et.setText(specialRequest[0]);
                    et.setSelection(lastSpecialRequestsCursorPosition[0]);
                }
                else{
                    specialRequest[0] = et.getText().toString();
                }
                et.addTextChangedListener(this);
            }
        });
    }


    private void setUpMenu() {
        Toolbar toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.add_publication);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_accept_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        askIfAreYouSure();

    }


    private void askIfAreYouSure() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.are_you_sure);
        dialog.setMessage(R.string.are_you_sure_message);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.positiveButonCheckPublication:
                addToDatabase();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setUpImageButton() {
        ib1 = findViewById(R.id.ibPub1);
        ib2 = findViewById(R.id.ibPub2);
        ib3 = findViewById(R.id.ibPub3);
        ib4 = findViewById(R.id.ibPub4);
        ib5 = findViewById(R.id.ibPub5);
        ib6 = findViewById(R.id.ibPub6);
        iv1=findViewById(R.id.ivPlus1);
        iv2 = findViewById(R.id.ivPlus2);
        iv3 = findViewById(R.id.ivPlus3);
        iv4 = findViewById(R.id.ivPlus4);
        iv5 = findViewById(R.id.ivPlus5);
        iv6 = findViewById(R.id.ivPlus6);
        c = findViewById(R.id.linearLayout2);
        ib2.setVisibility(View.GONE);
        iv2.setVisibility(View.GONE);
        ib3.setVisibility(View.GONE);
        iv3.setVisibility(View.GONE);
        c.setVisibility(View.GONE);
        firstDimensions=getTvDimensions();

        buttonSetOnClickListener(ib1,iv1);


    }

    private void buttonSetOnClickListener(ImageButton ib, ImageView iv1) {

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemporalUriSaver.getInstance().lastButtonChanged=ib;
                TemporalUriSaver.getInstance().lastImageViewChanged=iv1;
                ViewGroup.LayoutParams params = iv1.getLayoutParams();

                if(params.height!=firstDimensions){
                    showPopup(ib,iv1);
                }
                else
                    tryToOpenCamera();

            }
        });
    }


    private void tryToOpenCamera() {
        if(hasCameraPermissions() && hasWriteToStoragePermissions()
                && hasReadStoragePermissions()){

            openCameraActivity();
        }
        else{

            if(cont==0){
                askForCameraPermission();
            }
            else{
                askIfAreYouWantToChangePermission();
            }
        }
    }
    private void showPopup(ImageButton ib,ImageView iv){
        PopupMenu popup = new PopupMenu(this,ib);
        popup.inflate(R.menu.popup_add_publication_menu);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.chageImage:
                        tryToOpenCamera();
                        return true;
                    case R.id.seeImage:
                        openSeePhotoActivity(iv);
                        return true;
                    case R.id.deleteImage:
                        numberOfImages--;
                        checkDeletedImages(iv);
                }
                return false;
            }
        });
        popup.show();
    }

    private void deleteImage(ImageView iv) {
        ViewGroup.LayoutParams params = iv.getLayoutParams();
        params.height=firstDimensions;
        params.width=firstDimensions;
        iv.setImageResource(R.drawable.btn_add);


    }

    private void checkDeletedImages(ImageView iv) {
        if(iv6.equals(iv)){
            deleteImage(iv6);
        }
        else if(iv5.equals(iv)){
            iv5.setImageBitmap(((BitmapDrawable)iv6.getDrawable()).getBitmap());

              if(getHeightParams(iv6)==firstDimensions && iv6.getVisibility()==View.VISIBLE){
                deleteImage(iv5);
                iv6.setVisibility(View.GONE);
                ib6.setVisibility(View.GONE);
            }
              else deleteImage(iv6);
        }
        else if(iv4.equals(iv)){
            iv4.setImageBitmap(((BitmapDrawable)iv5.getDrawable()).getBitmap());
            iv5.setImageBitmap(((BitmapDrawable)iv6.getDrawable()).getBitmap());
            if(getHeightParams(iv6)==firstDimensions && iv6.getVisibility()==View.VISIBLE){
                deleteImage(iv5);
                iv6.setVisibility(View.GONE);
                ib6.setVisibility(View.GONE);
            }
            else if(getHeightParams(iv5)==firstDimensions && iv5.getVisibility()==View.VISIBLE){
                deleteImage(iv4);
                iv5.setVisibility(View.GONE);
                ib5.setVisibility(View.GONE);
            }
           else deleteImage(iv6);

        }
        else if(iv3.equals(iv)){
            iv3.setImageBitmap(((BitmapDrawable)iv4.getDrawable()).getBitmap());
            iv4.setImageBitmap(((BitmapDrawable)iv5.getDrawable()).getBitmap());
            iv5.setImageBitmap(((BitmapDrawable)iv6.getDrawable()).getBitmap());
            if(getHeightParams(iv6)==firstDimensions && iv6.getVisibility()==View.VISIBLE){
                deleteImage(iv5);
                iv6.setVisibility(View.GONE);
                ib6.setVisibility(View.GONE);
            }
            else if(getHeightParams(iv5)==firstDimensions&& iv5.getVisibility()==View.VISIBLE){
                deleteImage(iv4);
                iv5.setVisibility(View.GONE);
                ib5.setVisibility(View.GONE);
            }
            else if(getHeightParams(iv4)==firstDimensions && iv4.getVisibility()==View.VISIBLE){
                deleteImage(iv3);
                c.setVisibility(View.GONE);
                iv4.setVisibility(View.GONE);
                ib4.setVisibility(View.GONE);
            }
            else deleteImage(iv6);
        }
        else if(iv2.equals(iv)){
            iv2.setImageBitmap(((BitmapDrawable)iv3.getDrawable()).getBitmap());
            iv3.setImageBitmap(((BitmapDrawable)iv4.getDrawable()).getBitmap());
            iv4.setImageBitmap(((BitmapDrawable)iv5.getDrawable()).getBitmap());
            iv5.setImageBitmap(((BitmapDrawable)iv6.getDrawable()).getBitmap());
            if(getHeightParams(iv6)==firstDimensions && iv6.getVisibility()==View.VISIBLE
                    &&c.getVisibility()==View.VISIBLE){
                deleteImage(iv5);
                iv6.setVisibility(View.GONE);
                ib6.setVisibility(View.GONE);
            }
            else if(getHeightParams(iv5)==firstDimensions&& iv5.getVisibility()==View.VISIBLE
                    &&c.getVisibility()==View.VISIBLE){
                deleteImage(iv4);
                iv5.setVisibility(View.GONE);
                ib5.setVisibility(View.GONE);
            }
            else if(getHeightParams(iv4)==firstDimensions && iv4.getVisibility()==View.VISIBLE
                    &&c.getVisibility()==View.VISIBLE){
                deleteImage(iv3);
                iv4.setVisibility(View.GONE);
                ib4.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
            }
            else if(getHeightParams(iv3)==firstDimensions && iv3.getVisibility()==View.VISIBLE){
                deleteImage(iv2);
                iv3.setVisibility(View.GONE);
                ib3.setVisibility(View.GONE);
            }
            else deleteImage(iv6);

        }
        else if (iv1.equals(iv)){
            iv1.setImageBitmap(((BitmapDrawable)iv2.getDrawable()).getBitmap());
            iv2.setImageBitmap(((BitmapDrawable)iv3.getDrawable()).getBitmap());
            iv3.setImageBitmap(((BitmapDrawable)iv4.getDrawable()).getBitmap());
            iv4.setImageBitmap(((BitmapDrawable)iv5.getDrawable()).getBitmap());
            iv5.setImageBitmap(((BitmapDrawable)iv6.getDrawable()).getBitmap());
            if(getHeightParams(iv6)==firstDimensions && iv6.getVisibility()==View.VISIBLE
                    &&c.getVisibility()==View.VISIBLE){
                deleteImage(iv5);
                iv6.setVisibility(View.GONE);
                ib6.setVisibility(View.GONE);
            }
            else if(getHeightParams(iv5)==firstDimensions&& iv5.getVisibility()==View.VISIBLE
                    &&c.getVisibility()==View.VISIBLE){
                deleteImage(iv4);
                iv5.setVisibility(View.GONE);
                ib5.setVisibility(View.GONE);
            }
            else if(getHeightParams(iv4)==firstDimensions && iv4.getVisibility()==View.VISIBLE
                    &&c.getVisibility()==View.VISIBLE){
                deleteImage(iv3);
                iv4.setVisibility(View.GONE);
                ib4.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
            }
            else if(getHeightParams(iv3)==firstDimensions && iv3.getVisibility()==View.VISIBLE){
                deleteImage(iv2);
                iv3.setVisibility(View.GONE);
                ib3.setVisibility(View.GONE);
            }
            else if(getHeightParams(iv2)==firstDimensions && iv2.getVisibility()==View.VISIBLE){
                deleteImage(iv1);
                iv2.setVisibility(View.GONE);
                ib2.setVisibility(View.GONE);
            }
            else deleteImage(iv6);
        }


    }

    private int getHeightParams(ImageView iv) {
        ViewGroup.LayoutParams params = iv.getLayoutParams();
        return params.height;
    }

    private void openSeePhotoActivity(ImageView iv) {
        Bitmap bm=((BitmapDrawable)iv.getDrawable()).getBitmap();
        TemporalBitmapSaver.getInstance().temporalBitmap=bm;
        Intent intent = new Intent(this,SeeImageActivity.class);
        startActivity(intent);


    }

    /**
     * Return true if has camera permisions and false if not
     */
    private Boolean hasCameraPermissions(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }
    /**
     * Return true if has writeStorage permisions and false if not
     */
    private Boolean hasWriteToStoragePermissions(){
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }
    /**
     * Return true if has readStorage permisions and false if not
     */
    private Boolean hasReadStoragePermissions(){
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }
    /**
     * Ask for take Permision
     */
    private void askForCameraPermission() {

        requestPermissions(
                permissions,
                REQUEST_CODE);



    }
    private void askIfAreYouWantToChangePermission() {

        AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        dialog.setTitle(R.string.upload_an_image);
        dialog.setMessage(R.string.info_permissions);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.open_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirVentanaPermisos();
            }
        });
        dialog.setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();


    }
    private void abrirVentanaPermisos() {

        Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData( Uri.parse("package:" +getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(i);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cont++;
        if(hasCameraPermissions() && hasWriteToStoragePermissions() && hasReadStoragePermissions()){
            openCameraActivity();
        }
        else{
            Toast.makeText(
                            this,
                            R.string.cancel_permission_message,
                            Toast.LENGTH_SHORT
                    )
                    .show();
        }
        }

    private void openCameraActivity() {

        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {

        super.onResume();
        checkNewImages();
    }

    private void checkNewImages() {
        if(TemporalUriSaver.getInstance().hasChange){
            TemporalUriSaver.getInstance().hasChange=false;
            int dimension = getImageButtonDimensions();
            TemporalUriSaver.getInstance().lastImageViewChanged.setImageURI(TemporalUriSaver.getInstance().temporalUri);
           ViewGroup.LayoutParams params = TemporalUriSaver.getInstance().lastImageViewChanged.getLayoutParams();
            params.width= dimension;
            params.height=dimension;

            checkVisibilityOfNextElement();
            numberOfImages++;
        }
    }

    private void checkVisibilityOfNextElement() {
        if(iv2.getVisibility()!=View.VISIBLE ){
            ib2.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.VISIBLE);
            buttonSetOnClickListener(ib2,iv2);
        }
       else if(iv3.getVisibility()!=View.VISIBLE &&getHeightParams(iv2)!=firstDimensions){
            ib3.setVisibility(View.VISIBLE);
            iv3.setVisibility(View.VISIBLE);
            buttonSetOnClickListener(ib3,iv3);

        }
       else if(c.getVisibility()!=View.VISIBLE &&getHeightParams(iv3)!=firstDimensions){
           c.setVisibility(View.VISIBLE);
           iv4.setVisibility(View.VISIBLE);
           ib4.setVisibility(View.VISIBLE);
           ib5.setVisibility(View.GONE);
           iv5.setVisibility(View.GONE);
           ib6.setVisibility(View.GONE);
           iv6.setVisibility(View.GONE);
            buttonSetOnClickListener(ib4,iv4);
        }
       else if(iv5.getVisibility()!=View.VISIBLE &&getHeightParams(iv4)!=firstDimensions){
            ib5.setVisibility(View.VISIBLE);
            iv5.setVisibility(View.VISIBLE);
            buttonSetOnClickListener(ib5,iv5);
        }
       else if(iv6.getVisibility()!=View.VISIBLE &&getHeightParams(iv5)!=firstDimensions){
            ib6.setVisibility(View.VISIBLE);
            iv6.setVisibility(View.VISIBLE);
            buttonSetOnClickListener(ib6,iv6);
        }
    }

    private int getImageButtonDimensions() {
        ViewGroup.LayoutParams params = TemporalUriSaver.getInstance().lastButtonChanged.getLayoutParams();
        return params.height;
    }
    private int getTvDimensions() {
        ViewGroup.LayoutParams params = iv1.getLayoutParams();
        return params.height;
    }
    private void addToDatabase(){
        hasChange = true;
        auctionToAdd = new Auction();
        String idAuction = UUID.randomUUID().toString();
        idAuction = HashGenerator.getSHA256(idAuction);
        auctionToAdd.setId(idAuction);
        auctionToAdd.setImagesUrls(TemporalStringSaver.getInstance().list);

        TextInputEditText title = findViewById(R.id.tietTitle);
        TextInputEditText description = findViewById(R.id.tietDescription);
        TextInputEditText price = findViewById(R.id.tietPrice);
        EditText maxDays = findViewById(R.id.tilDaysSpinner);
        EditText maxMinutes = findViewById(R.id.tilMinutesSpinner);
        EditText maxHours= findViewById(R.id.tilHoursSpinner);
        RadioButton rNow = findViewById(R.id.rbNow);
        auctionToAdd.setTitle(title.getText().toString());
        auctionToAdd.setDescription(description.getText().toString());
        auctionToAdd.setInitialPrice(Double.parseDouble(price.getText().toString()));
        auctionToAdd.setCurrentPrice(Double.parseDouble(price.getText().toString()));
        getImagesAndSave(auctionToAdd);

        if(rNow.isChecked()){
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            date.setYear(date.getYear()+1900);
            auctionToAdd.setStartTime(date);
        }
        else {
            auctionToAdd.setStartTime(DateConvertionUtil.getDate(
                    TemporalTimeSaver.getInstance().year,
                    TemporalTimeSaver.getInstance().month,
                    TemporalTimeSaver.getInstance().day,
                    TemporalTimeSaver.getInstance().hours,
                    TemporalTimeSaver.getInstance().minutes,
                    0
            ));
        }


        auctionToAdd.setEndTime(DateConvertionUtil.addTime(
                Integer.parseInt(maxDays.getText().toString()),
                Integer.parseInt(maxHours.getText().toString()),
                Integer.parseInt(maxMinutes.getText().toString())
        ));
        auctionToAdd.setUserId(getUserId());

        if(counter!=0)
            finish();
        else{
            goContinue();
            counter++;

        }


    }
    private void goContinue() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.all_done);
        dialog.setMessage(R.string.auction_successfully);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addToDatabase();
            }
        });

        dialog.show();

    }

    private String getUserId() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        return preferences.getString("userID","");
    }

    @Override
    protected void onStop() {
        if(hasChange) {

            auctionToAdd.setImagesUrls(TemporalStringSaver.getInstance().list);

            AuctionDataBase.createAction(auctionToAdd);
        }
        super.onStop();

    }

    private void getImagesAndSave(Auction auctionToAdd) {
        List<Bitmap>listOfBitmap = new ArrayList<>();
        if(numberOfImages>=1)
            listOfBitmap.add(getBipMapOfAImageView(iv1));
        if(numberOfImages>=2)
            listOfBitmap.add(getBipMapOfAImageView(iv2));
        if(numberOfImages>=3)
            listOfBitmap.add(getBipMapOfAImageView(iv3));
        if(numberOfImages>=4)
            listOfBitmap.add(getBipMapOfAImageView(iv4));
        if(numberOfImages>=5)
            listOfBitmap.add(getBipMapOfAImageView(iv5));
        if(numberOfImages>=6)
            listOfBitmap.add(getBipMapOfAImageView(iv6));
        saveImages(listOfBitmap,auctionToAdd);
    }

    private Bitmap getBipMapOfAImageView(ImageView iv) {
        Bitmap bm=((BitmapDrawable)iv.getDrawable()).getBitmap();
        return bm;
    }
    private void saveImages(List<Bitmap> listOfBitmap, Auction auctionToAdd){
        SharedPreferences preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String nameOfImage = preferences.getString("email","");
        nameOfImage= HashGenerator.getSHA256(nameOfImage);


        nameOfImage+=HashGenerator.getSHA256(auctionToAdd.getDescription().trim()
                +auctionToAdd.getTitle().trim());
        for(int i =0;i<listOfBitmap.size();i++){
            Uri uri = getUriFromBitmap(listOfBitmap.get(i));
            nameOfImage+=i;

            AuctionDataBase.addImage(uri,i,nameOfImage);


           AuctionDataBase.getImage(uri, nameOfImage);


        }


        }







    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String uuid= UUID.randomUUID().toString();
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, uuid, null);
        return Uri.parse(path);
    }





}