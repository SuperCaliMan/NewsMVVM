package com.example.calinews.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calinews.R
import com.example.calinews.domain.model.NewsArticle
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

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
        holder.bind(data[position],context)
    }

    class NewsArticleViewHolder(inflater: LayoutInflater,parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_news,parent,false)){

        var mTitleView: TextView = itemView.findViewById(R.id.title)
        var mDetail: TextView = itemView.findViewById(R.id.description)
        var imgView: ImageView = itemView.findViewById(R.id.news_img)


        fun bind(article: NewsArticle, context: Context) {
            mTitleView.text = article.title
            mDetail.text = article.description
            val builder = Picasso.Builder(context)
            builder.downloader(OkHttp3Downloader(context))
            builder.build().load(article.urlToImage)
               // .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imgView)
        }
    }
}