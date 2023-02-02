package com.example.gamepod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.Window
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*

class Search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val resultText = findViewById<TextView>(R.id.number_of_result_text)
        val strResultText = resultText.text
        val mSpannableString = SpannableString(strResultText)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        resultText.text = mSpannableString


        GlobalScope.launch(Dispatchers.Main) {

            delay(5000)

            try {
                val request = withContext(Dispatchers.IO) {
                    //Request.getGameByName()
                }

                val convertedObject: JsonObject = Gson().fromJson(request.toString(), JsonObject::class.java)

            } catch (e: Exception) {
            }

        }

    }



}