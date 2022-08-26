package com.example.rc11news

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private val listener:NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>(){
    private val items: ArrayList<News> =ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener {
            listener.onItemClick(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val curr=items[position]
        holder.tv.text=curr.title
        holder.sh.setOnClickListener { v->
            Snackbar.make(v,"Initialising Sharing...",Snackbar.LENGTH_LONG ).show()
            val intent= Intent(Intent.ACTION_SEND)
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"Check this out what I found on RC11 News:\n ${curr.link}")
            val chooser=Intent.createChooser(intent,"Share this news using")
            v.context.startActivity(chooser)
        }
        holder.au.text= "${holder.au.text}" +" ${curr.author}" + "  |  Date: ${curr.url.substring(0,11)} \n\n\n\n"
        if(curr.imageUrl!="null"){
            holder.cv.visibility=View.VISIBLE
            Glide.with(holder.itemView.context).load(curr.imageUrl).into(holder.iv)
        }
        else{
            holder.cv.visibility=View.GONE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged() //recalls above funs
    }
}
class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var tv: TextView = itemView.findViewById(R.id.title)
    var iv:ImageView =itemView.findViewById(R.id.iv_magic)
    var au: TextView = itemView.findViewById(R.id.auth)
    var sh: ImageView =itemView.findViewById(R.id.iv_share)

    var cv: CardView =itemView.findViewById(R.id.cv_magic)
}

interface NewsItemClicked{
    fun onItemClick(item:News)
}
