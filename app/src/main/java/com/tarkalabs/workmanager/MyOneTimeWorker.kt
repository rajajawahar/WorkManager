package com.tarkalabs.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyOneTimeWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

  override fun doWork(): Result {
    for (i in 0..5000) {
      Log.d("MYTAG", "Number ${i}")
    }
    return Result.success()
  }
}