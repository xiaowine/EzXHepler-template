package com.example.template.hook.apps

import android.app.Application
import com.example.template.TitleDialog
import com.example.template.hook.BaseHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder

// Example hook
object ExampleHook : BaseHook() {
    override val name: String = "ExampleHook"

    override fun init() {
        getApplication {
            TitleDialog(it).setTitle("Hello, World!")
        }
    }

    private fun getApplication(callback: (Application) -> Unit) {
        var isLoad = false
        Application::class.java.methodFinder().filterByName("attach").first().createHook {
            after {
                if (isLoad) return@after
                isLoad = true
                callback(it.thisObject as Application)
            }
        }
    }
}