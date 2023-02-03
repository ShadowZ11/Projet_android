package com.example.gamepod.main

import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamepod.GamePreview
import com.example.gamepod.ListGameAdapter
import com.example.gamepod.R
import com.example.gamepod.Request
import com.example.gamepod.connexion.ConnexionActivity
import com.example.gamepod.connexion.ConnexionFragment
import com.example.gamepod.connexion.connect
import com.example.gamepod.gameDetails.GameDetailsActivity
import com.example.gamepod.myLikes.MyLikesActivity
import com.example.gamepod.myWishList.MyWishListActivity
import com.example.gamepod.search.SearchActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list_game)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val games: MutableList<GamePreview> = mutableListOf()

        val myLikes = view.findViewById<ImageView>(R.id.to_my_fav)
        val myWishList = view.findViewById<ImageView>(R.id.to_my_wish_list)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_circular_home)
        val search_game = view.findViewById<ImageView>(R.id.search_game)
        val edit_search = view.findViewById<EditText>(R.id.value_search_game)
        val showMore = view.findViewById<Button>(R.id.show_more_game)
        val disconnectButton = view.findViewById<ImageView>(R.id.disconnect_button)

        showMore.setOnClickListener{
            val intent = Intent(activity, GameDetailsActivity::class.java)
            intent.putExtra("idGame", 1811260)
            startActivity(intent)
        }

        myLikes.setOnClickListener {
            val toMyLikes = Intent(activity, MyLikesActivity::class.java)
            startActivity(toMyLikes)
        }

        myWishList.setOnClickListener {
            val toMyWishList = Intent(activity, MyWishListActivity::class.java)
            startActivity(toMyWishList)
        }

        disconnectButton.setOnClickListener {

            connect.uuidUser = ""
            connect.email = ""

            val intent = Intent(activity, ConnexionActivity::class.java)
            startActivity(intent)

        }

        search_game.setOnClickListener {

            try {

                val i = Intent(activity, SearchActivity::class.java)
                i.putExtra("game", edit_search.text.toString())
                startActivity(i)

/*                val nextFragment = SearchFragment()
                val bundle = Bundle()
                bundle.putString("game", edit_search.text.toString())
                nextFragment.arguments = bundle

                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, nextFragment)
                transaction.addToBackStack(null)
                transaction.commit()*/
            }catch (e: Exception){
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }

        }


        GlobalScope.launch(Dispatchers.Main) {

            try {
                val request = withContext(Dispatchers.IO) {
                    Request.getTopRelease()
                }
                val obj = request.response.pages[0]
                var valueInt = 0
                for (ids in obj.items){
                    if (valueInt > 10){
                        break
                    }
                    try {
                        Log.v("id", ids.toString())
                        val getGames = withContext(Dispatchers.IO){
                            Request.getGameById(ids.appid)
                        }
                        games.add(GamePreview(getGames.id, getGames.name, getGames.description, getGames.price.toString(), getGames.logo))
                        valueInt += 1
                    }catch (_: Exception){
                    }
                }
                //val convertedObject: JsonObject = Gson().fromJson(request.toString(), JsonObject::class.java)
                val adapter = ListGameAdapter(games)
                progressBar.visibility = View.INVISIBLE
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

            } catch (e: Exception) {
                Toast.makeText(context, "Impossible de récupérer les top jeux, veuillez réessayer", Toast.LENGTH_LONG).show()
                e.message?.let { Log.e("Erreur requête", it) }
            }

        }

        return view
    }
    fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

}
