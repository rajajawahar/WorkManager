package com.tarkalabs.workmanager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType.CONNECTED
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo.State
import androidx.work.WorkManager
import com.tarkalabs.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  lateinit var binding: ActivityMainBinding

  companion object {
    const val TEST_KEY = "KEY"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.btnTrigger.setOnClickListener {
      callMyFirstWorker()
    }
  }

  @SuppressLint("RestrictedApi")
  private fun callMyFirstWorker() {
    val stringMap = mutableMapOf<String, String>()
    stringMap[TEST_KEY] = "TEST"
    val data = Data(stringMap)
    val workManger = WorkManager.getInstance(this)
    val constraints = Constraints.Builder().setRequiredNetworkType(CONNECTED)
      .setRequiresCharging(true).build()
    val request =
      OneTimeWorkRequest.Builder(MyOneTimeWorker::class.java).setInputData(data)
        .setConstraints(constraints).build()

    workManger.enqueue(request)
    workManger.getWorkInfoByIdLiveData(request.id).observe(this) { observer ->
      if (observer.state == State.SUCCEEDED) {
        binding.tvReturnValue.setText(observer.outputData.getString("WORKER"))
      }
      binding.tvStatus.text = observer.state.name
    }
  }
}