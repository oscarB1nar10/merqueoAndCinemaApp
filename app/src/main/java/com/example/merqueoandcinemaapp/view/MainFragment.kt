package com.example.merqueoandcinemaapp.view


import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import com.example.merqueoandcinemaapp.adapters.RecyclerMoviesAdapter
import com.example.merqueoandcinemaapp.viewModel.MainFragmentViewModel
import com.example.merqueoandcinemaapp.viewUsefullInterfaces.MainFragmentView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment(), MainFragmentView {
    //vars
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var recyclerMoviesAdapter: RecyclerMoviesAdapter
    lateinit var navController: NavController

    override fun initialLayout(): Int {
        return R.layout.fragment_main
    }

    override fun initComponents(view: View) {
        viewModel = createViewModel()
        navController = Navigation.findNavController(view)


        initRecyclerView()
        initSwipprel()
        networkMoviesList()
        getMoviesList()
        dbCustomerNumberRent()
        getCustomerMoviesNum()

    }

    override fun initialSetUp() {
    }

    private fun createViewModel(): MainFragmentViewModel{
        return ViewModelProvider(this).get(MainFragmentViewModel(activity!!.application)::class.java)
    }

    private fun initSwipprel(){
        imv_back_arrow_main.visibility = View.GONE

        srl_main_swipe.setOnRefreshListener {
            if(viewModel != null){
                viewModel.getMovies()
            }
        }

        imv_shopping_car.setOnClickListener {
            if(txv_movies_number.text.toString().toInt() > 0){
                navController!!.navigate(R.id.action_mainFragment_to_carShoppingFragment)
            }else{
                Snackbar.make(this!!.view!!, "No hay peliculas en carrito", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        (activity as AppCompatActivity).setSupportActionBar(tb_main)
        setHasOptionsMenu(true)

        tb_main.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.itm_delete_rent_list -> {
                    viewModel.delleteAllCustomerMovies()
                    txv_movies_number.text = "0"
                }
                else -> { }
            }
            return@setOnMenuItemClickListener true
        }

    }

    private fun initRecyclerView(){
        rv_movies.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerMoviesAdapter = RecyclerMoviesAdapter()
        recyclerMoviesAdapter.mainFragmentView(this)

    }
    private fun networkMoviesList(){
        pg_main.visibility = View.VISIBLE
        viewModel.getMovies()
    }

    private fun getMoviesList(){
        viewModel.moviesMutableLiveData.observe(this, Observer {moviesGeneralResponse ->
            pg_main.visibility = View.GONE
            moviesGeneralResponse?.let {
                if(it.results != null){
                    //Toast.makeText(activity, "${moviesGeneralResponse.message}", Toast.LENGTH_SHORT).show()
                    rv_movies.adapter = recyclerMoviesAdapter

                    GlobalScope.launch(Dispatchers.Unconfined) {
                        recyclerMoviesAdapter.submitMovieList(it.results!!)
                    }
                    if(srl_main_swipe.isRefreshing){ srl_main_swipe.isRefreshing = false }
                }else{
                    //Toast.makeText(activity, "${moviesGeneralResponse.message}", Toast.LENGTH_SHORT).show()
                    if(srl_main_swipe.isRefreshing){ srl_main_swipe.isRefreshing = false }
                }

            }
        })
    }

    private fun dbCustomerNumberRent(){
        viewModel.getCustomerMoviesNum()
    }

    private fun getCustomerMoviesNum() {

        viewModel.customerMoviesRent.observe(this, Observer { number ->
            txv_movies_number.text = number.toString()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu, menu)
    }

    override fun movieSelected(id: Int) {
        val bundle = bundleOf("id" to id)
        navController!!.navigate(
            R.id.action_mainFragment_to_movieDescriptionFragment,
            bundle)
    }

    override fun rentMovie(id: Int) {
        viewModel.saveCustomerMovies(id)
    }








}
