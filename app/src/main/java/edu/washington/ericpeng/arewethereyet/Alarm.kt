package edu.washington.ericpeng.arewethereyet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class Alarm : BroadcastReceiver() {
    private lateinit var message : String
    private lateinit var number : String
    //private lateinit var interval : Long

    override fun onReceive(context: Context, intent: Intent) {

        message = intent.getStringExtra("message")
        number = intent.getStringExtra("number")

        Toast.makeText(context, number + ": " + message, Toast.LENGTH_SHORT).show()

    }
}
