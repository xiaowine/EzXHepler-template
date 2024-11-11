package com.example.template

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class CustomView(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    companion object {
        const val ICON_MARGIN = 32
        const val ICON_SIZE = 60
        const val ICON_TEXTVIEW_MARGIN = 16
        const val VACANCY_MARGIN = 16
    }

    val imageView: ImageView = createImageView(context)
    val textView: TextView = createTextView(context, "123456")
    val vacancyView: View = createVacancyView(context)
    val textView2: TextView = createTextView(context, "654321")
    val imageView2: ImageView = createImageView(context)

    init {
//        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        addView(imageView)
        addView(textView)
        addView(vacancyView)
        addView(textView2)
        addView(imageView2)
        background = GradientDrawable().apply {
            cornerRadius = 50f
            setColor(Color.BLACK)
        }
        setupInitialConstraints()
    }

    private fun setupInitialConstraints() {
        val constraintSet = ConstraintSet().apply {
            constrainWidth(imageView.id, ICON_SIZE)
            constrainHeight(imageView.id, ICON_SIZE)
            connect(imageView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, ICON_MARGIN)
            connect(imageView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, ICON_MARGIN)
            connect(imageView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(imageView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

            constrainWidth(textView.id, ConstraintSet.WRAP_CONTENT)
            constrainHeight(textView.id, ConstraintSet.WRAP_CONTENT)
            connect(textView.id, ConstraintSet.START, imageView.id, ConstraintSet.END, ICON_TEXTVIEW_MARGIN)
            connect(textView.id, ConstraintSet.END, vacancyView.id, ConstraintSet.START, VACANCY_MARGIN)
            connect(textView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(textView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

            constrainWidth(vacancyView.id, ConstraintSet.WRAP_CONTENT)
            constrainHeight(vacancyView.id, ConstraintSet.WRAP_CONTENT)
            connect(vacancyView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(vacancyView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            connect(vacancyView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(vacancyView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

            constrainWidth(textView2.id, ConstraintSet.WRAP_CONTENT)
            constrainHeight(textView2.id, ConstraintSet.WRAP_CONTENT)
            connect(textView2.id, ConstraintSet.START, vacancyView.id, ConstraintSet.END, VACANCY_MARGIN)
            connect(textView2.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(textView2.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

            constrainWidth(imageView2.id, ICON_SIZE)
            constrainHeight(imageView2.id, ICON_SIZE)
            connect(imageView2.id, ConstraintSet.START, textView2.id, ConstraintSet.END, ICON_TEXTVIEW_MARGIN)
            connect(imageView2.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, ICON_MARGIN)
            connect(imageView2.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(imageView2.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        }
        constraintSet.applyTo(this)
    }

    private fun createImageView(context: Context): ImageView {
        return ImageView(context).apply {
            id = View.generateViewId()
            setImageResource(android.R.drawable.ic_popup_reminder)
        }
    }

    private fun createTextView(context: Context, text: String): TextView {
        return TextView(context).apply {
            id = View.generateViewId()
            this.text = text
        }
    }

    private fun createVacancyView(context: Context): View {
        return View(context).apply {
            id = View.generateViewId()
        }
    }

    fun setVacancyViewWidth(width: Int) {
        val params = vacancyView.layoutParams as LayoutParams
        params.width = width
        vacancyView.layoutParams = params
    }
}