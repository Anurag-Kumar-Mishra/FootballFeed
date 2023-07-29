package com.example.footballfeed

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.authorView.text = currentItem.author
        holder.descriptionView.text = currentItem.description
        if (currentItem.imageLink=="null"){
            Glide.with(holder.itemView.context).load("https://editorial.uefa.com/resources/0262-10813cf2430c-0d77c0859dd0-1000/ucl-last-32-promo---outcome-2_20201001110146.jpg").into(holder.imageView)
        }
        else{
            Glide.with(holder.itemView.context).load(currentItem.imageLink).into(holder.imageView)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews: ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class  NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleView: TextView = itemView.findViewById(R.id.titleView)
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val authorView: TextView = itemView.findViewById(R.id.authorView)
    val descriptionView: TextView = itemView.findViewById(R.id.descriptionView)

}

interface NewsItemClicked{
    fun onItemClicked(item: News)
}