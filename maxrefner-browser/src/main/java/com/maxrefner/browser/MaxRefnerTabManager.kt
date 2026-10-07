/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.maxrefner.browser

data class MaxRefnerTab(
    val id: String,
    val title: String,
    val url: String,
    val isPrivate: Boolean = false,
)

/**
 * Manages active browser tabs, switching, creation, and tab tray activities.
 */
class MaxRefnerTabManager {

    private val activeTabs = mutableListOf<MaxRefnerTab>()
    var selectedTabId: String? = null
        private set

    init {
        // Create initial default tab
        createNewTab("Global Refinery Network", "https://refinery.maxrefner.com")
    }

    fun createNewTab(title: String, url: String, isPrivate: Boolean = false): MaxRefnerTab {
        val tabId = System.currentTimeMillis().toString()
        val newTab = MaxRefnerTab(tabId, title, url, isPrivate)
        activeTabs.add(newTab)
        selectedTabId = tabId
        return newTab
    }

    fun selectTab(id: String) {
        if (activeTabs.any { it.id == id }) {
            selectedTabId = id
        }
    }

    fun closeTab(id: String) {
        activeTabs.removeAll { it.id == id }
        if (selectedTabId == id) {
            selectedTabId = activeTabs.lastOrNull()?.id
        }
    }

    fun getTabs(): List<MaxRefnerTab> = activeTabs.toList()

    fun getSelectedTab(): MaxRefnerTab? = activeTabs.find { it.id == selectedTabId }
}
