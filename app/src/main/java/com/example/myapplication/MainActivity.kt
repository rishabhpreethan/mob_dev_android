package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val initialtip = 15
class MainActivity : AppCompatActivity() {
    private lateinit var baseamount : EditText
    private lateinit var seekbartip : SeekBar
    private lateinit var tipamount : TextView
    private lateinit var totalamount : TextView
    private lateinit var tippercentagelabel : TextView
    private lateinit var tipdesc : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        baseamount = findViewById(R.id.baseamount)
        seekbartip = findViewById(R.id.seekbartip)
        tipamount = findViewById(R.id.tipamount)
        totalamount = findViewById(R.id.totalamount)
        tippercentagelabel = findViewById(R.id.tippercentagelabel)
        tipdesc = findViewById(R.id.tipdesc)

        seekbartip.progress = initialtip
        tippercentagelabel.text = "$initialtip%"
        updatetipdesc(initialtip)
        seekbartip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG ,"onProgressChanged $p1")
                tippercentagelabel.text = "$p1%"
                tipandtotal()
                updatetipdesc(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        baseamount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "onTextChanged $p0")
                tipandtotal()
            }

        })
    }

    private fun tipandtotal(){
        if (baseamount.text.isEmpty()){
            tipamount.text = ""
            totalamount.text = ""
            return
        }
        val base = baseamount.text.toString().toDouble()
        val tipperc = seekbartip.progress
        val tip = base * tipperc / 100
        val total = tip + base
        tipamount.text = "%.2f".format(tip)
        totalamount.text = "%.2f".format(total)
    }

    private fun updatetipdesc(perc: Int) {
        val t = when (perc){
            in 0..9 -> "Poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else -> "Amazing"
        }
        tipdesc.text = t
    }
}