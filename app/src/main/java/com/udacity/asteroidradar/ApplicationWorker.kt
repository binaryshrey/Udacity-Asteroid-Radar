package com.udacity.asteroidradar

import android.app.Application
import androidx.work.*
import com.udacity.asteroidradar.work.RefreshAsteoidsWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ApplicationWorker : Application(){
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayInit()
    }
    private fun delayInit(){
        applicationScope.launch {
            setUpRecurringWork()
        }
    }
    private  fun setUpRecurringWork(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()
        val repeatingWork = PeriodicWorkRequestBuilder<RefreshAsteoidsWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()


        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshAsteoidsWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingWork
        )
    }
}

