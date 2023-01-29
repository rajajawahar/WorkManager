package com.tarkalabs.workmanager.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tarkalabs.workmanager.MainActivity

class MyOneTimeWorker3(context: Context, params: WorkerParameters) : Worker(context, params) {

  @SuppressLint("RestrictedApi") override fun doWork(): Result {
    val inputValue = inputData.keyValueMap.get(MainActivity.TEST_KEY) as String
    Log.d("TAG", inputValue)
    val sendingData = Data(mapOf("WORKER" to "WORKERVALUE"))
    return Result.success(sendingData)
  }
}