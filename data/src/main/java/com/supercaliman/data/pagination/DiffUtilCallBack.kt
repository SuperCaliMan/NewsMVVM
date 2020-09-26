package com.supercaliman.data.pagination

import androidx.recyclerview.widget.DiffUtil
import com.supercaliman.domain.model.NewsArticle

class DiffUtilCallBack:DiffUtil.ItemCallback<NewsArticle>() {
    override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem.content == newItem.content && oldItem.title == newItem.title
    }
}