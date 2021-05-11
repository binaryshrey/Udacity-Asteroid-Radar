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

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = MainAdapter(ClickListener { asteroid ->
            Toast.makeText(context,"${asteroid.id}",Toast.LENGTH_SHORT).show()
            viewModel.onNavigate(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.property.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.toMutableList())
            }
        })

        viewModel.navigate.observe(viewLifecycleOwner, Observer {
            if(it != null){
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.onNavigateComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
