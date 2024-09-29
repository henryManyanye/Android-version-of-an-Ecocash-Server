package com.example.smsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static android.media.AudioManager.ACTION_HEADSET_PLUG;
import static android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /***************** EXPERIMENT *********************/
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2000);

        }


       /* WifiManager wmgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wmgr.reassociate(); */

        /*************************************************/

        /******************** THIS CODE WORKS ******************
        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (SMS_RECEIVED_ACTION.equals(action)) {
                    Toast.makeText(getApplicationContext(), "Received a new message", Toast.LENGTH_LONG).show();

                    try{
                        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                        for (int i = 0; i < messages.length; i++)
                        {
                            Toast.makeText(getApplicationContext(), messages[i].getOriginatingAddress(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), messages[i].getMessageBody(), Toast.LENGTH_LONG).show();


                            String post1 = messages[i].getOriginatingAddress().trim();
                            String post2 = messages[i].getMessageBody().trim();
                           final String post3 = "number=" + post1 + "&" + "message=" + post2;

                            new Thread(new Runnable() {
                                public void run() {

                                    try{
                                        URL url = new URL("http://192.168.43.202/php/ecocashCode/25may2022.php");
                                        HttpURLConnection urlConnection =  (HttpURLConnection) url.openConnection();

                                        urlConnection.setDoOutput(true);


                                        OutputStreamWriter   out =  new OutputStreamWriter(urlConnection.getOutputStream());
                                        out.write(post3); // DONT FORGET TO URLENCODE
                                        out.close(); // WITHOUT THIS CODE, BLANK POSTS ARE SENT. OK RESPONSES ARE ALSO RECEIVED EVEN IF THE CODE IS BUGGY
                                        int response = urlConnection.getResponseCode();

                                        Log.v("response::::::", "response: " + response);

                                        ;

                                    }catch (Exception e)
                                    {
                                        Log.v("ERROR::::::", e.getMessage());
                                    }


                                }
                            }).start();






                        }

                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        };
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(SMS_RECEIVED_ACTION);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

          ***************** END OF CODE THAT WORKS **********************************/


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case 2000:
            {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "PERMISSION_GRANTED", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void startTheService(View v)
    {
        /*********************** EXPERIMENT ********************

        new Thread(new Runnable() {
            public void run() {

                try{
                    URL url = new URL("http://192.168.43.202/php/ecocashCode/25may2022.php");
                    HttpURLConnection urlConnection =  (HttpURLConnection) url.openConnection();

                    urlConnection.setDoOutput(true);


                    OutputStreamWriter   out =  new OutputStreamWriter(urlConnection.getOutputStream());
                      out.write("YOUSUCK=YOUSUCK");
                      out.close(); // WITHOUT THIS CODE, BLANK POSTS ARE SENT. OK RESPONSES ARE ALSO RECEIVED EVEN IF THE CODE IS BUGGY
                     int response = urlConnection.getResponseCode();

                      Log.v("response::::::", "response: " + response);

                      ;

                }catch (Exception e)
                {
                    Log.v("ERROR::::::", e.getMessage());
                }


            }
        }).start();
         *******************************************************/


       // startService(new Intent(getApplicationContext(), MyService.class));

    }

    public void stopTheService(View v)
    {


     //   startService(new Intent(getApplicationContext(), MyService.class).setAction("stopTheService"));
    }
}
