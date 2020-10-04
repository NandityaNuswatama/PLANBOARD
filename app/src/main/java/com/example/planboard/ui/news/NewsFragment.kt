package com.example.planboard.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.size
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
import com.jaredrummler.materialspinner.MaterialSpinner
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class NewsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, MaterialSpinner.OnItemSelectedListener<String> {
    private val endPointUrl by lazy { "https://newsapi.org/v2/" }
    private lateinit var newsEndpoint: NewsEndpoint
    private lateinit var newsApiConfig: String
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var articleList: ArrayList<Article>

    //RxJava
    private lateinit var newsObservable: Observable<NewsData>
    private lateinit var compositeDisposable: CompositeDisposable

    var itemSelected = ""

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

        setSpinner()
    }

    override fun onStart() {
        super.onStart()
        when (itemSelected) {
            "Bisnis" -> queryNews("business")
            "Kesehatan" -> queryNews("health")
            "Teknologi" -> queryNews("technology")
            "Hiburan" -> queryNews("entertainment")
            "Olahraga" -> queryNews("sports")
            "Pengetahuan" -> queryNews("science")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    private fun showRecyclerview() {
        news_lottie.visibility = View.GONE
        newsAdapter = NewsAdapter(articleList)
        newsAdapter.setNews(articleList)
        rv_berita.layoutManager = LinearLayoutManager(activity)
        rv_berita.adapter = newsAdapter
        rv_berita.itemAnimator = DefaultItemAnimator()
        swipe_refresh.isRefreshing = false

        newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Article) {
                val bundle = Bundle()
                bundle.putString(NewsDetailFragment.newsUrl, data.url)
                findNavController().navigate(
                    R.id.action_navigation_home_to_newsDetailFragment,
                    bundle
                )
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
                Timber.e("createArticleObserver, Article error: ${e.message}")
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

    private fun queryNews(category: String){
        swipe_refresh.isRefreshing = true
        newsObservable = newsEndpoint.getTopHeadlines("id", category, newsApiConfig)
        subscribeObservable()
    }

    private fun setSpinner(){
        newsSpinner.setItems("Bisnis", "Kesehatan", "Teknologi", "Hiburan", "Olahraga", "Pengetahuan")
        newsSpinner.setOnItemSelectedListener(this)
    }

    override fun onItemSelected(view: MaterialSpinner?, position: Int, id: Long, item: String) {
        itemSelected = item
        when(item){
            "Bisnis" -> queryNews("business")
            "Kesehatan" -> queryNews("health")
            "Teknologi" -> queryNews("technology")
            "Hiburan" -> queryNews("entertainment")
            "Olahraga" -> queryNews("sports")
            "Pengetahuan" -> queryNews("science")
        }
    }

    override fun onRefresh() {
        when(itemSelected){
            "Bisnis" -> queryNews("business")
            "Kesehatan" -> queryNews("health")
            "Teknologi" -> queryNews("technology")
            "Hiburan" -> queryNews("entertainment")
            "Olahraga" -> queryNews("sports")
            "Pengetahuan" -> queryNews("science")
        }
    }
}