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

        val dragView: FrameLayout = findViewById(R.id.drag_view)
        val imageView: ImageView = findViewById(R.id.image_view)
        val fl1: FrameLayout = findViewById(R.id.fl_1)

        fl1.setOnDragListener(dragListener)
        dragView.setOnDragListener(dragListener)

        imageView.setOnLongClickListener {

            val clipText = "this our text"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            it.visibility = View.VISIBLE
            true
        }
    }

    private val dragListener = View.OnDragListener { v, e ->
        when (e.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                e.clipDescription.hasMimeType((ClipDescription.MIMETYPE_TEXT_PLAIN))
                true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                v.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                v.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                val item = e.clipData.getItemAt(0)
                val dragData = item.text
                Toast.makeText(this, dragData, Toast.LENGTH_SHORT).show()

                v.invalidate()


                layoutInflater.inflate(R.layout.activity_main, null)

                val view = e.localState as View
                val owner = view.parent as ViewGroup
                owner.removeView(view)


                val destination = v as FrameLayout
                destination.addView(v)

                v.visibility = View.VISIBLE

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