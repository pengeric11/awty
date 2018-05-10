package edu.washington.ericpeng.arewethereyet

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import java.util.*
import edu.washington.ericpeng.arewethereyet.R.id.editText
import java.text.DecimalFormat
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {

    private lateinit var btn : Button
    private lateinit var message : EditText
    private lateinit var phone : EditText
    private lateinit var interval : EditText
    private lateinit var checkbox : CheckBox

    private lateinit var int : Intent
    private lateinit var pIntent : PendingIntent

    private lateinit var mText : String
    private lateinit var pText : String
    private lateinit var dText : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.button)
        message = findViewById(R.id.editText)
        phone = findViewById(R.id.editTextNum)
        interval = findViewById(R.id.editTextNag)
        checkbox = findViewById(R.id.checkBox)

        message.addTextChangedListener(tw)
        phone.addTextChangedListener(tw)
        interval.addTextChangedListener(tw)

        phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        btn.text = "Start"
        btn.isEnabled = false

        btn.setOnClickListener({

            //manager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            int = Intent(applicationContext, Alarm::class.java)
            pIntent = PendingIntent.getBroadcast(applicationContext, 0, int, PendingIntent.FLAG_UPDATE_CURRENT)

            if (btn.text == "Stop"){
                btn.text = "Start"
                stopTimer()
            }

            else if (btn.text == "Start"){
                btn.text = "Stop"
                int.putExtra("message", mText)
                int.putExtra("number", pText)

                val audio = checkbox.isChecked

                int.putExtra("audio", audio)

                pIntent = PendingIntent.getBroadcast(applicationContext, 0, int, PendingIntent.FLAG_UPDATE_CURRENT)

                setAlarm()

            }

        })
    }

    private fun setAlarm(){
        val manager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime(), 60000, pIntent)

    }

    private fun stopTimer() {
        val manager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        manager.cancel(pIntent)
    }

    private var tw : TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            mText = message.text.toString()
            pText = phone.text.toString()
            dText = interval.text.toString()

            btn.isEnabled = !message.text.isNullOrEmpty() && !phone.text.isNullOrEmpty() && !interval.text.isNullOrEmpty()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
    }

}
