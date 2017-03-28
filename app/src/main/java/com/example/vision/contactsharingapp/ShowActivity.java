package com.example.vision.contactsharingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ShowActivity extends AppCompatActivity {

    ImageView showimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent i1= getIntent();
        loadImageFromStorage(i1.getStringExtra("location"));

    }



    private void loadImageFromStorage(String path)
    {

        try {

            File f=new File( this.getFilesDir().getPath(), "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img=(ImageView)findViewById(R.id.showimg);
            img.setImageBitmap(b);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Toast.makeText(this,"Please Generate a QRcode",Toast.LENGTH_LONG).show();
        }

    }
}
