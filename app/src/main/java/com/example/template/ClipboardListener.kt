package com.example.template

import android.content.ClipboardManager
import android.content.Context

class ClipboardListener(private val context: Context, unit: (String) -> Unit) {
    private val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    private var previousClip: String? = null

    init {
        clipboardManager.addPrimaryClipChangedListener {
            val clip = clipboardManager.primaryClip
            if (clip != null && clip.itemCount > 0) {
                val newClip = clip.getItemAt(0).coerceToText(context).toString()
                if (newClip != previousClip) {
                    previousClip = newClip
                    unit(newClip)
                }
            }
        }
    }
}
