package com.wot.helper.ui.tankstats

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

class TankstatsFragment : Fragment() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ContentLoadingProgressBar
    private lateinit var loaderContainer: FrameLayout

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tankstats, container, false)

        webView = view.findViewById(R.id.webViewTankStats)
        progressBar = view.findViewById(R.id.progress_bar)
        loaderContainer = view.findViewById(R.id.loader_container)

        // Activăm JavaScript pentru compatibilitate
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.loadsImagesAutomatically = true
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT

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

        // Încărcăm site-ul în WebView
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://tomato.gg/tank-performance/recent/EU")

        return view
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
