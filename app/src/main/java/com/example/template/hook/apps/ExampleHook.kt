package com.example.template.hook.apps

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import com.example.template.CustomView
import com.example.template.LogTools.log
import com.example.template.hook.BaseHook
import com.example.template.tools.SVGTools
import com.example.template.tools.Tools.getApplication
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder

@SuppressLint("StaticFieldLeak", "DiscouragedApi")
object ExampleHook : BaseHook() {
    override val name: String = "ExampleHook"
    private var isMove = false

    private lateinit var context: Context
    private val windowManager by lazy { context.getSystemService(WindowManager::class.java) as WindowManager }
    private val layoutParams by lazy { createLayoutParams() }
    private val customview by lazy { CustomView(context) }
    private val cutout by lazy { context.resources.getString(context.resources.getIdentifier("config_mainBuiltInDisplayCutout", "string", "android")) }
    private val svgPathData by lazy { SVGTools.parseSvgPathData(cutout) }

    override fun init() {
        return
        loadClass("com.android.systemui.statusbar.phone.PhoneStatusBarView").methodFinder().filterByName("onTouchEvent").first().createHook {
            before { hookParam ->
                handleTouchEvent(hookParam.args[0] as MotionEvent)
            }
        }
        getApplication {
            context = it
            customview.apply {
                setVacancyViewWidth(svgPathData.width.toInt())
            }
            windowManager.addView(customview, layoutParams)
        }
    }

    private fun createLayoutParams(): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.CENTER
            height = svgPathData.height.toInt() - 20
            y = 10
            x = 0
        }
        return params
    }


    private fun handleTouchEvent(motionEvent: MotionEvent) {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                isMove = false
            }

            MotionEvent.ACTION_UP -> {
                if (isMove) {
                    isMove = false
                    return
                }
                val location = IntArray(2)
                customview.getLocationOnScreen(location)
                val x = location[0]
                val y = location[1]
                val width = customview.width
                val height = customview.height
                val isInside = motionEvent.rawX.toInt() in x..(x + width) && motionEvent.rawY.toInt() in y..(y + height)
                if (isInside) {
                    "Touch is inside linearLayout".log()
                } else {
                    "Touch is outside linearLayout".log()
                }
            }

            MotionEvent.ACTION_MOVE -> {
                isMove = true
            }
        }
    }
}