package com.example.myquizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_my_result.*

class MyResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_result)

        resultText.text = intent.getStringExtra(MainActivity.EXTRA_MY_SCORE)
    }

    fun goBack(view: View){
        finish()
    }

}
