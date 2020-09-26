package com.example.calinews.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.calinews.R
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.supercaliman.domain.model.NewsArticle

class NewsAdapter():RecyclerView.Adapter<NewsAdapter.NewsArticleViewHolder>(){
    lateinit var context:Context

    var data: List<NewsArticle> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context;
        return NewsArticleViewHolder(
            inflater,
            parent
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class NewsArticleViewHolder(inflater: LayoutInflater,parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_news,parent,false)){

        var mTitleView: TextView = itemView.findViewById(R.id.title)
        var mDetail: TextView = itemView.findViewById(R.id.description)
        var imgView: ImageView = itemView.findViewById(R.id.news_img)


        fun bind(article: NewsArticle) {
            mTitleView.text = article.title
            mDetail.text = article.description
            imgView.load(article.urlToImage){
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
            }
        }
    }
}