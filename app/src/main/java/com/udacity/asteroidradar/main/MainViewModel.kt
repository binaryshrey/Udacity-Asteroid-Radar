package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.APIService
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = getDatabase(app)
    private val repository = Repository(database)

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _property = MutableLiveData<ArrayList<Asteroid>>()
    val property: LiveData<ArrayList<Asteroid>>
        get() = _property

    private val _picOfTheDay = MutableLiveData<PictureOfDay>()
    val picOfTheDay: LiveData<PictureOfDay>
        get() = _picOfTheDay

    private val _navigate = MutableLiveData<Asteroid?>()
    val navigate: LiveData<Asteroid?>
        get() = _navigate

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
            getPictureOfTheDay()
        }

    }

    val asteroids = repository.asteroids

    private fun getPictureOfTheDay() {
        viewModelScope.launch {
            try {
                _picOfTheDay.value = APIService.retrofitService.getPictureOfTheDay()
                Log.i("MainViewModel", "data : ${_picOfTheDay.value}")
            } catch (e: Exception) {
                Log.i("MainViewModel", "Error : ${e.message}")
            }
        }
    }

    fun onNavigate(asteroid: Asteroid) {
        _navigate.value = asteroid
    }

    fun onNavigateComplete() {
        _navigate.value = null
    }

}