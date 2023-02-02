package com.example.gamepod.myLikes

import InscriptionFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamepod.GamePreview
import com.example.gamepod.ListGameAdapter
import com.example.gamepod.R

class MyLikesFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    companion object {
        fun newInstance() = MyLikesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_my_likes, container, false)
        val viewDetails = view.findViewById<LinearLayout>(R.id.myLikesContent)
        val recyclerView : RecyclerView? = activity?.let { RecyclerView(it) }

        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        val games = listOf(
            GamePreview("Game 1", "Description for Game 1", "10"),
            GamePreview("Game 2", "Description for Game 2", "20"),
            GamePreview("Game 3", "Description for Game 3", "30"),
            GamePreview("Game 1", "Description for Game 1", "10"),
            GamePreview("Game 2", "Description for Game 2", "20"),
            GamePreview("Game 3", "Description for Game 3", "30")
        )
        val adapter = ListGameAdapter(games)
        if (recyclerView != null) {
            recyclerView.adapter = adapter
        }

        viewDetails.addView(recyclerView)
        viewDetails.requestLayout()

        val quitLikes = view.findViewById<ImageView>(R.id.quit_wishlist)
        quitLikes.setOnClickListener {
            listener?.onQuitLikes()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onQuitLikes()
    }

}