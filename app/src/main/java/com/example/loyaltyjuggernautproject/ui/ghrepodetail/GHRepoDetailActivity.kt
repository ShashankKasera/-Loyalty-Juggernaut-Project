package com.example.loyaltyjuggernautproject.ui.ghrepodetail

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.loyaltyjuggernautproject.R
import com.example.loyaltyjuggernautproject.core.EMPTY

class GHRepoDetailActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var loader: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_repo_detail)
        webView = findViewById(R.id.webView)
        loader = findViewById(R.id.loader)
        val urlToLoad = intent.getStringExtra(getString(R.string.repo_url)) ?: String.EMPTY

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        // Prevent redirecting to browser
        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                loader.visibility = View.VISIBLE
                webView.visibility = View.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loader.visibility = View.GONE
                webView.visibility = View.VISIBLE
            }
        }

        webView.loadUrl(urlToLoad)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}