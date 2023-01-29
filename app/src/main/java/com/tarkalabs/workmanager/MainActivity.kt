package com.tarkalabs.workmanager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo.State
import androidx.work.WorkManager
import com.tarkalabs.workmanager.databinding.ActivityMainBinding
import com.tarkalabs.workmanager.worker.MyOneTimeWorker1
import com.tarkalabs.workmanager.worker.MyOneTimeWorker2
import com.tarkalabs.workmanager.worker.MyOneTimeWorker3
import com.tarkalabs.workmanager.worker.MyOneTimeWorker4

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
    val workRequests = mutableListOf<OneTimeWorkRequest>()

    val request1 =
      OneTimeWorkRequest.Builder(MyOneTimeWorker1::class.java).setInputData(data).build()
    val request2 =
      OneTimeWorkRequest.Builder(MyOneTimeWorker2::class.java).setInputData(data).build()
    val request3 =
      OneTimeWorkRequest.Builder(MyOneTimeWorker3::class.java).setInputData(data).build()
    val request4 =
      OneTimeWorkRequest.Builder(MyOneTimeWorker4::class.java).setInputData(data).build()

    // workRequests.add(request1)
    // workRequests.add(request2)

    workManger.beginWith(request1)
      .then(request2)
      .then(request3)
      .then(request4).enqueue()

    workManger.getWorkInfoByIdLiveData(request1.id).observe(this) { observer ->
      if (observer.state == State.SUCCEEDED) {
        binding.tvReturnValue.setText(observer.outputData.getString("WORKER"))
      }
      binding.tvStatus.text = observer.state.name
    }
  }
}