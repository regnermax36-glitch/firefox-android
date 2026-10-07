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
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity

/**
 * MaxRefner Browser - Main Native Android Browser Activity.
 * Supports Multi-tab management, Top URL bar, Global Refinery Network Dashboard,
 * and Ergonomic Bottom Navigation Bar.
 */
class MaxRefnerBrowserActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var urlEditText: EditText
    private lateinit var viewFlipper: ViewFlipper
    private val tabManager = MaxRefnerTabManager()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maxrefner_browser)

        urlEditText = findViewById(R.id.urlEditText)
        webView = findViewById(R.id.webView)
        viewFlipper = findViewById(R.id.viewFlipper)

        val goButton = findViewById<ImageButton>(R.id.goButton)
        val newTabButton = findViewById<ImageButton>(R.id.newTabButton)
        val navHome = findViewById<View>(R.id.navHome)
        val navDashboard = findViewById<View>(R.id.navDashboard)
        val navTabs = findViewById<View>(R.id.navTabs)
        val navSettings = findViewById<View>(R.id.navSettings)

        // Native WebView Configuration with security & performance settings
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                url?.let { urlEditText.setText(it) }
            }
        }
        webView.webChromeClient = WebChromeClient()

        // Load active tab URL
        tabManager.getSelectedTab()?.url?.let { loadUrl(it) }

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

        newTabButton?.setOnClickListener {
            val newTab = tabManager.createNewTab("New Tab", "https://refinery.maxrefner.com")
            loadUrl(newTab.url)
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
            val tabsCountView = findViewById<TextView>(R.id.activeTabsCountText)
            tabsCountView?.text = "Active Tabs: ${tabManager.getTabs().size}"
            viewFlipper.displayedChild = 3 // Display Tabs Tray
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
