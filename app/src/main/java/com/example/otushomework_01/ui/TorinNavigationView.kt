package com.example.otushomework_01.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.drawerlayout.widget.DrawerLayout
import com.example.otushomework_01.R
import com.google.android.material.navigation.NavigationView
import kotlin.math.roundToInt
import kotlin.math.sqrt

class TorinDrawerLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : DrawerLayout(context, attrs, defStyleAttr) {

    private val torinFace = ContextCompat.getDrawable(context,
        R.drawable.torin_face
    )!!

    private val torinForeground = ContextCompat.getDrawable(context,
        R.drawable.torin_foreground
    )!!

    var drawer: NavigationView? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        children.forEach {
            if (it is NavigationView) {
                drawer = it
            }
        }
    }

    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
        val view = drawer
        val header = drawer?.getHeaderView(0)

        if (canvas != null && view != null && header != null && view.right > 0) {

            val w = header.width
            val h = header.height

            // Height of face the same as height of header, but width is shorter
            val faceW = torinFace.intrinsicWidth * h / torinFace.intrinsicHeight

            val xFinish = w / 2
            val xStart  = FACE_START_OFFSET * faceW

            // relative slide position [0 - 1]
            val progress = view.right.toFloat() / w

            val x = xStart + (xFinish - xStart) * sqrt(progress)
            val faceLeft = (x - 0.5f*faceW).roundToInt()
            val faceRight = faceLeft + faceW

            canvas.clipRect(view.left, header.top, view.right, header.bottom)
            torinFace.setBounds(faceLeft, header.top, faceRight, header.bottom)
            torinFace.draw(canvas)

            torinForeground.setBounds(view.left, header.top, view.right, header.bottom)
            torinForeground.draw(canvas)
        }
    }

    companion object {
        const val FACE_START_OFFSET = 0.5f
    }
}