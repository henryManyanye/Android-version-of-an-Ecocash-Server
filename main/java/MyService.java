package com.example.smsapp;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;
import android.telephony.SmsMessage;
import androidx.core.app.NotificationCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED;
import static android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;

public class MyService extends Service {
    Boolean isAlreadySendingBackedUpMessages = false;

    File file;

    String createTable;




    @Override
    public void onCreate()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        /*********************EXPERIMENT ***********************/
                    createTable = "CREATE TABLE messages(message TEXT)";

                    class OpenHelper extends SQLiteOpenHelper
                    {
                        OpenHelper (Context context)
                        {
                            super(context, "messages.db", null, 1);

                        }

                        @Override
                        public void onCreate(SQLiteDatabase db)
                        {
                            db.execSQL(createTable);
                        }

                        @Override
                        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
                        {

                        }
                    }



        /********************************************************/

        final Intent copyIntent = intent;

        if(intent.getAction() == "SENDBACKEDUPMESSAGES")
        {
            if(!isAlreadySendingBackedUpMessages)
            {
                isAlreadySendingBackedUpMessages = true;

               /* File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                 file = new File(path, "backedUpMessages.txt");

                if(file.exists() == false)
                {
                    isAlreadySendingBackedUpMessages = false;
                    return START_NOT_STICKY;
                } */



                new Thread(new Runnable() {
                    public void run() {

                        try{
                             /*

                            FileReader fr = new FileReader(file);
                            LineNumberReader lnr = new LineNumberReader(fr);



                            String line = null;
                            int lineNumber = -2;

                            int totalNumberOfLines = 0;
                            while((line = lnr.readLine()) != null)
                            {
                                totalNumberOfLines = lnr.getLineNumber();
                            }

                            fr.close();
                            lnr.close();

                            fr = new FileReader(file);
                            lnr = new LineNumberReader(fr);



                            SharedPreferences settings = getSharedPreferences("lineNumberStorage", 0);
                            SharedPreferences.Editor editor = settings.edit();

                            while((line = lnr.readLine()) != null)
                            {
                                lineNumber = lnr.getLineNumber();

                                if(settings.getBoolean("didFailToResendSomeMessages", false))
                                {
                                    if(lineNumber == settings.getInt("lastFailedNumber", 0))
                                    {
                                        try{
                                            // URL url = new URL("http://192.168.43.202/php/ecocashCode/25may2022.php");
                                            URL url = new URL("http://firewall.altogradesoftwares.tk/ecocashCode.php");
                                            HttpURLConnection urlConnection =  (HttpURLConnection) url.openConnection();
                                            urlConnection.setDoOutput(true);
                                            OutputStreamWriter   out =  new OutputStreamWriter(urlConnection.getOutputStream());
                                            out.write(line); // DONT FORGET TO URLENCODE
                                            out.close(); // WITHOUT THIS CODE, BLANK POSTS ARE SENT. OK RESPONSES ARE ALSO RECEIVED EVEN IF THE CODE IS BUGGY
                                            int response = urlConnection.getResponseCode();
                                            urlConnection.disconnect();

                                            Log.v("response::::::", "response: " + response);


                                                // Log.v("response::::::", "response: " + response);
                                                if (lineNumber + 1 > totalNumberOfLines)
                                                {
                                                    editor.putBoolean("didFailToResendSomeMessages", false);
                                                    editor.putInt("lastFailedNumber", -2);
                                                    editor.commit();

                                                    // IF ALL MESSAGES ARE SENT, DELETE THE FILE.
                                                    file.delete();
                                                    break;

                                                }else {
                                                    editor.putInt("lastFailedNumber", lineNumber + 1);
                                                    editor.commit();
                                                }

                                          //  }

                                        }catch (Exception e)
                                        {
                                            editor.putInt("lastFailedNumber", lineNumber);
                                            editor.putBoolean("didFailToResendSomeMessages", true);
                                            editor.commit();
                                            break;
                                        }

                                    }


                                }else {

                                    try{
                                        URL url = new URL("http://firewall.altogradesoftwares.tk/ecocashCode.php");
                                        HttpURLConnection urlConnection =  (HttpURLConnection) url.openConnection();
                                        urlConnection.setDoOutput(true);
                                        OutputStreamWriter   out =  new OutputStreamWriter(urlConnection.getOutputStream());
                                        out.write(line); // DONT FORGET TO URLENCODE
                                        out.close(); // WITHOUT THIS CODE, BLANK POSTS ARE SENT. OK RESPONSES ARE ALSO RECEIVED EVEN IF THE CODE IS BUGGY
                                        int response = urlConnection.getResponseCode();
                                        urlConnection.disconnect();

                                        Log.v("response::::::", "response: " + response);



                                    }catch (Exception e)
                                    {
                                        editor.putInt("lastFailedNumber", lineNumber);
                                        editor.putBoolean("didFailToResendSomeMessages", true);
                                        editor.commit();

                                        break;
                                    }



                                }


                            }



                            try{
                                if(line == null)
                                {
                                    file.delete();
                                }

                            }catch (Exception e)
                            {
                                Log.v("ERROR::::::", e.getMessage());
                            }

                            fr.close();
                            lnr.close();

                            isAlreadySendingBackedUpMessages = false;

                            stopSelf();  */


                             /********************** EXPERIMENT ********************************/
                            OpenHelper openHelperInstance = new OpenHelper(getApplicationContext());
                            SQLiteDatabase db = openHelperInstance.getReadableDatabase();
                            String[] projection = {"message"};
                            Cursor c = db.query("messages", projection, null, null,null, null, null, null);
                            Log.v("CURSOR ROWS::::::", c.getCount()  + "");

                            if(c.getCount() != 0)
                            {
                                c.moveToFirst();
                                String msg;
                                String selection = "message LIKE ?";

                                do{
                                    try{
                                        msg = c.getString(c.getColumnIndexOrThrow("message"));


                                        URL url = new URL("http://firewall.altogradesoftwares.tk/ecocashCode.php");
                                        HttpURLConnection urlConnection =  (HttpURLConnection) url.openConnection();
                                        urlConnection.setDoOutput(true);
                                        OutputStreamWriter   out =  new OutputStreamWriter(urlConnection.getOutputStream());
                                        out.write(msg); // DONT FORGET TO URLENCODE
                                        out.close(); // WITHOUT THIS CODE, BLANK POSTS ARE SENT. OK RESPONSES ARE ALSO RECEIVED EVEN IF THE CODE IS BUGGY
                                        int response = urlConnection.getResponseCode();
                                        urlConnection.disconnect();
                                        Log.v("response::::::", "response: " + response);


                                        String[] selectionArgs = {msg};
                                        db = openHelperInstance.getWritableDatabase();
                                        int i = db.delete("messages", selection, selectionArgs);
                                        Log.v("db.delete::::::", i + "");

                                    }catch (Exception e)
                                    {
                                        // openHelperInstance.close();
                                        Log.v("ERROR::::::", e.getMessage());
                                        break;

                                       // stopSelf();
                                    }


                                }while (c.moveToNext());

                                openHelperInstance.close();
                                isAlreadySendingBackedUpMessages = false;
                                stopSelf();


                            }else{
                                openHelperInstance.close();
                                isAlreadySendingBackedUpMessages = false;
                                stopSelf();

                            }





                             /******************************************************************/

                        }catch (Exception e)
                        {

                            Log.v("ERROR::::::", e.getMessage());
                            stopSelf();
                        }


                    }
                }).start();


            }else{
                return START_NOT_STICKY;
            }


        }

        if(intent.getAction() == "POSTTHISMESSAGE") {
            /************* EXPERIMENT ******************************************/

                 new Thread(new Runnable() {
                public void run() {

                    try {
                        URL url = new URL("http://firewall.altogradesoftwares.tk/ecocashCode.php");
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                        urlConnection.setDoOutput(true);


                        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                        out.write(copyIntent.getStringExtra("postData")); // DONT FORGET TO URLENCODE
                        out.close(); // WITHOUT THIS CODE, BLANK POSTS ARE SENT. OK RESPONSES ARE ALSO RECEIVED EVEN IF THE CODE IS BUGGY
                        int response = urlConnection.getResponseCode();

                        urlConnection.disconnect();

                        Log.v("response::::::", "response: " + response);


                          /*  if(response == 200) // BACKUP THE MESSAGE
                           {
                               Log.v("response::::::", "response: " + response);

                           } */


                    } catch (Exception e) {

                        Log.v("ERROR::::::", e.getMessage());

                        try {

                           /* File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                            File file = new File(path, "backedUpMessages.txt");

                            FileWriter fw = new FileWriter(file, true);
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(copyIntent.getStringExtra("postData"));
                            bw.newLine();
                            bw.close();
                            fw.close(); */

                            /****************** EXPERIMENT ***********************/

                            OpenHelper openHelperInstance = new OpenHelper(getApplicationContext());
                            SQLiteDatabase db = openHelperInstance.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("message", copyIntent.getStringExtra("postData"));
                            long i = db.insert("messages", null, values);
                            openHelperInstance.close();
                            Log.v("db.insert Row id::::::", i + "");


                            /*****************************************************/




                            stopSelf();

                        } catch (Exception ee) {

                            Log.v("ERROR::::::", ee.getMessage());
                            stopSelf();
                        }


                        stopSelf();
                    }


                }
            }).start();





            /******************************************************************/
        }






        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy()
    {

    }
}
