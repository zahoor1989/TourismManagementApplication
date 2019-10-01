package com.example.zahoor.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.zahoor.model.TourPlace;
import com.example.zahoor.sql.TouristPlaceDatabase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TouristPlace extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener  {
    private EditText placeName, placeDesc, placePrice,placeAdd;
    private TextView placeVideo;
    private ScrollView tourTemplate;
    private Button addPlaceBtn,viewBtn,getLatLng,selectImage,selectVideoBtn;
    private String message;
    private VideoView videoView;
    private ImageView selectedImage;
    //private InputValidation inputValidation;
    private TouristPlaceDatabase tourplacedb;
    private ArrayList<TourPlace> tpList;
    private TourPlace tourplace;
    private MarkerOptions markerOptions;
    //googlemap
    private GoogleMap map;
    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private MapFragment mapFragment;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 20000; /* 20 sec */

    private TextView latitude, longitude,placeImage;
    private LocationManager locationManager;
    private LatLng latLng;
    private static final int UNIQUE_REQUEST_CODE = 999;
    private static final String IMAGE_DIRECTORY = "/tourism";
    private int GALLERY = 1, CAMERA =2, GALLERY_VIDEO=3, CAMERA_VIDEO=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_place);
        //creating the objects
        //inputValidation = new InputValidation(activity);
        tourplacedb = new TouristPlaceDatabase(this);
        tourplace = new TourPlace();
        //it was pre written
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //creating views and adding lister
        initViews();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initViews() {
        // getting variables
        placeName = (EditText) findViewById(R.id.placeName);
        placeDesc = (EditText) findViewById(R.id.placeDesc);
        placeAdd = (EditText) findViewById(R.id.placeAdd);
        placePrice = (EditText) findViewById(R.id.placePrice);
        placeImage = (TextView) findViewById(R.id.placeImage);
        placeVideo = (TextView) findViewById(R.id.placeVideo);
        addPlaceBtn = (Button) findViewById(R.id.addPlaceBtn);
        viewBtn = (Button) findViewById(R.id.ViewPlacesBtn);
        getLatLng = (Button) findViewById(R.id.getLatLng);
        //TextViews for latitude and longitude
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        selectedImage = (ImageView) findViewById(R.id.viewSelectedImage);
        selectImage = (Button) findViewById(R.id.selectImage);
        selectVideoBtn = (Button) findViewById(R.id.selectVideoBtn);
        videoView = (VideoView) findViewById(R.id.videoView);
        tourTemplate = (ScrollView) findViewById(R.id.tourTemplate);

    }
    private void initListener(){
        addPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onclick save all the values of the input fields
                postDataToSQLite();
            }   // ends onClick
        });
        //view all tourist place
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the add tour place activity
                Intent intent = new Intent(v.getContext(),ViewPlacesActivity.class);
                startActivity(intent);
            }   // ends onClick
        });

        //adding listener for getting lat and long
        getLatLng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = 0;
                double lng = 0;
                if(markerOptions != null){
                    lat = markerOptions.getPosition().latitude;
                    lng = markerOptions.getPosition().longitude;

                }
                String latLng = "Lat:"+lat+"lng:"+lng ;
                System.out.println("Land Long Recieved:"+ latLng);
                latitude.setText(new Double(lat).toString());
                longitude.setText(new Double(lng).toString());
                Toast.makeText(TouristPlace.this,latLng, Toast.LENGTH_SHORT).show();
            }
        });
        //Adding lister for image
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });
        selectVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVidoeDialog();
            }
        });

    }
    /**
     * This method is to initialize objects to be used
     */

    private void  postDataToSQLite(){
    // new getting values from input fields and saving to db
        tourplace.setName(placeName.getText().toString().trim());
        tourplace.setPrice(Integer.parseInt(placePrice.getText().toString()));
        tourplace.setAdd(placeAdd.getText().toString().trim());
        tourplace.setDesc(placeDesc.getText().toString().trim());
        tourplace.setImage(placeImage.getText().toString().trim());
        tourplace.setVideo(placeVideo.getText().toString().trim());
        tourplace.setLati(latitude.getText().toString().trim());
        tourplace.setLongi(longitude.getText().toString().trim());
        System.out.println("Name:"+placeName.getText().toString()+"\nPrice:"+placePrice.getText().toString()+"\nAddress:"+placeAdd.getText().toString()+"\nDescription:"+placeDesc.getText().toString()+"\nImage:"+placeImage.getText().toString()+"\nVideo"+placeVideo.getText().toString());
        message = tourplacedb.addTour(tourplace);

        //Notify user that data has been saved successfully
        Toast.makeText(getApplicationContext(),message.toString(),Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        latitude.setText(String.valueOf(location.getLatitude()));
        longitude.setText(String.valueOf(location.getLongitude()));


        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //it was pre written
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(new LatLng(31.54972,74.34361)).title("Current Location"));
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(31.54972,74.34361)));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();
                markerOptions = new MarkerOptions();
                markerOptions.position(latLng).title("Selected Location");
                map.addMarker(markerOptions);
                tourTemplate.requestDisallowInterceptTouchEvent(true);
            }
        });

    }
    //Now working on image
    //creating image show dialog
    private void showImageDialog(){
        AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        imageDialog.setTitle("Select Option");
        String[] picturesDialogItems= {
                "Image From Gallery","Image From Camera"
        };
        imageDialog.setItems(picturesDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        choosePhoneFromGallery();
                        break;
                    case 1:
                        takePhoneFromCamera();
                        break;
                }
            }
        });
        imageDialog.show();
    }

    //method if gallery choosen is selected
    public void choosePhoneFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }
    //method to take photo form camera
    public void takePhoneFromCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,CAMERA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == this.RESULT_CANCELED){
            Toast.makeText(this,"You Cancelled",Toast.LENGTH_LONG).show();
            return;
        }
        Log.d(TAG, "onActivityResult: "+resultCode);
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(TouristPlace.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    //Show image in the selectedImage view and Image path in the text view
                    selectedImage.setImageBitmap(bitmap);
                    placeImage.setText(BitMapToString(bitmap));

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(TouristPlace.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            selectedImage.setImageBitmap(thumbnail);
            placeImage.setText(BitMapToString(thumbnail));
            saveImage(thumbnail);
            Toast.makeText(TouristPlace.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        //if selected video
        if (requestCode == GALLERY_VIDEO) {
            Log.d("what","gale");
            if (data != null) {
                Uri contentURI = data.getData();
                String selectedVideoPath = getPath(contentURI);
                Log.d("path",selectedVideoPath);
                saveVideoToInternalStorage(selectedVideoPath);
                videoView.setVideoURI(contentURI);
                placeVideo.setText(contentURI.toString());
                videoView.requestFocus();
                final MediaController mediacontroller = new MediaController(this);
                mediacontroller.setAnchorView(videoView);
                videoView.setMediaController(mediacontroller);
                videoView.start();

            }

        } else if (requestCode == CAMERA_VIDEO) {
            Uri contentURI = data.getData();
            String recordedVideoPath = getPath(contentURI);
            Log.d("frrr",recordedVideoPath);
            saveVideoToInternalStorage(recordedVideoPath);
            videoView.setVideoURI(contentURI);
            //putting the vidoe address in TextView
            placeVideo.setText(contentURI.toString());
            videoView.requestFocus();
            videoView.start();
        }

    }
    //method to save image to specified location
    public String saveImage(Bitmap myBitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG,90, bytes);
        File WallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if(!WallpaperDirectory.exists()){
            WallpaperDirectory.mkdir();
        }
        //Now saving the file
        try{
            File f = new File(WallpaperDirectory, Calendar.getInstance().getTimeInMillis()+".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,new String[]{f.getPath()},new String[]{"image/jpeg"},null);
            fo.close();
            Log.d("TAg", "File saved----->"+f.getAbsolutePath());
            return f.getAbsolutePath();

        }catch(IOException e){
            e.printStackTrace();

        }
        return "";

    }

    public AlertDialog showDialog(String title, String message, String positiveLabel, DialogInterface.OnClickListener positiveOnClick, String negativeLabel, DialogInterface.OnClickListener negativeOnclick, boolean isCancelable){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setCancelable(isCancelable);
        dialog.setMessage(message);
        dialog.setPositiveButton(positiveLabel, positiveOnClick);
        dialog.setNegativeButton(negativeLabel, negativeOnclick);
        AlertDialog alert = dialog.create();
        alert.show();
        return alert;
    }
    //Working on video
    private void showVidoeDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();
                                break;
                        }
                    }
                });

        pictureDialog.show();
    }
    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY_VIDEO);
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA_VIDEO);
    }
    private void saveVideoToInternalStorage (String filePath) {

        File newfile;

        try {

            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
            newfile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4");

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            if(currentFile.exists()){

                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newfile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.v("vii", "Video file saved successfully.");
            }else{
                Log.v("vii", "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
