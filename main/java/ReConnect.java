package com.example.smsapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.widget.Toast;

import static android.content.Intent.ACTION_TIME_TICK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.net.NetworkCapabilities.TRANSPORT_WIFI;

public class ReConnect extends Service {

    ConnectivityManager connMgr;

    BroadcastReceiver mReceiver;

    boolean isRunning = false;


    public ReConnect() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        /******** Toast.makeText(getApplicationContext(), "Starting service for resending backed-up messages", Toast.LENGTH_LONG).show(); *******************/



        if(!isRunning)
        {
            mReceiver = new BroadcastReceiver()
            {
                public void onReceive(Context context, Intent intent)
                {
                    String action = intent.getAction();

                    if (ACTION_TIME_TICK.equals(action))
                    {
                        /*************** Toast.makeText(getApplicationContext(), "ACTION_TIME_TICK", Toast.LENGTH_SHORT).show(); ***********************/
                        context.startService(new Intent(context, MyService.class).setAction("SENDBACKEDUPMESSAGES"));


                    }
                }
            };

            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_TIME_TICK);
            registerReceiver(mReceiver, filter);

            isRunning = true;
        }else{
            return START_NOT_STICKY;
        }






        /* WifiManager wmgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wmgr.reassociate(); */

       // stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
