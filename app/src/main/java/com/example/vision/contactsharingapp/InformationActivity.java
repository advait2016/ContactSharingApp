package com.example.vision.contactsharingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

public class InformationActivity extends AppCompatActivity {
    private Button generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Intent intent2 = getIntent();
        generate = (Button) findViewById(R.id.generate);
        generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText Name, PH1,Email,jobTitle;
                String name, ph1,title,email ;
                Name = (EditText) findViewById(R.id.name);
                PH1 = (EditText) findViewById(R.id.PH1);
                Email =(EditText) findViewById(R.id.email);
                jobTitle=(EditText) findViewById(R.id.title);
                email= Email.getText().toString();
                title=jobTitle.getText().toString();
                name = Name.getText().toString();
                ph1 = PH1.getText().toString();

                if(name.matches("")||title.matches("")||email.matches("")||ph1.matches("")){
                    Toast.makeText(InformationActivity.this,"Some Fields are Missing ", Toast.LENGTH_LONG).show();
                }
                else {
                    VCard userInfomation = new VCard(name);
                    userInfomation.setPhoneNumber(ph1);
                    userInfomation.setEmail(email);
                    userInfomation.setTitle(title);
                    QRCode.from(userInfomation);
                    Bitmap userQR = QRCode.from(userInfomation).bitmap();
                    Intent intent3 = new Intent(InformationActivity.this, CodeShowActivity.class);
                    intent3.putExtra("QRcode", userQR);
                    intent3.putExtra("UserName", name);
                    intent3.putExtra("check",5);

                    startActivity(intent3);
                }
                /*

                Toast.makeText(InformationActivity.this, name, Toast.LENGTH_LONG).show();
                Toast.makeText(InformationActivity.this, ph1, Toast.LENGTH_LONG).show();
                Toast.makeText(InformationActivity.this, ph2, Toast.LENGTH_LONG).show();
                Toast.makeText(InformationActivity.this, email, Toast.LENGTH_LONG).show();
                Toast.makeText(InformationActivity.this, website, Toast.LENGTH_LONG).show();
                 */
            }

        });
    }

}

