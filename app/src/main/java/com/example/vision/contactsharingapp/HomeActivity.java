package com.example.vision.contactsharingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.VCardResultParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeActivity extends AppCompatActivity {
    private Button scan_btn ,Myinfo,show_btn;
    Intent intent;
    // String path=null,status="unknown";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Activity activity = this;

        scan_btn = (Button) findViewById(R.id.Scan);
        scan_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });

        /*show_btn = (Button) findViewById(R.id.ShowQR);
        show_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                intent = getIntent();
                status = intent.getStringExtra("savestatus");
                path = intent.getStringExtra("img_path");
                if(status.matches("ok")){
                    Intent intent3 = new Intent(HomeActivity.this,CodeShowActivity.class);
                    intent3.putExtra("check",10);
                    intent3.putExtra("img_path",path);
                    startActivity(intent3);
                }
                else{
                    Toast.makeText(HomeActivity.this, "No Image path Available", Toast.LENGTH_LONG).show();
                    //Intent intent3 = new Intent(HomeActivity.this,CodeShowActivity.class);
                   // startActivity(intent3);
                }


            }

        });*/

        /*Myinfo = (Button) findViewById(R.id.Myinfo);
        Myinfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent2 = new Intent(HomeActivity.this, InformationActivity.class);
                startActivity(intent2);

            }


        });*/

    }
    public void onBackPressed() {

        moveTaskToBack(true);

    }






    public void onActivityResult(int requestCode,int resultCode,Intent intent) {

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanResult != null) {
            if (scanResult.getContents() == null) {

                Toast.makeText(this, "Scan Failed", Toast.LENGTH_LONG).show();
            }
            else {
                byte rawbyte[] = scanResult.getRawBytes();
                String contents = scanResult.getContents();
                if (contents.startsWith("BEGIN")) {
                    try {
                        BarcodeFormat fomat = BarcodeFormat.valueOf(scanResult.getFormatName());
                        Result result = new Result(contents, rawbyte, null, fomat);
                        VCardResultParser info = new VCardResultParser();
                        AddressBookParsedResult parseinfo = info.parse(result);
                        String[] Name = parseinfo.getNames();
                        String[] phoneNumber = parseinfo.getPhoneNumbers();
                        String[] Email=parseinfo.getEmails();
                        String titl = parseinfo.getTitle();
                        Intent intent2 = new Intent(ContactsContract.Intents.Insert.ACTION);
                        intent2.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                        intent2.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber[0]);
                        intent2.putExtra(ContactsContract.Intents.Insert.NAME, Name[0]);
                        intent2.putExtra(ContactsContract.Intents.Insert.EMAIL,Email[0]);
                        intent2.putExtra(ContactsContract.Intents.Insert.JOB_TITLE,titl);
                        startActivityForResult(intent2, 100);
                        Toast.makeText(this, phoneNumber[0], Toast.LENGTH_LONG).show();
                        Toast.makeText(this, Name[0], Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e){
                        Toast.makeText(this,"SomeFields Might be Missing",Toast.LENGTH_LONG);

                    }
                } else {

                    Toast.makeText(this, scanResult.getContents(), Toast.LENGTH_LONG).show();
                }

            }


        }
        else{
            super.onActivityResult(requestCode, resultCode, intent);
        }


    }


    public void clickinfo(View view) {
        Intent intent2 = new Intent(HomeActivity.this, InformationActivity.class);
        startActivity(intent2);
    }

    public void clickshow(View view) {
        intent = getIntent();
        Intent intent4 = new Intent (HomeActivity.this,ShowActivity.class);
        intent4.putExtra("location",intent.getStringExtra("location"));
        startActivity(intent4);

    }
}

