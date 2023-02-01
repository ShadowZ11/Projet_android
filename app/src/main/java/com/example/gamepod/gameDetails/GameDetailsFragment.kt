import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.gamepod.AdapterRecyclerViewReviewsDG
import com.example.gamepod.ItemReviewsView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.gamepod.R
import com.example.gamepod.API

class DetailsGameFragment : Fragment() {

    var buttonChooseDescription = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_details_game, container, false)

        val recyclerViewReviews = view.findViewById<RecyclerView>(R.id.list_reviews)
        recyclerViewReviews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewReviews.itemAnimator = DefaultItemAnimator()
        recyclerViewReviews.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL)
        )

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
            loadReviews(viewReviews, recyclerViewReviews)
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

        GlobalScope.launch(Dispatchers.Main) {

            delay(5000)

            try {
                val request = withContext(Dispatchers.IO) {

                    val api = Retrofit.Builder()
                        .baseUrl("gfsdgsd")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(API::class.java)

                    api.getOpinionGame(750, "1")

                }

            } catch (e: Exception) {
            }

        }
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

