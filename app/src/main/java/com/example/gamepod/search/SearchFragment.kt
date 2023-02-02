package com.example.gamepod.search

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gamepod.R

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_search, container, false)

        val resultText = view.findViewById<TextView>(R.id.number_of_result_text)
        val strResultText = resultText.text
        val mSpannableString = SpannableString(strResultText)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        resultText.text = mSpannableString

        return view
    }
}
