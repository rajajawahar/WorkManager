package com.tarkalabs.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  private fun callMyFirstWorker() {
    val request = OneTimeWorkRequest.Builder(MyOneTimeWorker::class.java).build()
    WorkManager.getInstance(this).enqueue(request)
  }
}