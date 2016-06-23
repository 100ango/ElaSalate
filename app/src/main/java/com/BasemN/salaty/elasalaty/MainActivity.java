package com.BasemN.salaty.elasalaty;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView aboutIV;
    TextView AppName, Title1, Title2;
    Button StartSched, ChangeTimes, playTazkeer;
    ProgressDialog mDialog;
    Spinner spinner1;
    int id,elsamettech,tazkeraPosition,everyTazkera;
    Boolean ConectionChecker;
    RadioGroup radioGroup;
    ImageView TestSound,ContactIcon;

    Runnable run;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartSched = (Button) findViewById(R.id.startSched);
        ChangeTimes = (Button) findViewById(R.id.changeTimes);
        playTazkeer = (Button) findViewById(R.id.playTazkeer);

        toolbar = (Toolbar) findViewById(R.id.app_bar_id);
        setSupportActionBar(toolbar);

        aboutIV = (ImageView) findViewById(R.id.aboutIcon);
        AppName = (TextView) findViewById(R.id.appName);
        Title1 = (TextView) findViewById(R.id.texthint1);
        Title2 = (TextView) findViewById(R.id.texthint2);
        AppName.setText("إلا صلاتي");

        String fontPath = "fonts/AGA-Granada-V2.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        AppName.setTypeface(tf);

        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/DroidSansArabic.ttf");

        Title1.setTypeface(tf2);
        Title2.setTypeface(tf2);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        radioGroup = (RadioGroup) findViewById(R.id.radioSex);


        SharedPreferences sharedPreferences2 = getSharedPreferences("myfile", MODE_PRIVATE);
        id = sharedPreferences2.getInt("playTazkera", 0);
        elsamettech = sharedPreferences2.getInt("elsametTech",0);
        tazkeraPosition = sharedPreferences2.getInt("tazkPosss",0);
        everyTazkera = sharedPreferences2.getInt("everyTazk",radioGroup.getCheckedRadioButtonId());
        radioGroup.check(everyTazkera);
        spinner1.setSelection(tazkeraPosition);





        TestSound = (ImageView) findViewById(R.id.testsund);

        ConectionChecker = isNetworkAvailable();

        if (id == 0) {
            playTazkeer.setText("تشغيل");

        }
        if (id == 1) {
            playTazkeer.setText("إيقاف");

        }

        if (elsamettech==0){
            StartSched.setText("تفعيل");

        }
        if (elsamettech==1){
            StartSched.setText("إيقاف");
        }



        if (ConectionChecker == true){

            updateTimesMethod();

        }


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //ya3ne mesh mshaghalhaa lesa
                tazkeraPosition = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TestSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = null;
                if (tazkeraPosition == 0) {

                    if (mp != null) {
                        mp.stop();
                    }
                    mp = MediaPlayer.create(MainActivity.this, R.raw.allahomsaly);
                    mp.start();

                } else if (tazkeraPosition == 1) {

                    if (mp != null) {
                        mp.stop();
                    }
                    mp = MediaPlayer.create(MainActivity.this, R.raw.ezkor_ellah);
                    mp.start();

                } else if (tazkeraPosition == 2) {
                    if (mp != null) {
                        mp.stop();
                    }
                    mp = MediaPlayer.create(MainActivity.this, R.raw.est);
                    mp.start();

                } else if (tazkeraPosition == 3) {

                    if (mp != null) {
                        mp.stop();
                    }
                    mp = MediaPlayer.create(MainActivity.this, R.raw.sob7an);
                    mp.start();

                }


            }
        });


        aboutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, contactAct.class);
                startActivity(intent);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                if (id == 0) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    SharedPreferences sharedPreferences = getSharedPreferences("myfile", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("everyTazk", selectedId);
                    editor.commit();
                }


            }
        });

        StartSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (elsamettech == 0){

                    if (ConectionChecker == true){

                        updateTimesMethod();
                    }

                    SharedPreferences timesShared = getSharedPreferences("times", MODE_PRIVATE);
                    int fajr1 = timesShared.getInt("fajr1", 3);
                    int fajr2 = timesShared.getInt("fajr2",14);
                    int duhr1 = timesShared.getInt("duhr1",11);
                    int duhr2 = timesShared.getInt("duhr2",57);
                    int asr1 = timesShared.getInt("asr1",15);
                    int asr2 = timesShared.getInt("asr2",35);
                    int maghrib1 = timesShared.getInt("maghrib1",19);
                    int maghrib2 = timesShared.getInt("maghrib2",2);
                    int isha1 = timesShared.getInt("isha1",20);
                    int isha2 = timesShared.getInt("isha2",37);
                    Log.d("keeeeeess",isha2+"");


                    Log.d("MaghribTime",maghrib1+" : "+maghrib2);







                    ///////////////////////// FAJR //////////////////////////
                    Calendar midnightCalendar = Calendar.getInstance();
                    //set the time to midnight tonight
                    midnightCalendar.setTimeInMillis(System.currentTimeMillis());
                    midnightCalendar.set(Calendar.HOUR_OF_DAY,fajr1);
                    midnightCalendar.set(Calendar.MINUTE,fajr2);
                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //create a pending intent to be called at midnight
                    Intent myIntent = new Intent(MainActivity.this, SilenceBroadcastReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
                    //schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                    am.setRepeating(AlarmManager.RTC_WAKEUP, midnightCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


                    Calendar calendar2 = Calendar.getInstance();
                    //set the time to 6AM
                    calendar2.setTimeInMillis(System.currentTimeMillis());
                    calendar2.set(Calendar.HOUR_OF_DAY, fajr1);
                    calendar2.set(Calendar.MINUTE, fajr2+20);
                    //create a pending intent to be called at 6 AM
                    Intent myIntent2 = new Intent(MainActivity.this, UnsilenceBroadcastReceiver.class);
                    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent2, 0);
                    //schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);



                    ///////////////////////// Duhr //////////////////////////
                    Calendar clender3 = Calendar.getInstance();
                    //set the time to midnight tonight
                    clender3.setTimeInMillis(System.currentTimeMillis());
                    clender3.set(Calendar.HOUR_OF_DAY,duhr1);
                    clender3.set(Calendar.MINUTE,duhr2);
                    AlarmManager am2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //create a pending intent to be called at midnight
                    Intent myIntent3 = new Intent(MainActivity.this, SilenceBroadcastReceiver2.class);
                    PendingIntent pendingIntent3 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent3, 0);
                    //schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                    am2.setRepeating(AlarmManager.RTC_WAKEUP, clender3.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                            pendingIntent3);


                    Calendar calendar4 = Calendar.getInstance();
                    //set the time to 6AM
                    calendar4.setTimeInMillis(System.currentTimeMillis());
                    if (duhr1>45){
                        calendar4.set(Calendar.HOUR_OF_DAY, duhr1+1);
                        calendar4.set(Calendar.MINUTE, duhr2+10);
                    }else{
                        calendar4.set(Calendar.HOUR_OF_DAY, duhr1);
                        calendar4.set(Calendar.MINUTE, duhr2+20);
                    }
                    //create a pending intent to be called at 6 AM
                    Intent myIntent4 = new Intent(MainActivity.this, UnsilenceBroadcastReceiver2.class);
                    PendingIntent pendingIntent4 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent4, 0);
                    //schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                    am2.setRepeating(AlarmManager.RTC_WAKEUP, calendar4.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent4);


                    ///////////////////////// ASR //////////////////////////
                    Calendar calendar5 = Calendar.getInstance();
                    //set the time to midnight tonight
                    calendar5.setTimeInMillis(System.currentTimeMillis());
                    calendar5.set(Calendar.HOUR_OF_DAY,asr1);
                    calendar5.set(Calendar.MINUTE,asr2);
                    AlarmManager am3 = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //create a pending intent to be called at midnight
                    Intent myIntent5 = new Intent(MainActivity.this, SilenceBroadcastReceiver3.class);
                    PendingIntent pendingIntent5 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent5, 0);
                    //schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                    am3.setRepeating(AlarmManager.RTC_WAKEUP, calendar5.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent5);


                    Calendar calendar6 = Calendar.getInstance();
                    //set the time to 6AM
                    calendar6.setTimeInMillis(System.currentTimeMillis());
                    if (asr2>45){
                        calendar6.set(Calendar.HOUR_OF_DAY, asr1+1);
                        calendar6.set(Calendar.MINUTE, asr2+10);
                    }else{
                        calendar6.set(Calendar.HOUR_OF_DAY, asr1);
                        calendar6.set(Calendar.MINUTE, asr2+20);
                    }
                    //create a pending intent to be called at 6 AM
                    Intent myIntent6 = new Intent(MainActivity.this, UnsilenceBroadcastReceiver3.class);
                    PendingIntent pendingIntent6 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent6, 0);
                    //schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                    am3.setRepeating(AlarmManager.RTC_WAKEUP, calendar6.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent6);



                    ///////////////////////// Maghrib //////////////////////////
                    Calendar calendar7 = Calendar.getInstance();
                    //set the time to midnight tonight
                    calendar7.setTimeInMillis(System.currentTimeMillis());
                    calendar7.set(Calendar.HOUR_OF_DAY,maghrib1);
                    calendar7.set(Calendar.MINUTE,maghrib2);
                    AlarmManager am4 = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //create a pending intent to be called at midnight
                    Intent myIntent7 = new Intent(MainActivity.this, SilenceBroadcastReceiver4.class);
                    PendingIntent pendingIntent7 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent7, 0);
                    //schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                    am4.setRepeating(AlarmManager.RTC_WAKEUP, calendar7.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent7);


                    Calendar calendar8 = Calendar.getInstance();
                    //set the time to 6AM
                    calendar8.setTimeInMillis(System.currentTimeMillis());
                    if (maghrib2>50){
                        calendar8.set(Calendar.HOUR_OF_DAY, maghrib1+1);
                        calendar8.set(Calendar.MINUTE, maghrib2+10);
                    }else{
                        calendar8.set(Calendar.HOUR_OF_DAY, maghrib1);
                        calendar8.set(Calendar.MINUTE, maghrib2+20);
                    }
                    //create a pending intent to be called at 6 AM
                    Intent myIntent8 = new Intent(MainActivity.this, UnsilenceBroadcastReceiver4.class);
                    PendingIntent pendingIntent8 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent8, 0);
                    //schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                    am4.setRepeating(AlarmManager.RTC_WAKEUP, calendar8.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent8);



                    ///////////////////////// ISHA //////////////////////////
                    Calendar calendar9 = Calendar.getInstance();
                    //set the time to midnight tonight
                    calendar9.setTimeInMillis(System.currentTimeMillis());
                    calendar9.set(Calendar.HOUR_OF_DAY,isha1);
                    calendar9.set(Calendar.MINUTE,isha2);
                    AlarmManager am5 = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //create a pending intent to be called at midnight
                    Intent myIntent9 = new Intent(MainActivity.this, SilenceBroadcastReceiver5.class);
                    PendingIntent pendingIntent9 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent9, 0);
                    //schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                    am5.setRepeating(AlarmManager.RTC_WAKEUP, calendar9.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent9);


                    Calendar calendar10 = Calendar.getInstance();
                    //set the time to 6AM
                    calendar10.setTimeInMillis(System.currentTimeMillis());
                    if (isha2>50){
                        calendar10.set(Calendar.HOUR_OF_DAY, isha1+1);
                        calendar10.set(Calendar.MINUTE, isha2+10);
                    }else{
                        calendar10.set(Calendar.HOUR_OF_DAY, isha1);
                        calendar10.set(Calendar.MINUTE, isha2+20);
                    }
                    //create a pending intent to be called at 6 AM
                    Intent myIntent10 = new Intent(MainActivity.this, UnsilenceBroadcastReceiver5.class);
                    PendingIntent pendingIntent10 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent10, 0);
                    //schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
                    am5.setRepeating(AlarmManager.RTC_WAKEUP, calendar10.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent10);


                    SharedPreferences sharedPreferences = getSharedPreferences("myfile",MODE_PRIVATE);
                    SharedPreferences.Editor editor =  sharedPreferences.edit();
                    editor.putInt("elsametTech",1);
                    editor.commit();

                    elsamettech=1;

                    StartSched.setText("إيقاف");


                }else if(elsamettech == 1){


                    Intent myIntent = new Intent(MainActivity.this, SilenceBroadcastReceiver.class);
                    PendingIntent  pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);

                    Intent myIntent2 = new Intent(MainActivity.this, UnsilenceBroadcastReceiver.class);
                    PendingIntent  pendingIntent2 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent2,0);

                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    manager.cancel(pendingIntent);
                    manager.cancel(pendingIntent2);

                    elsamettech=0;

                    SharedPreferences sharedPreferences = getSharedPreferences("myfile",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("elsametTech", 0);
                    editor.commit();

                    StartSched.setText("تفعيل");



                }







            }
        });


        ChangeTimes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                SharedPreferences timesShared = getSharedPreferences("times", MODE_PRIVATE);
                int fajr1 = timesShared.getInt("fajr1", 3);
                int fajr2 = timesShared.getInt("fajr2", 14);
                int duhr1 = timesShared.getInt("duhr1", 11);
                int duhr2 = timesShared.getInt("duhr2", 57);
                int asr1 = timesShared.getInt("asr1", 15);
                int asr2 = timesShared.getInt("asr2", 35);
                int maghrib1 = timesShared.getInt("maghrib1", 19);
                int maghrib2 = timesShared.getInt("maghrib2", 2);
                int isha1 = timesShared.getInt("isha1", 20);
                int isha2 = timesShared.getInt("isha2", 37);


                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_times);
                dialog.setTitle("أوقات الفروض");

                // set the custom dialog components - text, image and button
                EditText FajrhrET = (EditText) dialog.findViewById(R.id.fagrhour);
                EditText FajrminET = (EditText) dialog.findViewById(R.id.fagrMin);
                FajrhrET.setText(fajr1 + "");
                FajrminET.setText(fajr2 + "");

                EditText DuhrhrET = (EditText) dialog.findViewById(R.id.zohrhour);
                EditText DuhrminET = (EditText) dialog.findViewById(R.id.zohrMin);
                DuhrhrET.setText(duhr1 + "");
                DuhrminET.setText(duhr2 + "");

                EditText AsrhrET = (EditText) dialog.findViewById(R.id.asrhour);
                EditText AsrminET = (EditText) dialog.findViewById(R.id.asrMin);
                AsrhrET.setText(asr1 + "");
                AsrminET.setText(asr2 + "");

                EditText MaghribhrET = (EditText) dialog.findViewById(R.id.maghrebhour);
                EditText MaghribminET = (EditText) dialog.findViewById(R.id.maghrebMin);
                MaghribhrET.setText(maghrib1 + "");
                MaghribminET.setText(maghrib2 + "");

                EditText IshahrET = (EditText) dialog.findViewById(R.id.eshahour);
                EditText IshaminET = (EditText) dialog.findViewById(R.id.eshaMin);
                IshahrET.setText(isha1 + "");
                IshaminET.setText(isha2 + "");


                TextView warningTV = (TextView) dialog.findViewById(R.id.desc1);
                TextView TV1 = (TextView) dialog.findViewById(R.id.tv1);
                TextView TV2 = (TextView) dialog.findViewById(R.id.tv2);
                TextView TV3 = (TextView) dialog.findViewById(R.id.tv3);
                TextView TV4 = (TextView) dialog.findViewById(R.id.tv4);
                TextView TV5 = (TextView) dialog.findViewById(R.id.tv5);
                TextView TV6 = (TextView) dialog.findViewById(R.id.tv6);
                TextView TV7 = (TextView) dialog.findViewById(R.id.tv7);
                TextView TV8 = (TextView) dialog.findViewById(R.id.tv8);


                Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/DroidSansArabic.ttf");
                warningTV.setTypeface(tf2);
                TV1.setTypeface(tf2);
                TV2.setTypeface(tf2);
                TV3.setTypeface(tf2);
                TV4.setTypeface(tf2);
                TV5.setTypeface(tf2);
                TV6.setTypeface(tf2);
                TV7.setTypeface(tf2);
                TV8.setTypeface(tf2);


                dialog.show();


            }
        });


        playTazkeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioButton rad1 = (RadioButton) findViewById(R.id.min10);
                RadioButton rad2 = (RadioButton) findViewById(R.id.min20);
                RadioButton rad3 = (RadioButton) findViewById(R.id.min30);
                int timeTazker = 10;

                if (rad1.isChecked()){
                    timeTazker = 10;
                }else if(rad2.isChecked()){
                    timeTazker=20;
                }else if(rad3.isChecked()){
                    timeTazker=30;
                }

                if (id==0){

                    Log.e("BtnClicka","0");
                    //ya3ne mesh mshaghalhaa lesa
                    SharedPreferences sharedPreferences = getSharedPreferences("myfile",MODE_PRIVATE);
                    SharedPreferences.Editor editor =  sharedPreferences.edit();
                    editor.putInt("playTazkera",1);
                    editor.putInt("tazkPosss",tazkeraPosition);
                    editor.commit();

                    AlarmManager am2 = (AlarmManager)getSystemService(ALARM_SERVICE);
                    Intent myIntent3 = new Intent(MainActivity.this, TazkeerBroadcastRecevier.class);
                    PendingIntent pendingIntent3 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent3, 0);


                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTimeInMillis(System.currentTimeMillis());

                    am2.setRepeating(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis(), 1000 * 60 * timeTazker,
                            pendingIntent3);

                    playTazkeer.setText("إيقاف");
                    id  = 1;
                } else if(id==1){

                    Log.e("BtnClicka","1");
                    //ya3ne  mshaghalhaa w 3awez a2felha

                    Intent myIntent = new Intent(MainActivity.this, TazkeerBroadcastRecevier.class);
                    PendingIntent  pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);

                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    manager.cancel(pendingIntent);

                    SharedPreferences sharedPreferences = getSharedPreferences("myfile",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("playTazkera",0);
                    editor.commit();

                    playTazkeer.setText("تشغيل");
                    id  = 0;


                }






            }
        });



    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public  void updateTimesMethod(){
        mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setMessage("Update Times . . .");
        mDialog.show();

        GPSTracker gps = new GPSTracker(MainActivity.this);

        long unixTime = System.currentTimeMillis() / 1000L;

        if (gps.canGetLocation()){

            double lat = gps.getLatitude();
            double longit = gps.getLongitude();
            Log.e("locationMan", lat + " " + longit);



            String url = "http://api.aladhan.com/timings/"+unixTime+"?latitude="+lat+
                    "&longitude="+longit+"&timezonestring="+ TimeZone.getDefault().getID()+"&method=5";


            StringRequest timesrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    try {

                        Log.e("jobsResponsing", response);
                        mDialog.dismiss();
                        JSONObject object = new JSONObject(response);
                        JSONObject dataobject = object.getJSONObject("data");
                        JSONObject timingsobject = dataobject.getJSONObject("timings");


                        String Fajr = timingsobject.getString("Fajr");
                        String Dhuhr = timingsobject.getString("Dhuhr");
                        String Asr = timingsobject.getString("Asr");
                        String Maghrib = timingsobject.getString("Maghrib");
                        String Isha = timingsobject.getString("Isha");



                        SimpleDateFormat df = new SimpleDateFormat("kk:mm");
                        Date fajr = df.parse(Fajr);
                        int fajrhr = fajr.getHours();
                        int fajrmin = fajr.getMinutes();

                        Date duhr = df.parse(Dhuhr);
                        int duhrhr = duhr.getHours();
                        int duhrmin = duhr.getMinutes();

                        Date asr = df.parse(Asr);
                        int asrhr = asr.getHours();
                        int asrmin = asr.getMinutes();

                        Date magreb = df.parse(Maghrib);
                        int magrebhr = magreb.getHours();
                        int magrebmin = magreb.getMinutes();

                        Date isha = df.parse(Isha);
                        int ishahr = isha.getHours();
                        int ishamin = isha.getMinutes();

                        SharedPreferences sharedPreferences = getSharedPreferences("times",MODE_PRIVATE);
                        SharedPreferences.Editor editor =  sharedPreferences.edit();
                        editor.putInt("fajr1", fajrhr);
                        editor.putInt("fajr2",fajrmin);
                        editor.putInt("duhr1",duhrhr);
                        editor.putInt("duhr2",duhrmin);
                        editor.putInt("asr1",asrhr);
                        editor.putInt("asr2",asrmin);
                        editor.putInt("maghrib1",magrebhr);
                        editor.putInt("maghrib2",magrebmin);
                        editor.putInt("isha1",ishahr);
                        editor.putInt("isha2",ishamin);
                        editor.commit();






                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mDialog.dismiss();

                }
            });

            Volley.newRequestQueue(MainActivity.this).add(timesrequest);





        }
        else {
            Toast.makeText(MainActivity.this, "من فضلك قم بفتح إعدادات الموقع", Toast.LENGTH_SHORT).show();
            gps.showSettingsAlert();
        }

    }
}
