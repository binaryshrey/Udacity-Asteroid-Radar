package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Repository(val databaseAsteroid: DatabaseAsteroid) {

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(
        databaseAsteroid.dataBaseDao.getAsteroids()
    ) {
        it.asDomainModel()
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroidDataString = APIService.retrofitService.getAsteroidData(
                    getToday(),
                    getSeventhDay(), Constants.API_KEY
                )
                Log.i("Repository", "asteroidDataString : ${asteroidDataString}")

                val asteroids =
                    AsteroidContainer(parseAsteroidsJsonResult(JSONObject(asteroidDataString))).asDatabaseModel()
                Log.i("Repository", "asteroids : ${asteroids}")

                databaseAsteroid.dataBaseDao.insertAll(*asteroids)
            } catch (e: Exception) {
                Log.i("Repository", "error : ${e.message}")
            }
        }
    }

}