package com.example.merqueoandcinemaapp.view


import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.merqueoandcinemaapp.BaseFragment
import com.example.merqueoandcinemaapp.R
import com.example.merqueoandcinemaapp.viewModel.MovieDescriptionFrViewModel
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_movie_description.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
class MovieDescriptionFragment : BaseFragment() {
    //vars
    private lateinit var viewModel: MovieDescriptionFrViewModel
    var uid: Int by Delegates.notNull()

    override fun initialLayout(): Int {
     return R.layout.fragment_movie_description
    }

    override fun initComponents(view: View) {
        viewModel = createViewModel()
        imv_back_arrow_main.isEnabled = false
        GlobalScope.launch(Dispatchers.Unconfined) {
            launch {
                delay(400L)
                withContext(Main){
                    imv_back_arrow_main.isEnabled = true
                }
            }
        }
        dbMovie()
        getMovie()
        backArrowSetUp()

    }

    override fun initialSetUp() {
        uid = arguments!!.getInt("id")
    }

    private fun createViewModel(): MovieDescriptionFrViewModel{
        return ViewModelProvider(this).get(MovieDescriptionFrViewModel(activity!!.application)::class.java)
    }

    private fun dbMovie(){
        pg_main.visibility = View.VISIBLE
        viewModel.getMovie(uid)
    }

    private fun getMovie(){
        viewModel.moviesMutableLiveData.observe(this, Observer {movieDetail ->
            pg_main.visibility = View.GONE
            movieDetail?.let {
                txv_movie_title.text = movieDetail.title
                Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500/${movieDetail.posterPath}")
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imv_movie_img)
                txv_movie_description.text = movieDetail.overview

            }
        })
    }

    fun backArrowSetUp(){
        imv_back_arrow_main.setOnClickListener {
            activity?.let {
                imv_back_arrow_main.isEnabled = false
                it.onBackPressed()
            }
        }
    }


}
