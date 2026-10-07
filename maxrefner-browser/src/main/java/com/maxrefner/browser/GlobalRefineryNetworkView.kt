/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.maxrefner.browser

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Native Custom View for rendering the MaxRefner Global Refinery Network Map and Telemetry.
 */
class GlobalRefineryNetworkView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val nodePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#00FF88")
        style = Paint.Style.FILL
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#005533")
        strokeWidth = 3f
        style = Paint.Style.STROKE
    }

    private val nodes = listOf(
        Pair(0.2f, 0.3f),
        Pair(0.5f, 0.4f),
        Pair(0.8f, 0.6f),
        Pair(0.35f, 0.75f),
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.parseColor("#0D0D0D"))

        val w = width.toFloat()
        val h = height.toFloat()

        // Draw network connections
        for (i in 0 until nodes.size - 1) {
            val p1 = nodes[i]
            val p2 = nodes[i + 1]
            canvas.drawLine(p1.first * w, p1.second * h, p2.first * w, p2.second * h, linePaint)
        }

        // Draw network nodes
        for (node in nodes) {
            canvas.drawCircle(node.first * w, node.second * h, 14f, nodePaint)
        }
    }
}
