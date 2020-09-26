package com.example.calinews.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.calinews.R
import com.supercaliman.data.pagination.DiffUtilCallBack
import com.supercaliman.domain.model.NewsArticle
import kotlinx.android.synthetic.main.row_news.view.*

class NewsAdapterPage:PagedListAdapter<NewsArticle,NewsAdapterPage.NewsViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_news, parent, false)
        return NewsViewHolder(view)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bind(news:NewsArticle){
            with(news){
                itemView.title.text = news.title
                itemView.description.text = news.description
                itemView.news_img.load(news.urlToImage){
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                }
            }
        }

    }
}