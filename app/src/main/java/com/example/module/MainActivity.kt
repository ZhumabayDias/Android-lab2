package com.example.module

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chatlibrary.ChatLauncher

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ChatLauncher.start(this)
        finish()
    }
}