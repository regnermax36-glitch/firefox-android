/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.maxrefner.browser

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity

/**
 * MaxRefner Browser - Main Native Android Browser Activity.
 * Combines minimalist Top Bar, Native WebView engine, Global Refinery Network Dashboard,
 * and Ergonomic Bottom Navigation Bar.
 */
class MaxRefnerBrowserActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var urlEditText: EditText
    private lateinit var viewFlipper: ViewFlipper

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maxrefner_browser)

        urlEditText = findViewById(R.id.urlEditText)
        webView = findViewById(R.id.webView)
        viewFlipper = findViewById(R.id.viewFlipper)

        val goButton = findViewById<ImageButton>(R.id.goButton)
        val navHome = findViewById<View>(R.id.navHome)
        val navDashboard = findViewById<View>(R.id.navDashboard)
        val navTabs = findViewById<View>(R.id.navTabs)
        val navSettings = findViewById<View>(R.id.navSettings)

        // Native WebView Configuration
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                url?.let { urlEditText.setText(it) }
            }
        }
        webView.webChromeClient = WebChromeClient()

        // Default Load - Refinery Home
        loadUrl("https://refinery.maxrefner.com")

        goButton.setOnClickListener {
            val query = urlEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                val targetUrl = if (query.startsWith("http://") || query.startsWith("https://")) {
                    query
                } else {
                    "https://www.google.com/search?q=$query"
                }
                loadUrl(targetUrl)
            }
        }

        // Bottom Navigation Bar Action Handlers
        navHome.setOnClickListener {
            viewFlipper.displayedChild = 0
            loadUrl("https://refinery.maxrefner.com")
        }

        navDashboard.setOnClickListener {
            viewFlipper.displayedChild = 1 // Display Global Refinery Network Dashboard
        }

        navTabs.setOnClickListener {
            viewFlipper.displayedChild = 0
        }

        navSettings.setOnClickListener {
            viewFlipper.displayedChild = 2 // Display Settings
        }
    }

    private fun loadUrl(url: String) {
        viewFlipper.displayedChild = 0
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        if (viewFlipper.displayedChild != 0) {
            viewFlipper.displayedChild = 0
        } else if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
