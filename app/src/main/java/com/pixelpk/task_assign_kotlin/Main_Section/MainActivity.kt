package com.pixelpk.task_assign_kotlin.Main_Section

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pixelpk.task_assign_kotlin.R
import android.app.ProgressDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast

import com.pixelpk.task_assign_kotlin.Adapter.Adapter_Article

import com.pixelpk.task_assign_kotlin.Model.Model_Article

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.google.android.material.textfield.TextInputLayout
import com.pixelpk.task_assign_kotlin.Urls.Main_Url
import org.json.JSONException
import org.json.JSONObject





class MainActivity : AppCompatActivity()
{
    //Search for Popular Article
    lateinit var textInputLayout_search: TextInputLayout

    //Recycler for Popular Article
    lateinit var recyclerView_popular_article: RecyclerView

    //Model Classes (Array List)
    lateinit var model_articles: ArrayList<Model_Article>
    lateinit var searched_data_arraylist: ArrayList<Model_Article>


    //Adapter Popular Article
    lateinit var adapter_article: Adapter_Article

    lateinit var progressDialog: ProgressDialog

    private var img_str: String = ""

    //Putting Data in String
    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intialize_view()
        Load_data()

        //Search Field
        //Implementing text watcher

        textInputLayout_search.editText!!.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int)
            {
                //Iterator is used for item iteration in list
                var iterator = 0
                searched_data_arraylist.clear() // Clearing old data from the list

                if (!model_articles.isEmpty()) // Check if arraylist is not empty
                {
                    val searched_data = s.toString()

                    for (`object` in model_articles)   // Setting object from model artiicle arraylist
                    {
                        val type: String = `object`.title   // Adding title one by one to type(String)
                        if (type.contains(searched_data))   // Checking if searched data contains any letter that matches to the type
                        {
                            searched_data_arraylist.add(model_articles[iterator])  // After Filtering [Data] data is being added to new arrraylist
                        }
                      iterator++  // iterator incrementation
                    }

                    adapter_article = Adapter_Article(searched_data_arraylist, this@MainActivity)  //Passing the new list to adapter
                    adapter_article.notifyDataSetChanged()                 //notifing if anything changes in the list
                    recyclerView_popular_article.setHasFixedSize(true)     //So that adapter changes does not have any effect on recycler view size
                    recyclerView_popular_article.smoothScrollToPosition(0) //scrolling from position 0
                    recyclerView_popular_article.adapter = adapter_article  //adding adpter to recycler view
                }
            }

            override fun onTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int) {

                if (s.toString() == "")    // texts are removed loading all the data
                {
                    Load_data()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

    }

    // Getting popular article from Api
    private fun Load_data()
    {
        //Progress Dialogue
        progressDialog.setMessage("Please Wait while the Articles are being Loaded")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        //Adding Volley Get Request
        val stringRequest = StringRequest(Request.Method.GET, Main_Url.get_popular_article(),
            Response.Listener { response ->
                try
                {
                    //getting the whole json object from the response
                    val jObject = JSONObject(response)

                    //GEtting Status
                    val message = jObject.getString("status")

                    //Checking if the status is [OK]
                    if(message.equals("OK"))
                    {
                        progressDialog.dismiss()           //Dismissing Progress Dialogue
                        val array_articles = jObject.getJSONArray("results")  //Getting Result Array
                        for (i in 0 until array_articles.length())
                        {
                            val jsonObject = array_articles.getJSONObject(i)
                            val title_str = jsonObject.getString("title")        //Getting Title
                            val date_str  = jsonObject.getString("published_date")  //Getting Published Date
                            val url_str   = jsonObject.getString("url") // Getting Url for the whole ost

                            val array_media = jsonObject.getJSONArray("media") //Getting Media Array in result array

                            for (j in 0 until array_media.length())
                            {
                                val jsonObject_media = array_media.getJSONObject(j)
                                val details_str = jsonObject_media.getString("caption")  //Getting Caption

                                val array_media_meta = jsonObject_media.getJSONArray("media-metadata")  //Getting media meta data array in media array

                                for (k in 0 until array_media_meta.length())
                                {
                                    val jsonObject_media_meta = array_media_meta.getJSONObject(k)

                                    val val_format = jsonObject_media_meta.getString("format") //Getting Format of the media

                                    if (val_format == "mediumThreeByTwo210")         //Check for the large image size
                                    {
                                        img_str = jsonObject_media_meta.getString("url")  //Getting img url
                                    }

                                }
                                val model_article = Model_Article(
                                    img_str,             //Passing Imaage
                                    title_str,           //Passing title
                                    details_str,         //Passing detail
                                    date_str,            //Passing date
                                    url_str              //Passing url
                                )
                                model_articles.add(model_article)  //Adding the model clase data to arraylist
                                Log.e("tag_details","img_str"+" "+title_str+" "+details_str+" "+date_str+" "+url_str)
                            }

                            //Adding Recycler View
                            adapter_article = Adapter_Article(model_articles, this@MainActivity)
                            adapter_article.notifyDataSetChanged()
                            recyclerView_popular_article.setHasFixedSize(true)
                            recyclerView_popular_article.smoothScrollToPosition(0)
                            recyclerView_popular_article.adapter = adapter_article

                        }
                    }

                }

                catch (e: JSONException)
                {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("tag_details",e.toString())


                }
            },
            Response.ErrorListener { error ->
                //displaying the error in toast if occurrs
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            })

        // request queue
        val requestQueue = Volley.newRequestQueue(this)

        requestQueue.add(stringRequest)

    }

    private fun intialize_view()
    {
        //Initializing Values

        progressDialog = ProgressDialog(this)

        textInputLayout_search = findViewById(R.id.search_article_txtinput)
        recyclerView_popular_article = findViewById(R.id.popular_article_recycler)
        model_articles = ArrayList()
        searched_data_arraylist = ArrayList()
    }
}