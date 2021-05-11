package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.APIService
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception
import kotlin.reflect.typeOf

class MainViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status : LiveData<String>
        get() = _status

    private val _property = MutableLiveData<ArrayList<Asteroid>>()
    val property : LiveData<ArrayList<Asteroid>>
        get() = _property

    private val _picOfTheDay = MutableLiveData<PictureOfDay>()
    val picOfTheDay : LiveData<PictureOfDay>
        get() = _picOfTheDay


    init {
        getAsteroidDataFromService()
    }

    private fun getAsteroidDataFromService() {
        viewModelScope.launch {
            try{
                _property.value = parseAsteroidsJsonResult(JSONObject(APIService.retrofitService.getAsteroidData(getToday(), getSeventhDay(),Constants.API_KEY)))
                _picOfTheDay.value = APIService.retrofitService.getPictureOfTheDay()
                Log.i("MainViewModel","data : ${_property.value}")
                Log.i("MainViewModel","data : ${_picOfTheDay.value}")

            }
            catch (e : Exception){
                Log.i("MainViewModel","Error : ${e.message}")
            }
        }
    }

}