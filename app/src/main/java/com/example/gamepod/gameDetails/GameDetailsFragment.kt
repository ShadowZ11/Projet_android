import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamepod.*
import com.example.gamepod.connexion.ConnexionFragment
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameDetailsFragment : Fragment() {

    var buttonChooseDescription = true

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
                loadReviews(viewReviews, recyclerViewReviews)
            }
        }
        descriptionButton.setOnClickListener {
            if (buttonChooseDescription){
                return@setOnClickListener
            }
            buttonChooseDescription = true
            reviewButton.setBackgroundResource(R.drawable.custom_opinion)
            descriptionButton.setBackgroundResource(R.drawable.custom_decription_full)
            loadDescriptionGame(viewReviews)
        }

        val returnButton = view.findViewById<ImageView>(R.id.back_button)
        returnButton.setOnClickListener {
            requireActivity().finish()
        }

        return view
    }

    private fun loadDescriptionGame(view: LinearLayout){

        view.removeAllViews()
        val descriptionView: View = layoutInflater.inflate(R.layout.description_details_game, view, false)
        view.addView(descriptionView)
        view.requestLayout()

    }

    private fun loadReviews(view: LinearLayout, recyclerViewReviews: RecyclerView){
        view.removeAllViews()

        var nameGame: String = ""
        var rateGame: Float = 0f
        var descriptionGame: String = ""


        GlobalScope.launch(Dispatchers.Main) {

            delay(5000)

            try {
                val request = withContext(Dispatchers.IO) {

                    val api = Retrofit.Builder()
                        .baseUrl("https://us-central1-androidsteam-b9b14.cloudfunctions.net")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(API::class.java)

                    api.getGames("750")
                }

                val convertedObject: JsonObject = Gson().fromJson(request.toString(), JsonObject::class.java)

                nameGame = convertedObject.get("name").asString
               // rateGame = convertedObject.get("votedUp").asFloat
                descriptionGame = convertedObject.get("description").asString

            } catch (e: Exception) {
            }

        }
        view.findViewById<TextView>(R.id.game_details_title).text = nameGame
        view.findViewById<TextView>(R.id.game_details_description).text = descriptionGame

        //val reviewsView: View = layoutInflater.inflate(R.layout.reviews_details_game, view, false)
        val reviews = listOf(
            ItemReviewsView("Game 1", 0.9f,"Description for Game 1"),
            ItemReviewsView("Game 2", 5f,"Description for Game 2"),
            ItemReviewsView("Game 3", 2f,"Description for Game 3"),
            ItemReviewsView("Game 1", 2.04f,"Description for Game 1"),
            ItemReviewsView("Game 2", 1.6f,"Description for Game 2"),
            ItemReviewsView("Game 3", 3f,"Description for Game 3")
        )
        val adapter = AdapterRecyclerViewReviewsDG(reviews)
        recyclerViewReviews.adapter = adapter
        view.addView(recyclerViewReviews)
        view.requestLayout()
    }

}

