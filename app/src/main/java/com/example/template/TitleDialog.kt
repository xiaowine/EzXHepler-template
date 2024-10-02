package com.example.template

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.template.Tools.dp2px

@SuppressLint("InternalInsetResource", "DiscouragedApi")
class TitleDialog(context: Context) : Dialog(context) {

    private val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    private val statusBarHeight = if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
    private val maxWidth = context.resources.displayMetrics.widthPixels / 2 - 80 - statusBarHeight / 2
    private var isShowIng: Boolean = false

    private val textView: TextView by lazy {
        TextView(context).apply {
            ellipsize = TextUtils.TruncateAt.MARQUEE
            setSingleLine(true)
            marqueeRepeatLimit = -1
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            maxWidth = this@TitleDialog.maxWidth
        }
    }

    private val iconView: ImageView by lazy {
        ImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(dp2px(context, 15f), dp2px(context, 15f)).apply {
                setMargins(0, 0, 15, 0)
            }
        }
    }

    private val content: LinearLayout by lazy {
        LinearLayout(context).apply {
            addView(iconView)
            addView(textView)
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, statusBarHeight - 4).apply {
                setMargins(0, 2, 0, 2)
            }
            background = GradientDrawable().apply {
                cornerRadius = 50f
                setColor(Color.BLACK)
            }
            setPadding(40, 5, 40, 5)
        }
    }

    private val root: LinearLayout by lazy {
        LinearLayout(context).apply {
            addView(content)
            gravity = Gravity.CENTER
            visibility = View.GONE
            setOnClickListener {
                hideTitle()
                Log.i("TitleDialog", "aaaaa")
            }
        }
    }


    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(root)
        window?.apply {
            setBackgroundDrawable(null)
            val params = attributes
            params.apply {
                gravity = Gravity.CENTER or Gravity.TOP
                flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM or
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                format = PixelFormat.TRANSLUCENT
                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            attributes = params
        }
        show()
        val a = context.resources.getIdentifier("config_mainBuiltInDisplayCutout", "string", "android")
        Log.i("TitleDialog", context.resources.getString(a))
    }

    fun hideTitle() {
        isShowIng = false
        root.visibility = View.GONE
    }

    fun setTitle(title: String) {
        isShowIng = true
        root.visibility = View.VISIBLE
        textView.text = title
    }
}