package edu.washington.ericpeng.arewethereyet

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.telephony.*
import android.util.Log
import android.widget.Toast
import java.io.File

class Alarm : BroadcastReceiver() {
    private lateinit var message : String
    private lateinit var number : String

    override fun onReceive(context: Context, intent: Intent) {

        message = intent.getStringExtra("message")
        number = intent.getStringExtra("number")

        val audio = intent.getBooleanExtra("audio", false)


        //Toast.makeText(context, number + ": " + message, Toast.LENGTH_SHORT).show()

        if (audio){
            val i = Intent(Intent.ACTION_SEND)
            //i.putExtra("address", number)
            //i.putExtra("message", message)

            i.setPackage("com.android.mms");
            //i.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
            i.putExtra("address", number)
            i.putExtra("sms_body", message)

            val file : File = File(Environment.getExternalStorageDirectory().absolutePath, "sdcard/audio.mp3")
            val uri = Uri.fromFile(file)
            i.putExtra(Intent.EXTRA_STREAM, uri)
            i.setType("audio/*")

            val pI = PendingIntent.getActivity(context, 0, i, 0)
            val sms = SmsManager.getDefault()

            if (Build.VERSION.SDK_INT >= 21) {
                Toast.makeText(context, "Feature currently being worked on", Toast.LENGTH_SHORT).show()
                //sms.sendMultimediaMessage(context, uri, "", null, pI)
            }

            else Toast.makeText(context, "Sorry - you need to upgrade your Android version to send an audio file :)", Toast.LENGTH_SHORT).show()
        }

        else {
            val i = Intent(context, Alarm::class.java)
            val pI = PendingIntent.getActivity(context, 0, i, 0)
            val sms = SmsManager.getDefault()
            sms.sendTextMessage(number, "", message, pI, null)
        }

        //val aI

        //sms.sen
    }
}
