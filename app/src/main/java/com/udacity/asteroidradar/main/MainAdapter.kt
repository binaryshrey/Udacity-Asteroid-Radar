package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ListItemAsteroidsBinding

class MainAdapter : ListAdapter<Asteroid, MainAdapter.ViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid)
    }

    class ViewHolder private constructor(val binding : ListItemAsteroidsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(asteroid: Asteroid){
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemAsteroidsBinding.inflate(layoutInflater,parent,false)

                return ViewHolder(binding)
            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Asteroid>(){
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }

}