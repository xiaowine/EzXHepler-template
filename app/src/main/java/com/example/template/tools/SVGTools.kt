package com.example.template.tools

object SVGTools {
    data class SvgDimensions(
        val width: Float,
        val height: Float,
        val depth: Float,
        val startX: Float,
        val startY: Float
    ) {
        override fun toString(): String {
            return "SvgDimensions(width=$width, height=$height, depth=$depth, startX=$startX, startY=$startY)"
        }
    }

    fun parseSvgPathData(pathData: String): SvgDimensions {
        val commands = pathData.split(" ")
        var minX = Float.MAX_VALUE
        var minY = Float.MAX_VALUE
        var maxX = Float.MIN_VALUE
        var maxY = Float.MIN_VALUE
        var startX = 0f
        var startY = 0f

        var i = 0
        while (i < commands.size) {
            when (commands[i]) {
                "M" -> {
                    val coords = commands[i + 1].split(",")
                    val x = coords[0].toFloat()
                    val y = coords[1].toFloat()
                    startX = x
                    startY = y
                    minX = minOf(minX, x)
                    minY = minOf(minY, y)
                    maxX = maxOf(maxX, x)
                    maxY = maxOf(maxY, y)
                    i += 2
                }

                "L" -> {
                    val coords = commands[i + 1].split(",")
                    val x = coords[0].toFloat()
                    val y = coords[1].toFloat()
                    minX = minOf(minX, x)
                    minY = minOf(minY, y)
                    maxX = maxOf(maxX, x)
                    maxY = maxOf(maxY, y)
                    i += 2
                }

                "H" -> {
                    val x = commands[i + 1].toFloat()
                    minX = minOf(minX, x)
                    maxX = maxOf(maxX, x)
                    i += 2
                }

                "V" -> {
                    val y = commands[i + 1].toFloat()
                    minY = minOf(minY, y)
                    maxY = maxOf(maxY, y)
                    i += 2
                }

                "Z" -> i += 1

                else -> i += 1
            }
        }

        val width = maxX - minX
        val height = maxY - minY
        val depth = 0f // Assuming depth is not applicable for 2D SVG paths

        return SvgDimensions(width, height, depth, startX, startY)
    }
}