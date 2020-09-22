package com.example.planboard.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.planboard.R
import com.example.planboard.ui.news.data.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private var articleList: ArrayList<Article>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val placeHolderImage = "https://pbs.twimg.com/profile_images/467502291415617536/SP8_ylk9.png"
    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: Article)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setNews(articles: ArrayList<Article>){
        articleList = articles
        notifyDataSetChanged()
    }
    inner class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article){
            with(itemView){
                tv_title_news.text = article.title
                tv_description_news.text = article.description
                if (article.urlToImage.isEmpty()) {
                    Picasso.get()
                        .load(placeHolderImage)
                        .centerCrop()
                        .fit()
                        .into(img_news)
                } else {
                    Picasso.get()
                        .load(article.urlToImage)
                        .centerCrop()
                        .fit()
                        .into(img_news)
                }
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(article) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}