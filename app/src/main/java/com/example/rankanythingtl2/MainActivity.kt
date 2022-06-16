package com.example.rankanythingtl2

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.DragEvent.ACTION_DROP
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.DragStartHelper
import androidx.core.view.ancestors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dragContainer: FrameLayout = findViewById(R.id.drag_container)
        val imageView: ImageView = findViewById(R.id.image_view)

        dragContainer.setOnDragListener(dragListener)

        imageView.setOnLongClickListener {


            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(null, dragShadowBuilder, it, 0)
            it.visibility = View.INVISIBLE
            true
        }
    }

    private val dragListener = View.OnDragListener { v, e ->

        val view = e.localState as View

        when (e.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                v.alpha = 0.3f
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                v.alpha = 1.0f
                true
            }
            DragEvent.ACTION_DROP -> {


                val owner = view.parent as ViewGroup
                owner.removeView(view)

                val destination = v as FrameLayout
                destination.addView(view)

                v.visibility = View.VISIBLE
                view.visibility = View.VISIBLE
                v.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                v.invalidate()
                when (e.result) {
                    true ->
                        Toast.makeText(this, "The drop was handled.", Toast.LENGTH_LONG)
                    else ->
                        Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_LONG)
                }.show()
                true
            }
            else -> false
        }
    }


}