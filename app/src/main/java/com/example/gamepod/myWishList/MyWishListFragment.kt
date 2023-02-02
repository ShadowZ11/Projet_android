package com.example.gamepod.myWishList

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


class MyWishListFragment : Fragment() {

    private var listener: MyWishListFragment.OnFragmentInteractionListener? = null

    companion object {
        fun newInstance() = MyWishListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_my_wish_list, container, false)
        val viewDetails = view.findViewById<LinearLayout>(R.id.myWishListContent)
        val recyclerView : RecyclerView? = activity?.let { RecyclerView(it) }

        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        val games = listOf(
            GamePreview("Game 1", "Description for Game 1", "10", "https://cdn.akamai.steamstatic.com/steam/apps/534380/capsule_184x69.jpg"),
            GamePreview("Game 2", "Description for Game 2", "20", "https://cdn.akamai.steamstatic.com/steam/apps/534380/capsule_184x69.jpg"),
            GamePreview("Game 3", "Description for Game 3", "30", "https://cdn.akamai.steamstatic.com/steam/apps/534380/capsule_184x69.jpg"),
            GamePreview("Game 1", "Description for Game 1", "10", "https://cdn.akamai.steamstatic.com/steam/apps/534380/capsule_184x69.jpg"),
            GamePreview("Game 2", "Description for Game 2", "20", "https://cdn.akamai.steamstatic.com/steam/apps/534380/capsule_184x69.jpg"),
            GamePreview("Game 3", "Description for Game 3", "30", "https://cdn.akamai.steamstatic.com/steam/apps/534380/capsule_184x69.jpg")
        )

        viewDetails.addView(recyclerView)
        viewDetails.requestLayout()

        val adapter = ListGameAdapter(games)
        if (recyclerView != null) {
            recyclerView.adapter = adapter
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

