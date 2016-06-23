package com.BasemN.salaty.elasalaty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

/**
 * Created by BasemNasr on 6/18/2016.
 */
public class TazkeerBroadcastRecevier extends BroadcastReceiver {



    int sound;
    @Override
    public void onReceive(Context context, Intent intent) {



        SharedPreferences sharedPreferences2 = context.getSharedPreferences("myfile", context.MODE_PRIVATE);
        sound = sharedPreferences2.getInt("tazkPosss", 0);

        if (sound==0){

            MediaPlayer mp = MediaPlayer.create(context,R.raw.allahomsaly);
            mp.start();

        }else if (sound==1){

            MediaPlayer mp = MediaPlayer.create(context,R.raw.ezkor_ellah);
            mp.start();

        }else if (sound==2){

            MediaPlayer mp = MediaPlayer.create(context,R.raw.est);
            mp.start();

        }else if (sound==3){

            MediaPlayer mp = MediaPlayer.create(context,R.raw.sob7an);
            mp.start();

        }




    }

}
