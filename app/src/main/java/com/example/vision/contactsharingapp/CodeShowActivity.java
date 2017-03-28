package com.example.vision.contactsharingapp;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CodeShowActivity extends AppCompatActivity {

    Bitmap userQR1,UserQR;
    String UserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_show);
        Intent intent3=getIntent();

        ImageView QRCode =(ImageView) findViewById(R.id.QRShow);
        userQR1 = intent3.getParcelableExtra("QRcode");
        UserName =intent3.getStringExtra("UserName");
        UserQR = getScaledBitMapBaseOnScreenSize(userQR1);
        if(UserQR!=null)
            QRCode.setImageBitmap(Bitmap.createBitmap(UserQR));
        TextView Display = (TextView) findViewById(R.id.showname);
        Display.setText(UserName);




    }


    private Bitmap getScaledBitMapBaseOnScreenSize(Bitmap bitmapOriginal){

        Bitmap scaledBitmap=null;
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width = bitmapOriginal.getWidth();
            int height = bitmapOriginal.getHeight();
            float scaleWidth = metrics.scaledDensity;
            float scaleHeight = metrics.scaledDensity;
            // create a matrix for the manipulation
            Matrix matrix = new Matrix();
            // resize the bit map
            matrix.postScale(scaleWidth, scaleHeight);

            // recreate the new Bitmap
            scaledBitmap = Bitmap.createBitmap(bitmapOriginal, 0, 0, width, height, matrix, true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return scaledBitmap;
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/ConnectMe/app_data/imageDir
        File directory =  this.getFilesDir();
        //cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this,"unable to save",Toast.LENGTH_LONG).show();
            }
        }
        return directory.getAbsolutePath();
    }







    public void saveBitmap(View view) {


        String path = saveToInternalStorage(UserQR);
       // Toast.makeText(this,path,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CodeShowActivity.this,HomeActivity.class);
        intent.putExtra("loaction",path);
        startActivity(intent);

    }
}
