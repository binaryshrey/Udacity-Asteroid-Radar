package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.APIService
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    enum class STATUS { LOADING, DONE, ERROR }

    private val database = getDatabase(app)
    private val repository = Repository(database)

    private val _status = MutableLiveData<STATUS>()
    val status: LiveData<STATUS>
        get() = _status

    private val _picOfTheDay = MutableLiveData<PictureOfDay>()
    val picOfTheDay: LiveData<PictureOfDay>
        get() = _picOfTheDay

    private val _navigate = MutableLiveData<Asteroid?>()
    val navigate: LiveData<Asteroid?>
        get() = _navigate

    init {
        _status.postValue(STATUS.LOADING)
        try{
            viewModelScope.launch {
                repository.refreshAsteroids()
                getPictureOfTheDay()
                _status.postValue(STATUS.DONE)
            }
        } catch (e : Exception){
            Log.i("Repository", "${e.message}")
            _status.postValue(STATUS.ERROR)
        }
    }

    var asteroids = repository.asteroids

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

    fun savedAsteroids(){
        viewModelScope.launch {
            asteroids = Transformations.map(
                database.dataBaseDao.getAllAsteroids()) {
                it.asDomainModel()
            }
        }
    }

    fun WeeksAsteroids(){
        viewModelScope.launch {
            asteroids = Transformations.map(
                database.dataBaseDao.getAsteroidsByCloseApproachDate(getToday(), getSeventhDay())) {
                it.asDomainModel()
            }
        }
    }

    fun TodaysAsteroids(){
        viewModelScope.launch {
            asteroids = Transformations.map(
                database.dataBaseDao.getAsteroidsByCloseApproachDate(getToday(), getToday())) {
                it.asDomainModel()
            }
        }
    }

}