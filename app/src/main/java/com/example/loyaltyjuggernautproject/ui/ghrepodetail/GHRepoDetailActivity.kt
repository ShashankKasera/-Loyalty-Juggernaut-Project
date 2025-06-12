package com.example.loyaltyjuggernautproject.ui.ghrepodetail

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.loyaltyjuggernautproject.R
import com.example.loyaltyjuggernautproject.databinding.ActivityRepoDetailBinding

class GHRepoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepoDetailBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@GHRepoDetailActivity
        }
        setContentView(binding.root)

        val urlToLoad = intent.getStringExtra(getString(R.string.repo_url)).orEmpty()

        setupWebView(urlToLoad)
    }

    private fun setupWebView(url: String) = with(binding.webView) {
        binding.progressBarVisibility = true

        settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }

        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBarVisibility = false
            }
        }

        loadUrl(url)
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
