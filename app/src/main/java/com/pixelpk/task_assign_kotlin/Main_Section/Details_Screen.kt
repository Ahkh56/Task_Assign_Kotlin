package com.pixelpk.task_assign_kotlin.Main_Section

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.pixelpk.task_assign_kotlin.R

class Details_Screen : AppCompatActivity()
{
    //TextView to show details
    lateinit var date: TextView
    lateinit var title: TextView
    lateinit var caption: TextView
    lateinit var url: TextView

    //Strings to get details from
    lateinit var detail_str: String
    lateinit var img_str: String
    lateinit var title_str: String
    lateinit var date_str: String
    lateinit var url_str:  String

    //back button and image for article
    lateinit var img_view: ImageView
    lateinit var back_btn: ImageView

    //Caption Layout
    lateinit var layout_whole_caption: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_screen)

        initialize_view()

        Load_data()

        //On Click url view the whole post on web
        url.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url_str))
            startActivity(browserIntent)
        }

        //Going back to main screen
        back_btn.setOnClickListener { finish() }
    }

    private fun Load_data()
    {
        //Loading data in glide
        Glide.with(this@Details_Screen)
            .load(img_str)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(android.R.drawable.stat_notify_error)
            .into(img_view)

        //check if there is no caption removing caption layout
        if (detail_str == "") {
            layout_whole_caption.visibility = View.GONE
        } else {
            layout_whole_caption.visibility = View.VISIBLE
        }

        date.text = date_str
        title.text = title_str
        caption.text = detail_str
        url.text = url_str
    }

    private fun initialize_view()
    {
        //Getting Data from Activity

        //Getting Data from Activity
        detail_str = intent.getStringExtra("intent_article_details")!!
        img_str = intent.getStringExtra("intent_article_img")!!
        title_str = intent.getStringExtra("intent_article_title")!!
        date_str = intent.getStringExtra("intent_article_date")!!
        url_str = intent.getStringExtra("intent_article_url")!!

        //Initializing the Textviews and Imageviews

        //Initializing the Textviews and Imageviews
        date = findViewById(R.id.date_published_detail)
        title = findViewById(R.id.title_detail)
        caption = findViewById(R.id.caption_detail)
        url = findViewById(R.id.url_detail)
        img_view = findViewById(R.id.img_article)
        back_btn = findViewById(R.id.back_btn)
        layout_whole_caption = findViewById(R.id.layout_whole_caption)
    }
}