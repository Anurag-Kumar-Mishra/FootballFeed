package com.example.footballfeed

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.footballfeed.News
import com.example.footballfeed.NewsAdapter
import com.example.footballfeed.NewsItemClicked
import com.example.footballfeed.R

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = NewsAdapter(this)
        recyclerView.adapter = mAdapter
        fetchdata()

    }

    private fun fetchdata() {

        val url = "http://api.mediastack.com/v1/news?access_key=YOUR_MEDIASTACK_KEY&keywords=football&countries=br,gb,de,it&categories=sports"

        // Request a string response from the provided URL.
        val newsArray = ArrayList<News>()
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                val newsJsonArray = it.getJSONArray("data")
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("image"),
                        newsJsonObject.getString("description")
                    )

                    newsArray.add(news)

                }
                mAdapter.updateNews(newsArray)
            },
            {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    override fun onItemClicked(item: News) {

     val builder = CustomTabsIntent.Builder()
     val customTabsIntent = builder.build()
     customTabsIntent.launchUrl(this, Uri.parse(item.link))

    }

}
