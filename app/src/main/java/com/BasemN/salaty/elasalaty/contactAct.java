package com.BasemN.salaty.elasalaty;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class contactAct extends AppCompatActivity {

    ImageView FaceIV,InIV,GoogleIV,TwitterIV;
    TextView appNameTV;

    Toolbar toolbar;
    ImageView backIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_appp);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_id);
        setSupportActionBar(toolbar);


        backIV = (ImageView) findViewById(R.id.backIco);

        FaceIV = (ImageView) findViewById(R.id.faceIV);
        InIV = (ImageView) findViewById(R.id.InIV);
        GoogleIV = (ImageView) findViewById(R.id.googleIV);
        TwitterIV = (ImageView) findViewById(R.id.twitIV);
        appNameTV = (TextView) findViewById(R.id.textsss);

        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/AGA-Granada-V2.ttf");
        appNameTV.setTypeface(tf2);


        FaceIV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Basem.Nasr.Ahmed"));
                startActivity(browserIntent);

            }
        });

        InIV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/basem-nasr-0238a0109?trk=hp-identity-name"));
                startActivity(browserIntent);

            }
        });


        GoogleIV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/110648686786465824012"));
                startActivity(browserIntent);

            }
        });


        TwitterIV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/basemonly1"));
                startActivity(browserIntent);

            }
        });


        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

}
