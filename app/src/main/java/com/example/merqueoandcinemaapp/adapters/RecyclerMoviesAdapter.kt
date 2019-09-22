package com.example.merqueoandcinemaapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.merqueoandcinemaapp.R
import com.example.merqueoandcinemaapp.model.moviePoko.MovieDetail
import com.example.merqueoandcinemaapp.viewUsefullInterfaces.MainFragmentView
import com.squareup.picasso.Picasso

class RecyclerMoviesAdapter : RecyclerView.Adapter<RecyclerMoviesAdapter.ViewHolder>() {

    //vars
     var movies: List<MovieDetail> = ArrayList()
    lateinit var mainFragmentView: MainFragmentView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text = movies[position].title
        val movieImagePath =   movies[position].posterPath
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500/$movieImagePath")
            //.networkPolicy(NetworkPolicy.OFFLINE)
            .into(holder.movieImage)

        holder.movieImage.setOnClickListener {
            mainFragmentView.movieSelected(
                movies[holder.adapterPosition].id!!
            )
        }

        holder.rentMovie.setOnClickListener {
            mainFragmentView.rentMovie(
                movies[holder.adapterPosition].id!!
            )
        }
    }

    fun submitMovieList(movies: ArrayList<MovieDetail>){
        val oldList = this.movies
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            MovieItemDiffCallBack(
                oldList,
                movies
            )
        )

       this.movies = movies
        diffResult.dispatchUpdatesTo(this)
    }

    class MovieItemDiffCallBack(
        var oldMovieDetail: List<MovieDetail>,
        var newMovieDetail: List<MovieDetail>
    ):DiffUtil.Callback(){
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldMovieDetail.get(oldItemPosition).id == newMovieDetail.get(newItemPosition).id)
        }

        override fun getOldListSize(): Int {
           return oldMovieDetail.size
        }

        override fun getNewListSize(): Int {
            return newMovieDetail.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMovieDetail.get(oldItemPosition).equals(newMovieDetail.get(newItemPosition))
        }

    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val movieTitle: TextView = itemView.findViewById(R.id.txv_movie_title)
        val movieImage: ImageView = itemView.findViewById(R.id.imv_movie_img)
        val rentMovie: ImageView = itemView.findViewById(R.id.imv_rent_movie)

    }


    fun mainFragmentView(mainFragmentView: MainFragmentView){
        this.mainFragmentView = mainFragmentView
    }
}