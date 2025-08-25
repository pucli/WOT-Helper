package com.wot.helper.ui.mapstats

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import com.wot.helper.R

class MapStatsFragment : Fragment() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ContentLoadingProgressBar
    private lateinit var loaderContainer: FrameLayout

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_mapstats, container, false)

        webView = root.findViewById(R.id.webViewMapStats)
        progressBar = root.findViewById(R.id.progress_bar)
        loaderContainer = root.findViewById(R.id.loader_container)

        // Setări WebView
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.loadsImagesAutomatically = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT

        // Loader ON când începe încărcarea și OFF când e gata
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                loaderContainer.visibility = View.VISIBLE
                progressBar.show()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.hide()
                loaderContainer.visibility = View.GONE
            }

            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url ?: "")
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }

        webView.webChromeClient = WebChromeClient()

        // Încărcăm pagina
        webView.loadUrl("https://tomato.gg/maps/EU")

        return root
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView.destroy()
    }
}
