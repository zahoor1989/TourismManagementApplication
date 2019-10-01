package com.example.zahoor.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.example.zahoor.model.TourPlace;
import com.example.zahoor.sql.TouristPlaceDatabase;

import java.util.ArrayList;

public class SingleTourPlaceActivity extends AppCompatActivity {
    private TextView placeName;
    private ImageView placeImage;
    private TextView placeAdd;
    private VideoView placeVideo;
    private TextView placeDescription;
    private TextView placePrice;
    private String placeId;
    private TouristPlaceDatabase tpDb;
    private TourPlace tourPlace;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tour_place);
        initViews();
        //display place method
        displayPlace();
    }
    private void initViews(){
        placeName = (TextView) findViewById(R.id.singlePlaceName);
        placeImage = (ImageView) findViewById(R.id.singlePlaceImage);
        placeAdd = (TextView) findViewById(R.id.singlePlaceAdd);
        placeVideo = (VideoView) findViewById(R.id.singlePlaceVideo);
        placeDescription = (TextView) findViewById(R.id.singlePlaceDesc);
        placePrice = (TextView) findViewById(R.id.singlePlacePrice);

    }
    private void displayPlace(){
        extras = getIntent().getExtras();
        placeId = extras.getString("PLACE_ID");
        //get the place object of the given id
        tpDb = new TouristPlaceDatabase(this);
        tourPlace = new TourPlace();
        tourPlace = tpDb.getTourPlace(Integer.parseInt(placeId));
        //now putting the data in the views
        placeName.setText(tourPlace.getName());
        //Placing the image
        String imageName = tourPlace.getImage();
        //int id = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
        if(!imageName.isEmpty()){
            //Adding image to image view after converting form string to bitmap
            placeImage.setImageBitmap(StringToBitMap(imageName));
        }else{
            placeImage.setImageResource(R.drawable.image);
        }
        //add the address
        placeAdd.setText(tourPlace.getAdd());
        //Set MediaController  to enable play, pause, forward, etc options.
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(placeVideo);
        //Location of Media File
        String videoName =tourPlace.getVideo();
            Uri uri = Uri.parse(videoName);
            System.out.println(uri);
            //Starting VideoView By Setting MediaController and URI
            placeVideo.setMediaController(mediaController);
            placeVideo.setVideoURI(uri);
            placeVideo.requestFocus();
            placeVideo.start();

            placeDescription.setText(tourPlace.getDesc());
            placePrice.setText(Integer.toString(tourPlace.getPrice()));
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
