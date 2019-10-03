package com.example.zahoor.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.zahoor.model.PackageModel;
import com.example.zahoor.sql.PackageDatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class RegisterPackageActivity extends AppCompatActivity {
    private EditText name,detail,price;
    private TextView packImage;
    private ImageView viewImage;
    private Button addPackage,viewPackage,selectImgBtn;
    private PackageDatabaseHelper packDb;
    private PackageModel pack;

    //vars for image selection from gallery and camera
    private static final String TAG = "MainActivity";
    private static final int UNIQUE_REQUEST_CODE = 999;
    private static final String IMAGE_DIRECTORY = "/tourism";
    private int GALLERY = 1, CAMERA =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_package);
        //making objects
        packDb = new PackageDatabaseHelper(this);
        pack = new PackageModel();
        initViews();
    }
    private void initViews(){
        name = (EditText) findViewById(R.id.packName);
        detail = (EditText) findViewById(R.id.packDetail);
        packImage = (TextView) findViewById(R.id.packImage);
        price = (EditText) findViewById(R.id.packPrice);
        addPackage = (Button) findViewById(R.id.addPackage);
        viewPackage = (Button) findViewById(R.id.viewPackage);
        selectImgBtn = (Button) findViewById(R.id.selectImgBtn);
        viewImage = (ImageView) findViewById(R.id.packagImageView);
        addPackage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //saving data
                postDataToSQLite();
            }
        });

        //Adding lister for image
        selectImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
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
                    //get file
                    File photo = new File(path);
                    //file name
                    String fileName = photo.getName();
                    //resave file with new name
                    Date currentTime = Calendar.getInstance().getTime();
                    File newFile = new File("package"+currentTime);
                    photo.renameTo(newFile);
                    Toast.makeText(RegisterPackageActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    //Show image in the selectedImage view and Image path in the text view
                    //view image
                    viewImage.setImageBitmap(bitmap);
                    //setting text for image name
                    packImage.setText(BitMapToString(bitmap));


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterPackageActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            viewImage.setImageBitmap(thumbnail);
            packImage.setText(BitMapToString(thumbnail));
            saveImage(thumbnail);
            Toast.makeText(RegisterPackageActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
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
        String message="";
        if(!name.getText().toString().isEmpty()){
            // new getting values from input fields and saving to db
            pack.setName(name.getText().toString().trim());
            pack.setDesc(detail.getText().toString().trim());
            pack.setImage(packImage.getText().toString());
            pack.setPrice(Integer.parseInt(price.getText().toString().trim()));
            message = packDb.addPackage(pack);
        }else{
            message="Fill the form!";
        }
        //Notify user that data has been saved successfully
        Toast.makeText(getApplicationContext(),message.toString(),Toast.LENGTH_SHORT).show();
    }

}
