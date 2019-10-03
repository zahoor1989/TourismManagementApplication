package com.example.zahoor.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.zahoor.model.Hotel;
import com.example.zahoor.sql.HotelDatabaseHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class RegisterHotelActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener  {
    private EditText hotelName,hotelAdd,hotelPhone,hotelDesc,hotelService,hotelPrice;
    private TextView selectedImage,latitude,longitude;
    private ImageView viewHotelImage;
    private Button hotelImage,addHotel,viewHotel,getLatLng;
    private HotelDatabaseHelper hotelDb;
    private Hotel hotel;
    private ScrollView hotelRegisterTemplate;
    //vars for map
    //googlemap
    private MarkerOptions markerOptions;
    private GoogleMap map;
    private LatLng latLng;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private MapFragment mapFragment;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 20000; /* 20 sec */

    //vars for image selection from gallery and camera
    private static final String TAG = "MainActivity";
    private static final int UNIQUE_REQUEST_CODE = 999;
    private static final String IMAGE_DIRECTORY = "/tourism";
    private int GALLERY = 1, CAMERA =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hotel);
        //creating object
        hotelDb = new HotelDatabaseHelper(this);
        hotel = new Hotel();
        //veiw for map
               mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
               mapFragment.getMapAsync(this);
        // views
        initViews();
    }
    private void initViews() {
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
         hotelName = (EditText) findViewById(R.id.hotelName);
         hotelAdd =(EditText) findViewById(R.id.hotelAddress);
         hotelPhone =(EditText) findViewById(R.id.hotelPhone);
         hotelDesc =(EditText) findViewById(R.id.hotelDesc);
         hotelService =(EditText) findViewById(R.id.hotelService);
         hotelPrice =(EditText) findViewById(R.id.hotelPrice);
        selectedImage = (TextView) findViewById(R.id.selectedImage);
         viewHotelImage = (ImageView) findViewById(R.id.viewHotelImage);
         hotelImage =(Button) findViewById(R.id.hotelImage);
         addHotel =(Button) findViewById(R.id.addHotelBtn);
         viewHotel =(Button) findViewById(R.id.viewHotelBtn);
         getLatLng =(Button) findViewById(R.id.getLatLng);
        hotelRegisterTemplate = (ScrollView) findViewById(R.id.hotelRegisterTemplate);
         //add hotel listener
        addHotel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //save data
                postDataToSQLite();
            }
        });
        viewHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling the add manage hotel activity
                Intent intent = new Intent(v.getContext(),ManageHotelActivity.class);
                startActivity(intent);
            }
        });
        //button listener for hotelImage
        //Adding lister for image
        hotelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
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
                Toast.makeText(RegisterHotelActivity.this,latLng, Toast.LENGTH_SHORT).show();
            }
        });
    }
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
                    Toast.makeText(RegisterHotelActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    //Show image in the selectedImage view and Image path in the text view
                    //view image
                    viewHotelImage.setImageBitmap(bitmap);
                    //setting text for image name
                    selectedImage.setText(BitMapToString(bitmap));


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterHotelActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            viewHotelImage.setImageBitmap(thumbnail);
            selectedImage.setText(BitMapToString(thumbnail));
            saveImage(thumbnail);
            Toast.makeText(RegisterHotelActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
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

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void  postDataToSQLite(){
        //initilize the message var
        String message = "";
        if(!hotelName.getText().toString().isEmpty()){
            // new getting values from input fields and saving to db
            hotel.setName(hotelName.getText().toString().trim());
            hotel.setAddress(hotelAdd.getText().toString());
            hotel.setPhone(hotelPhone.getText().toString().trim());
            hotel.setDesc(hotelDesc.getText().toString().trim());
            hotel.setService(hotelService.getText().toString().trim());
            hotel.setPricePerNight(hotelPrice.getText().toString().trim());
            hotel.setImages(selectedImage.getText().toString().trim());
            hotel.setLati(latitude.getText().toString().trim());
            hotel.setLongi(longitude.getText().toString().trim());
            message = hotelDb.addHotel(hotel);
        }else{
            message="Fill the form!";
        }
        //Notify user that data has been saved successfully
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    //working on map and its mathods
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
                hotelRegisterTemplate.requestDisallowInterceptTouchEvent(true);
            }
        });

    }
}
