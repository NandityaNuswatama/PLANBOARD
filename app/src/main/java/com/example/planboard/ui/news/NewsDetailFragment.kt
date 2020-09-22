package com.example.planboard.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.planboard.R
import kotlinx.android.synthetic.main.fragment_news_detail.*

class NewsDetailFragment : Fragment() {
    companion object{
        var newsUrl = "url"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar2.visibility = View.VISIBLE
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView, url: String) {
                progressBar2.visibility = View.INVISIBLE
            }
        }

        if(arguments != null){
            val url = requireArguments().getString(newsUrl).toString()
            webView.loadUrl(url)
            Log.d("NEWS DETAIL", url)
        }
    }
}