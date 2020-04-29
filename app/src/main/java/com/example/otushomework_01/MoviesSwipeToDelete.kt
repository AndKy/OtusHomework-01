package com.example.otushomework_01

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

abstract class MoviesSwipeToDelete(context: Context?)
    : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val icon = ContextCompat.getDrawable(context!!, R.drawable.ic_delete_forever_black_24dp)!!

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        // Dont allow slide button item
        if (viewHolder is ButtonsItemViewHolder)
            return 0

        // Dont allow slide not selected items
        if (viewHolder is MovieItemViewHolder) {
            val movie = viewHolder.movie
            if (movie != null)
                if(!movie.isSelected)
                    return 0
        }
        return super.getMovementFlags(recyclerView, viewHolder)
    }


    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val slideWidth = abs(dX.toInt())
        val iconSize = Math.max(Math.min(itemView.height, slideWidth), icon.intrinsicWidth)

        // Calculate position of delete icon
        val deleteIconTop = itemView.top + (itemView.height - iconSize) / 2
        val deleteIconBottom = deleteIconTop + iconSize

        var deleteIconLeft = itemView.right - iconSize
        var deleteIconRight = itemView.right

        if (slideWidth > iconSize) {
            deleteIconLeft = Math.max(itemView.left + (itemView.width - iconSize) / 2, itemView.right + dX.toInt())
            deleteIconRight = deleteIconLeft + iconSize
        }

        // Draw the delete icon
        icon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        icon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}