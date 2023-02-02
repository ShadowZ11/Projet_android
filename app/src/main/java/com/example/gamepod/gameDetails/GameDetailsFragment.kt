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
import com.google.gson.Gson
import com.google.gson.JsonObject
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

        var description: String = "null"
        val viewReviews = view.findViewById<LinearLayout>(R.id.view_group)

        val descriptionButton = view.findViewById<TextView>(R.id.description_path)
        descriptionButton.setBackgroundResource(R.drawable.custom_decription_full)
        val reviewButton = view.findViewById<TextView>(R.id.reviews_path)

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
                .setView(R.layout.progress_dialog)
                .setCancelable(false)
                .create()
            progressDialog.show()
            delay(5000)

            try {
                val request = withContext(Dispatchers.IO) {
                    Request.getGames("730")
                }

                /* Formaté la classe en json
                val jsonObject = Gson().toJson(request)
                val convertedObject = Gson().fromJson(jsonObject, Game::class.java)*/

                nameGame = request.name
                // rateGame = convertedObject.get("votedUp").asFloat
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

/*        GlobalScope.launch(Dispatchers.Main) {

            try {

                val request = withContext(Dispatchers.IO){
                    Request.getOpinionGame(730)
                }

                val convertedObject: JsonObject = Gson().fromJson(request.toString(), JsonObject::class.java)

                for (obj in convertedObject.asJsonArray){
                    val objElement = obj.asJsonObject
                    reviews.toMutableList().add(ItemReviewsView(objElement.get("steamUserResume").asJsonObject.get("steamUserName").asString,
                        objElement.get("rating").asFloat, objElement.get("description").asString))
                }

            }catch (e: Exception){
                Toast.makeText(context, "Erreur récupération api", Toast.LENGTH_SHORT).show()
            }

        }*/

        //val reviewsView: View = layoutInflater.inflate(R.layout.reviews_details_game, view, false)
        val adapter = AdapterRecyclerViewReviewsDG(reviews.toList())
        recyclerViewReviews.adapter = adapter
        view.addView(recyclerViewReviews)
        view.requestLayout()
    }

}

