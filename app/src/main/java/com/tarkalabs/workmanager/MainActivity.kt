package com.tarkalabs.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.tarkalabs.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.btnTrigger.setOnClickListener {
      callMyFirstWorker()
    }
  }

  private fun callMyFirstWorker() {
    val request =
      OneTimeWorkRequest.Builder(MyOneTimeWorker::class.java).build()
    WorkManager.getInstance(this).enqueue(request)
  }
}