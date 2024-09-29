package com.example.smsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;
import android.telephony.SmsMessage;

import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.getAction().equals(SMS_RECEIVED_ACTION))
        {
            Toast.makeText(context, "SMS RECEIVED", Toast.LENGTH_SHORT).show();
            Log.v("SMS RECEIVED BROADCAST", "SMS RECEIVED RECEIVED IN MY SMS APP");

            try{
                SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                for (int i = 0; i < messages.length; i++)
                {
                    /*********** Toast.makeText(context, messages[i].getOriginatingAddress(), Toast.LENGTH_LONG).show(); ********************/
                   /******************* Toast.makeText(context, messages[i].getMessageBody(), Toast.LENGTH_LONG).show(); **********************/

                   /***************** EXPERIMENT *********************************/
                        if(messages[i].getOriginatingAddress().trim().equalsIgnoreCase("+263164"))
                        {

                            // String post1 = messages[i].getOriginatingAddress().trim();
                            // String post2 = messages[i].getMessageBody().trim();
                            // final String post3 = "number=" + post1 + "&" + "message=" + post2;
 PL
                            Intent postData = new Intent(context, MyService.class).setAction("POSTTHISMESSAGE");
                            postData.putExtra("postData", messages[i].getMessageBody().trim());
                            context.startService(postData);



                            // ************** EXPERIMENT ********************
                            context.startService(new Intent(context, ReConnect.class));
                            // **********************************************
                        }

                   /**************************************************************/









                }

            }catch (Exception e)
            {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }




    }
}
