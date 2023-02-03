import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamepod.*
import kotlinx.coroutines.*

class GameDetailsFragment : Fragment() {

    var buttonChooseDescription = true
    private lateinit var progressDialog: AlertDialog
    private lateinit var logo: String
    private lateinit var icon: String
    private var id: Int? = null
    private lateinit var allReviews: List<Reviews>
    companion object {
        fun newInstance() = GameDetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_details_game, container, false)
        val recyclerViewReviews : RecyclerView? = activity?.let { RecyclerView(it) }
        if (recyclerViewReviews != null) {
            recyclerViewReviews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerViewReviews.itemAnimator = DefaultItemAnimator()
            recyclerViewReviews.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL)
            )
        }

        val idGame = arguments?.getInt("idGame")
        if (idGame == 0) {
            Toast.makeText(context, "Erreur du jeu", Toast.LENGTH_LONG).show()
            activity?.finish()
        }
        id = idGame

        var description: String = "null"
        val viewReviews = view.findViewById<LinearLayout>(R.id.view_group)

        val descriptionButton = view.findViewById<TextView>(R.id.description_path)
        descriptionButton.setBackgroundResource(R.drawable.custom_decription_full)
        val reviewButton = view.findViewById<TextView>(R.id.reviews_path)
        val like_button = view.findViewById<ImageView>(R.id.to_my_fav)
        val wishList_button = view.findViewById<ImageView>(R.id.to_my_wish_list)

        like_button.setOnClickListener {
            id?.let { it1 -> addToFavorite(it1, 1) }
            like_button.setBackgroundResource(R.drawable.like_full)
        }

        wishList_button.setOnClickListener {
            id?.let { it1 -> addToWishList(it1, 1) }
            like_button.setBackgroundResource(R.drawable.whishlist_full)
        }

        reviewButton.setOnClickListener{
            if (!buttonChooseDescription){
                return@setOnClickListener
            }
            buttonChooseDescription = false
            reviewButton.setBackgroundResource(R.drawable.custom_opinion_full)
            descriptionButton.setBackgroundResource(R.drawable.custom_decription)
            if (recyclerViewReviews != null) {
                loadReviews(viewReviews, recyclerViewReviews, allReviews)
            }
        }
        descriptionButton.setOnClickListener {
            if (buttonChooseDescription){
                return@setOnClickListener
            }
            buttonChooseDescription = true
            reviewButton.setBackgroundResource(R.drawable.custom_opinion)
            descriptionButton.setBackgroundResource(R.drawable.custom_decription_full)
            loadDescriptionGame(viewReviews, description)
        }

        val returnButton = view.findViewById<ImageView>(R.id.back_button)
        returnButton.setOnClickListener {
            requireActivity().finish()
        }

        var nameGame: String = ""
        var editorGame: String = ""


        GlobalScope.launch(Dispatchers.Main) {
            progressDialog = AlertDialog.Builder(activity)
                .setTitle("Chargement de la page de jeu...")
                .setView(R.layout.progress_dialog_loading_general)
                .setCancelable(false)
                .create()
            progressDialog.show()
            //delay(5000)

            try {
                if (idGame == null){
                    return@launch
                }
                val request = withContext(Dispatchers.IO) {
                    Request.getGameById(idGame)
                }

                /* Formater la classe en json
                val jsonObject = Gson().toJson(request)
                val convertedObject = Gson().fromJson(jsonObject, Game::class.java)*/

                nameGame = request.name
                editorGame = request.editorName
                description = request.description
                logo = request.logo
                icon = request.icon
                id = request.id
                allReviews = request.reviews

            } catch (e: Exception) {
                print(e.message)
                e.message?.let { Log.e("erreur", it) }
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
            progressDialog.dismiss()

            view.findViewById<TextView>(R.id.game_details_title).text = nameGame
            view.findViewById<TextView>(R.id.game_editor_description).text = editorGame
            val descriptionView: View = layoutInflater.inflate(R.layout.description_details_game, viewReviews, false)
            descriptionView.findViewById<TextView>(R.id.description_game).text = description
            viewReviews.addView(descriptionView)
            viewReviews.requestLayout()
        }

        return view
    }

    private fun loadDescriptionGame(view: LinearLayout, description: String){

        view.removeAllViews()
        val descriptionView: View = layoutInflater.inflate(R.layout.description_details_game, view, false)
        descriptionView.findViewById<TextView>(R.id.description_game).text = description
        view.addView(descriptionView)
        view.requestLayout()

    }

    private fun loadReviews(view: LinearLayout, recyclerViewReviews: RecyclerView, allReviews: List<Reviews>){
        view.removeAllViews()
        val reviews: MutableList<ItemReviewsView> = mutableListOf()

        allReviews.forEach {review ->
            var random = 0f
            random = if (review.votedUp){
                (2.5 + Math.random() * (5f - 2.5f)).toFloat()
            }else{
                (0f + Math.random() * (2.4 - 0f)).toFloat()
            }
            reviews.add(ItemReviewsView(review.steamUserResume.nameUser, random, review.description))
        }

        //val reviewsView: View = layoutInflater.inflate(R.layout.reviews_details_game, view, false)
        val adapter = AdapterRecyclerViewReviewsDG(reviews.toList())
        recyclerViewReviews.adapter = adapter
        view.addView(recyclerViewReviews)
        view.requestLayout()
    }


    private fun addToWishList(id: Int, idUser: Int){

        GlobalScope.launch(Dispatchers.Main) {

            try {

                withContext(Dispatchers.IO){
                    Request.addToWishList(WishListFragment(idUser, IdGames(id)))
                }

            }catch (e: java.lang.Exception){

                Toast.makeText(context, "Impossible d'ajouter à la wishList", Toast.LENGTH_SHORT).show()

            }
        }


    }
    private fun addToFavorite(id: Int, idUser: Int){
        GlobalScope.launch(Dispatchers.Main) {

            try {
                withContext(Dispatchers.IO){
                    Request.addToLikeList(LikeListFragment(idUser, IdGames(id)))
                }

            }catch (e: java.lang.Exception){

                Toast.makeText(context, "Impossible d'ajouter à la likeList", Toast.LENGTH_SHORT).show()

            }
        }
    }

}

