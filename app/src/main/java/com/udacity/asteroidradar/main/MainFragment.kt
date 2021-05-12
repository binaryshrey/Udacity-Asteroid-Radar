package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        viewModelFactory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        adapter = MainAdapter(ClickListener { asteroid ->
            Toast.makeText(context, "${asteroid.id}", Toast.LENGTH_SHORT).show()
            viewModel.onNavigate(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter

        observeAsteroids()

        viewModel.navigate.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.onNavigateComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun observeAsteroids() {
        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.toMutableList())
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //as per the guidance of the mentor : https://knowledge.udacity.com/questions/577992
        when (item.itemId) {
            R.id.next_week_asteroids -> {
                Toast.makeText(context, "Weeks Asteroids", Toast.LENGTH_SHORT).show()
                viewModel.WeeksAsteroids()
                observeAsteroids()
            }
            R.id.today_asteroids -> {
                Toast.makeText(context, "Todays Asteroids", Toast.LENGTH_SHORT).show()
                viewModel.TodaysAsteroids()
                observeAsteroids()
            }
            R.id.saved_asteroids -> {
                Toast.makeText(context, "Saved Asteroids", Toast.LENGTH_SHORT).show()
                viewModel.savedAsteroids()
                observeAsteroids()
            }
        }

        return true
    }
}
