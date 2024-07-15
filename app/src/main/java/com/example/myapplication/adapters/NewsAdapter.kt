package com.example.myapplication.com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.repositories.Article

class NewsAdapter(
    private val dataList: List<Article>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onButtonClick(title: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val newsTitle = itemView.findViewById<TextView>(R.id.newsTitle)
        val author = itemView.findViewById<TextView>(R.id.author)
        val publishedAt = itemView.findViewById<TextView>(R.id.publishedTime)
        val id = itemView.findViewById<TextView>(R.id.newsId)
        val imgViewer = itemView.findViewById<ImageView>(R.id.cardImage1)
        val moreButton=itemView.findViewById<ImageButton>(R.id.more)
        init {
            moreButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                if (v == moreButton) {
                    listener.onButtonClick(dataList[position].title)
                }
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.newsTitle.text = dataList[position].title
        holder.publishedAt.text = dataList[position].publishedAt
        holder.author.text = dataList[position].author
        holder.id.text = dataList[position].source.id
        Glide.with(holder.imgViewer.context)
            .load(dataList[position].urlToImage)
            .into(holder.imgViewer)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}

