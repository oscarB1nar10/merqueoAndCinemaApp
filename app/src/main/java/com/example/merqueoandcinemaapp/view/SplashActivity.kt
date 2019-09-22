package com.example.merqueoandcinemaapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.merqueoandcinemaapp.R
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initialSetUp()
    }

    private fun initialSetUp(){
        GlobalScope.launch(Dispatchers.Unconfined) {
            val job = launch {
                delay(3000L)
                withContext(Dispatchers.Main){
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)

                }
            }
            job.join()
        }
    }


}