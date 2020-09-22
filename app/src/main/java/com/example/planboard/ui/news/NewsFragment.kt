package com.example.planboard.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.planboard.R
import com.example.planboard.ui.news.data.Article
import com.example.planboard.ui.news.data.NewsData
import com.example.planboard.ui.news.endpoint.NewsEndpoint
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.item_news.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private val endPointUrl by lazy { "https://newsapi.org/v2/" }
    private lateinit var newsEndpoint: NewsEndpoint
    private lateinit var newsApiConfig: String
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var articleList: ArrayList<Article>
    //RxJava
    private lateinit var newsObservable: Observable<NewsData>
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleList = ArrayList()
        newsApiConfig = resources.getString(R.string.api_key)
        val retrofit: Retrofit = generateRetrofitBuilder()
        newsEndpoint = retrofit.create(NewsEndpoint::class.java)
        swipe_refresh.setOnRefreshListener { activity }
        swipe_refresh.setColorSchemeResources(R.color.colorAccent)
        compositeDisposable = CompositeDisposable()
    }

    override fun onStart() {
        super.onStart()
        queryNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onRefresh() {
        queryNews()
    }

    private fun showRecyclerview(){
        newsAdapter = NewsAdapter(articleList)
        newsAdapter.setNews(articleList)
        rv_berita.setHasFixedSize(true)
        rv_berita.layoutManager = LinearLayoutManager(activity)
        rv_berita.adapter = newsAdapter
        rv_berita.itemAnimator = DefaultItemAnimator()
        swipe_refresh.isRefreshing = false

        newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback{
            override fun onItemClicked(article: Article) {
                val bundle = Bundle()
                bundle.putString(NewsDetailFragment.newsUrl, article.url)
                findNavController().navigate(R.id.action_navigation_home_to_newsDetailFragment, bundle)
            }
        })
    }

    private fun generateRetrofitBuilder(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(endPointUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createObserver(): DisposableObserver<Article>{
        return object : DisposableObserver<Article>(){
            override fun onNext(t: Article) {
                if (!articleList.contains(t)){
                    articleList.add(t)
                }
            }
            override fun onError(e: Throwable) {
                Log.e("createArticleObserver", "Article error: ${e.message}")
                Toast.makeText(activity, "Network error", Toast.LENGTH_SHORT).show()
            }
            override fun onComplete() {
                showRecyclerview()
            }
        }
    }

    private fun subscribeObservable(){
        articleList.clear()
        compositeDisposable.add(
            newsObservable.subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    Observable.fromIterable(it.articles)
                }
                .subscribeWith(createObserver())
        )
    }

    private fun queryNews(){
        swipe_refresh.isRefreshing = true
        newsObservable = newsEndpoint.getTopHeadlines("id", "business", newsApiConfig)
        subscribeObservable()
    }
}