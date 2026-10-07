/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.samples.browser.maxrefner

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.mozilla.samples.browser.R

/**
 * MaxRefner Browser - Global Refinery Network Dashboard Activity.
 * Provides a dark-mode optimized view showing real-time network node status,
 * traffic metrics, and refinery network health indicators.
 */
class MaxRefnerDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maxrefner_dashboard)

        val statusView = findViewById<TextView>(R.id.networkStatusText)
        statusView?.text = getString(R.string.maxrefner_network_status_active)
    }
}
