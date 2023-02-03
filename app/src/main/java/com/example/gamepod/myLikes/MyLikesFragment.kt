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
import com.example.gamepod.Request
import com.example.gamepod.myWishList.MyWishListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val games: MutableList<GamePreview> = mutableListOf()

        GlobalScope.launch(Dispatchers.Main) {

            try {

                val request = withContext(Dispatchers.IO){
                    Request.getLikeList("1")
                }

                for (obj in request.games){
                    val game = withContext(Dispatchers.IO){
                        Request.getGameById(obj.appid)
                    }

                    games.add(GamePreview(game.id, game.name, game.description, game.price.toString(), game.logo))
                }

                if (request.games.isEmpty()){

                }

                val adapter = ListGameAdapter(games)
                if (recyclerView != null) {
                    recyclerView.adapter = adapter
                }

            }catch (_: java.lang.Exception){
            }

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
