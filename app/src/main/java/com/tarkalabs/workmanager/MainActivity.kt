package com.tarkalabs.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType.CONNECTED
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
    val workManger = WorkManager.getInstance(this)
    val constraints = Constraints.Builder().setRequiredNetworkType(CONNECTED)
      .setRequiresCharging(true).build()
    val request =
      OneTimeWorkRequest.Builder(MyOneTimeWorker::class.java).setConstraints(constraints).build()
    workManger.enqueue(request)
    workManger.getWorkInfoByIdLiveData(request.id).observe(this) { observer ->
      binding.tvStatus.text = observer.state.name
    }
  }
}