package com.example.merqueoandcinemaapp.view


import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.merqueoandcinemaapp.BaseFragment
import com.example.merqueoandcinemaapp.R
import com.example.merqueoandcinemaapp.adapters.RecylerRentMoviesAdapter
import com.example.merqueoandcinemaapp.viewModel.CarShoppingFragmentViewModel
import com.example.merqueoandcinemaapp.viewUsefullInterfaces.CarShoppingFragmentView
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class CarShoppingFragment : BaseFragment(),  CarShoppingFragmentView{

    //vars
    private lateinit var viewModel: CarShoppingFragmentViewModel
    private lateinit var recyclerMoviesAdapter: RecylerRentMoviesAdapter
    lateinit var navController: NavController

    override fun initialLayout(): Int {
       return R.layout.fragment_car_shopping
    }
    override fun initComponents(view: View) {
        viewModel = createViewModel()
        navController = Navigation.findNavController(view)
        initRecyclerView()
        dbMovies()
        getDbMovies()
    }

    override fun initialSetUp() {

    }

    private fun createViewModel(): CarShoppingFragmentViewModel{
        return ViewModelProvider(this).get(CarShoppingFragmentViewModel(activity!!.application)::class.java)
    }

    private fun initRecyclerView(){
        rv_movies.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerMoviesAdapter = RecylerRentMoviesAdapter()
        recyclerMoviesAdapter.mainFragmentView(this)

        imv_back_arrow_main.setOnClickListener {
            activity?.let{
                activity!!.onBackPressed()
            }
        }
    }

    private fun dbMovies(){
        pg_main.visibility = View.VISIBLE
        viewModel.getCustomerMovies()
    }

    private fun getDbMovies(){
        viewModel.moviesMutableLiveData.observe(this, Observer {
            pg_main.visibility = View.GONE
            it?.let {
                if(it.results != null){
                    rv_movies.adapter = recyclerMoviesAdapter
                    GlobalScope.launch(Dispatchers.Unconfined) {
                        val job = launch {
                            recyclerMoviesAdapter.submitMovieList(it.results!!)
                        }
                        job.join()
                    }
                }
            }
        })
    }


    override fun deleteMovieFromCar(id: Int, size: Int) {
        pg_main.visibility = View.VISIBLE
        viewModel.deleteRentMovieFromdb(id)
        if(size == 1) {
            activity?.let {
                activity!!.onBackPressed()
            }
        }
    }

    override fun movieSelected(id: Int) {
        val bundle = bundleOf("id" to id)
        navController!!.navigate(R.id.action_carShoppingFragment_to_movieDescriptionFragment, bundle)
    }


}
