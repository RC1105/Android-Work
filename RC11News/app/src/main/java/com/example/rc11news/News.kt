package com.example.rc11news

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

data class News (
    val title:String,
    var author:String,
    val url:String,
    val imageUrl:String,
    val link:String
        )