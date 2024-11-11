/*
 * StatusBarLyric
 * Copyright (C) 2021-2022 fkj@fkj233.cn
 * https://github.com/577fkj/StatusBarLyric
 *
 * This software is free opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either
 * version 3 of the License, or any later version and our eula as published
 * by 577fkj.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/577fkj/StatusBarLyric/blob/main/LICENSE>.
 */

package com.example.template

import android.util.Log

object LogTools {
    private const val maxLength = 4000
    private const val TAG = "AACBridge"
    private const val XP_TAG = "LSPosed-Bridge"
    private var outprint = false


    fun Any?.log(): Any? {
        if (!outprint) return this
        val content = if (this is Throwable) Log.getStackTraceString(this) else this.toString()
        if (content.length > maxLength) {
            val chunkCount = content.length / maxLength
            for (i in 0..chunkCount) {
                val max = 4000 * (i + 1)
                val value = if (max >= content.length) {
                    content.substring(maxLength * i)
                } else {
                    content.substring(maxLength * i, max)
                }
                Log.d(TAG, value)
                Log.d(XP_TAG, "$TAG:$value")
            }

        } else {
            Log.d(TAG, content)
            Log.d(XP_TAG, "$TAG:$content")
        }
        return this
    }

    fun init(out: Boolean) {
        outprint = out
    }
}
