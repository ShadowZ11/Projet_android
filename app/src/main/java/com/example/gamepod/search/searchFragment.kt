package com.example.gamepod.search

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamepod.GamePreview
import com.example.gamepod.ListGameAdapter
import com.example.gamepod.R
import com.example.gamepod.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val editText = view.findViewById<EditText>(R.id.value_search_game)

        val games: MutableList<GamePreview> = mutableListOf()
        val clickSearch = view.findViewById<ImageView>(R.id.search_click)

        val recyclerView = view.findViewById<RecyclerView>(R.id.search_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val resultText = view.findViewById<TextView>(R.id.number_of_result_text)
        val strResultText = resultText.text
        val mSpannableString = SpannableString(strResultText)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        resultText.text = mSpannableString

        clickSearch.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                games.clear()
                try {
                    if (editText.text.isEmpty()){
                        Toast.makeText(context, "La recherche ne peut pas être vide", Toast.LENGTH_SHORT).show()
                        return@launch
                    }
                    Log.v("value", editText.text.toString())
                    val request = withContext(Dispatchers.IO) {
                        Request.getGameByName(editText.text.toString())
                    }

                    Log.v("value", request.toString())

                    for (obj in request){
                        games.add(GamePreview(obj.name, obj.description, obj.price.toString()))
                    }
                    val adapter = ListGameAdapter(games)

                    val newStr = java.lang.StringBuilder()

                    newStr.append(resultText.text)
                    newStr.append(" ")
                    newStr.append(request.size.toString())

                    resultText.text = newStr.toString()
                    //progressBar.visibility = View.INVISIBLE
                    recyclerView.adapter = adapter
                }catch (e: Exception){
                    e.message?.let { it1 -> Log.e("error", it1) }
                    Toast.makeText(context, "Impossible de récupérer les jeux rechercher", Toast.LENGTH_LONG).show()
                }

            }
        }

        return view
    }
}
