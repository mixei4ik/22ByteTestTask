package com.example.a22bytetesttask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.a22bytetesttask.data.News
import com.example.a22bytetesttask.databinding.LayoutNewsItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.author == newItem.author &&
                    oldItem.content == newItem.content &&
                    oldItem.description == newItem.description &&
                    oldItem.publishedAt == newItem.publishedAt &&
                    oldItem.title == newItem.title &&
                    oldItem.url == newItem.url &&
                    oldItem.urlToImage == newItem.urlToImage
        }
    }

    private val diff = AsyncListDiffer(this, diffCallback)

    var news: List<News>
        get() = diff.currentList
        set(value) = diff.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutNewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = news[position]
        with(holder.binding) {
            newsTitle.text = newsItem.title
            newsContent.text = newsItem.content
            newsImage.load(newsItem.urlToImage) {
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
                crossfade(true)
            }
        }
    }

    override fun getItemCount(): Int = news.size

    class NewsViewHolder(
        val binding: LayoutNewsItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}