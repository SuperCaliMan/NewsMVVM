package com.example.calinews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.text.TextPaint
import android.util.Log
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView



class SwipeHelper(
    private val onRightSwipe: (Any?) -> Unit = {},
    private var swipeRightText: String? = null,
    private var swipeRightActionIconId: Int = 0,
    private var swipeRightBackgroundColor: Int = 0,
    private var swipeRightTextSize: Float = 14f,
    private var swipeRightTextColor: Int = Color.WHITE,
    private var swipeRightFont: Typeface? = Typeface.DEFAULT,

    private val onLeftSwipe: (Any?) -> Unit = {},
    private var swipeLeftText: String? = null,
    private var swipeLeftActionIconId: Int = 0,
    private var swipeLeftBackgroundColor: Int = 0,
    private var swipeLeftTextSize: Float = 14f,
    private var swipeLeftTextColor: Int = Color.WHITE,
    private var swipeLeftFont: Typeface? = Typeface.DEFAULT,
    private var iconHorizontalMargin: Int = 16,
    private val context: Context
): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
    private var textUnit: Int = TypedValue.COMPLEX_UNIT_SP
    init {
       iconHorizontalMargin =  TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            iconHorizontalMargin.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }



    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
       // val mail = adapter.data[pos]
        when (direction) {
            ItemTouchHelper.RIGHT -> {
                //adapter.removeItem(pos)
                onRightSwipe(null)

            }
            ItemTouchHelper.LEFT -> {
                // adapter.removeItem(pos)
                onLeftSwipe(null)
            }
        }

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
        decorate(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }



    private fun decorate( canvas: Canvas,
                  recyclerView: RecyclerView,
                  viewHolder: RecyclerView.ViewHolder,
                  dX: Float,
                  dY: Float,
                  actionState: Int,
                  isCurrentlyActive: Boolean) {
        try {
            if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE) return
            if (dX > 0) {
                // Swiping Right
                canvas.clipRect(
                    viewHolder.itemView.left,
                    viewHolder.itemView.top,
                    viewHolder.itemView.left + dX.toInt(),
                    viewHolder.itemView.bottom
                )
                if (swipeRightBackgroundColor != 0) {
                    val background = ColorDrawable(swipeRightBackgroundColor)
                    background.setBounds(
                        viewHolder.itemView.left,
                        viewHolder.itemView.top,
                        viewHolder.itemView.left + dX.toInt(),
                        viewHolder.itemView.bottom
                    )
                    background.draw(canvas)
                }
                var iconSize = 0
                if (swipeRightActionIconId != 0 && dX > iconHorizontalMargin) {
                    val icon =
                        ContextCompat.getDrawable(recyclerView.context, swipeRightActionIconId)
                    if (icon != null) {
                        iconSize = icon.intrinsicHeight
                        val halfIcon = iconSize / 2
                        val top =
                            viewHolder.itemView.top + ((viewHolder.itemView.bottom - viewHolder.itemView.top) / 2 - halfIcon)
                        icon.setBounds(
                            viewHolder.itemView.left + iconHorizontalMargin,
                            top,
                            viewHolder.itemView.left + iconHorizontalMargin + icon.intrinsicWidth,
                            top + icon.intrinsicHeight
                        )
                        icon.draw(canvas)
                    }
                }
                if (swipeRightText != null && swipeRightText!!.length > 0 && dX > iconHorizontalMargin + iconSize) {
                    val textPaint = TextPaint()
                    textPaint.isAntiAlias = true
                    textPaint.textSize = TypedValue.applyDimension(
                        this.textUnit,
                        swipeRightTextSize,
                        recyclerView.context.resources.displayMetrics
                    )
                    textPaint.color = swipeRightTextColor
                    textPaint.typeface = swipeRightFont
                    val textTop =
                        (viewHolder.itemView.top + (viewHolder.itemView.bottom - viewHolder.itemView.top) / 2.0 + textPaint.textSize / 2).toInt()
                    canvas.drawText(
                        swipeRightText!!,
                        viewHolder.itemView.left + iconHorizontalMargin + iconSize + (if (iconSize > 0) iconHorizontalMargin / 2 else 0).toFloat(),
                        textTop.toFloat(),
                        textPaint
                    )
                }
            } else if (dX < 0) {
                // Swiping Left
                canvas.clipRect(
                    viewHolder.itemView.right + dX.toInt(),
                    viewHolder.itemView.top,
                    viewHolder.itemView.right,
                    viewHolder.itemView.bottom
                )

                if (swipeLeftBackgroundColor != 0) {
                    val background = ColorDrawable(swipeLeftBackgroundColor)
                    background.setBounds(
                        viewHolder.itemView.right + dX.toInt(),
                        viewHolder.itemView.top,
                        viewHolder.itemView.right,
                        viewHolder.itemView.bottom
                    )
                    background.draw(canvas)
                }

                var iconSize = 0
                var imgLeft = viewHolder.itemView.right
                if (swipeLeftActionIconId != 0 && dX < -iconHorizontalMargin) {
                    val icon =
                        ContextCompat.getDrawable(recyclerView.context, swipeLeftActionIconId)
                    if (icon != null) {
                        iconSize = icon.intrinsicHeight
                        val halfIcon = iconSize / 2
                        val top =
                            viewHolder.itemView.top + ((viewHolder.itemView.bottom - viewHolder.itemView.top) / 2 - halfIcon)
                        imgLeft = viewHolder.itemView.right - iconHorizontalMargin - halfIcon * 2
                        icon.setBounds(
                            imgLeft,
                            top,
                            viewHolder.itemView.right - iconHorizontalMargin,
                            top + icon.intrinsicHeight
                        )
                        icon.draw(canvas)
                    }
                }
                if (swipeLeftText != null && swipeLeftText!!.length > 0 && dX < -iconHorizontalMargin - iconSize) {
                    val textPaint = TextPaint()
                    textPaint.isAntiAlias = true
                    textPaint.textSize = TypedValue.applyDimension(
                        this.textUnit,
                        swipeLeftTextSize,
                        recyclerView.context.resources.displayMetrics
                    )
                    textPaint.color = swipeLeftTextColor
                    textPaint.typeface = swipeLeftFont
                    val width = textPaint.measureText(swipeLeftText)
                    val textTop =
                        (viewHolder.itemView.top + (viewHolder.itemView.bottom - viewHolder.itemView.top) / 2.0 + textPaint.textSize / 2).toInt()
                    canvas.drawText(
                        swipeLeftText!!,
                        imgLeft - width - if (imgLeft == viewHolder.itemView.right) iconHorizontalMargin else iconHorizontalMargin / 2,
                        textTop.toFloat(),
                        textPaint
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message)
        }
    }
}
