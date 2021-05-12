package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.repository.Repository
import com.udacity.asteroidradar.database.getDatabase


class RefreshAsteoidsWorker(private val context: Context, params: WorkerParameters):
    CoroutineWorker(context,params){
    companion object{
        const val WORK_NAME = "RefreshAsteroidData"
    }
    override suspend fun doWork(): Result {
        val database = getDatabase(context)
        val repository = Repository(database)

        return try {
            repository.refreshAsteroids()
            Result.success()
        }catch (e:Exception){
            Result.retry()
        }
    }

}