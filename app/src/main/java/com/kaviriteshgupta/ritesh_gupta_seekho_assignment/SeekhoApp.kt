package com.kaviriteshgupta.ritesh_gupta_seekho_assignment

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.work.SyncScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SeekhoApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory


    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    override fun onCreate() {
        super.onCreate()

        SyncScheduler(applicationContext).schedulePeriodicSync()
        Log.d("cece", "onCreate: schedulePeriodicSync")
    }


}