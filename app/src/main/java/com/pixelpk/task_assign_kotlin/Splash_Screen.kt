package com.pixelpk.task_assign_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.pixelpk.task_assign_kotlin.Main_Section.MainActivity

class Splash_Screen : AppCompatActivity()
{
    private val SPLASH_TIME_OUT = 3000
    private var fade: Animation? = null
    private var slide:Animation? = null
    private var logo: ImageView? = null
    private var text_logo: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        intialize_view()

        //Setting Animation
        logo!!.animation = fade
        text_logo!!.animation = slide

        Handler().postDelayed({ //Calling new Activity
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

    private fun intialize_view()
    {
        //Initialize View
        logo = findViewById(R.id.splash_logo_img)
        text_logo = findViewById(R.id.textView_logo_splash)

        fade = AnimationUtils.loadAnimation(this, R.anim.fade_anim)
        slide = AnimationUtils.loadAnimation(this, R.anim.slide_anim)
    }
}