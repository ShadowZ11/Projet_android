package com.example.gamepod.myWishList

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
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
import com.example.gamepod.myLikes.MyLikesFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyWishListFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

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
        val quitFavorite = view.findViewById<ImageView>(R.id.quit_wishlist)

        quitFavorite.setOnClickListener {
            listener?.onQuitLikes()
        }

        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        val games: MutableList<GamePreview> = mutableListOf()

        GlobalScope.launch(Dispatchers.Main) {

            try {

                val request = withContext(Dispatchers.IO){
                    Request.getMyWishList("1")
                }

                for (obj in request.games){
                    val game = withContext(Dispatchers.IO){
                        Request.getGameById(obj.appid)
                    }

                    games.add(GamePreview(game.id, game.name, game.description, game.price.toString(), game.icon))
                }

                val adapter = ListGameAdapter(games)
                if (recyclerView != null) {
                    recyclerView.adapter = adapter
                    recyclerView.addItemDecoration(
                        object : RecyclerView.ItemDecoration() {
                            override fun getItemOffsets(
                                outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State
                            ) {
                                super.getItemOffsets(outRect, view, parent, state)
                                outRect.bottom = 12.dpToPx()
                            }
                        }
                    )
                }

            }catch (e: java.lang.Exception){

            }

        }

        viewDetails.addView(recyclerView)
        viewDetails.requestLayout()

        return view
    }

    fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

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

