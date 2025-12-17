package com.github.djrasmusp.oklchconverter.converter

import java.lang.IllegalArgumentException
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cbrt
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Converts common color representations (hex, rgb, rgba) to OKLCH.
 */
class ColorConverter {

    data class Rgba(val r: Double, val g: Double, val b: Double, val alpha: Double)

    data class Oklch(val l: Double, val c: Double, val h: Double, val alpha: Double)

    data class ConversionResult(val input: Rgba, val oklch: Oklch, val formatted: String)

    fun convert(input: String): ConversionResult {
        val rgba = parseColor(input)
        val oklch = toOklch(rgba)
        return ConversionResult(rgba, oklch, format(oklch))
    }

    private fun parseColor(raw: String): Rgba {
        val value = raw.trim()
        return when {
            value.matches(HEX_PATTERN) -> parseHex(value)
            value.matches(RGB_PATTERN) -> parseRgb(value)
            value.matches(RGBA_PATTERN) -> parseRgba(value)
            else -> throw IllegalArgumentException("Unsupported color. Use hex, rgb(), or rgba().")
        }
    }

    private fun parseHex(value: String): Rgba {
        val hex = value.removePrefix("#")
        val (rgb, alpha) = when (hex.length) {
            3 -> hex.map { "$it$it" }.joinToString("") to 255
            4 -> {
                val expanded = hex.map { "$it$it" }.joinToString("")
                expanded.take(6) to expanded.substring(6, 8).toInt(16)
            }
            6 -> hex to 255
            8 -> hex.substring(0, 6) to hex.substring(6, 8).toInt(16)
            else -> throw IllegalArgumentException("Invalid hex color.")
        }

        val r = rgb.substring(0, 2).toInt(16)
        val g = rgb.substring(2, 4).toInt(16)
        val b = rgb.substring(4, 6).toInt(16)
        return Rgba(r.toDouble(), g.toDouble(), b.toDouble(), alpha / 255.0)
    }

    private fun parseRgb(value: String): Rgba {
        val groups = RGB_PATTERN.find(value)?.groupValues ?: throw IllegalArgumentException("Invalid rgb().")
        return Rgba(groups[1].toChannel(), groups[2].toChannel(), groups[3].toChannel(), 1.0)
    }

    private fun parseRgba(value: String): Rgba {
        val groups = RGBA_PATTERN.find(value)?.groupValues ?: throw IllegalArgumentException("Invalid rgba().")
        val alpha = groups[4].toDoubleOrNull() ?: throw IllegalArgumentException("Alpha must be a number.")
        return Rgba(groups[1].toChannel(), groups[2].toChannel(), groups[3].toChannel(), alpha.coerceIn(0.0, 1.0))
    }

    private fun String.toChannel(): Double {
        val value = toIntOrNull() ?: throw IllegalArgumentException("RGB channel must be 0-255.")
        require(value in 0..255) { "RGB channel must be 0-255." }
        return value.toDouble()
    }

    private fun toOklch(rgba: Rgba): Oklch {
        val r = srgbToLinear(rgba.r / 255.0)
        val g = srgbToLinear(rgba.g / 255.0)
        val b = srgbToLinear(rgba.b / 255.0)

        val l = 0.4122214708 * r + 0.5363325363 * g + 0.0514459929 * b
        val m = 0.2119034982 * r + 0.6806995451 * g + 0.1073969566 * b
        val s = 0.0883024619 * r + 0.2817188376 * g + 0.6299787005 * b

        val lRoot = cbrt(l)
        val mRoot = cbrt(m)
        val sRoot = cbrt(s)

        val lOk = 0.2104542553 * lRoot + 0.7936177850 * mRoot - 0.0040720468 * sRoot
        val aOk = 1.9779984951 * lRoot - 2.4285922050 * mRoot + 0.4505937099 * sRoot
        val bOk = 0.0259040371 * lRoot + 0.7827717662 * mRoot - 0.8086757660 * sRoot

        val chroma = sqrt(aOk * aOk + bOk * bOk)
        val hue = (Math.toDegrees(atan2(bOk, aOk)) + 360) % 360

        return Oklch(lOk, chroma, hue, rgba.alpha)
    }

    private fun srgbToLinear(channel: Double): Double {
        return if (channel <= 0.04045) channel / 12.92 else ((channel + 0.055) / 1.055).pow(2.4)
    }

    private fun format(oklch: Oklch): String {
        val l = oklch.l.format()
        val c = oklch.c.format()
        val h = oklch.h.format()
        val alpha = oklch.alpha.format()
        return "oklch($l $c $h / $alpha)"
    }

    private fun Double.format(): String = String.format(Locale.US, "%.4f", this)

    companion object {
        private val HEX_PATTERN = Regex("^#([0-9a-fA-F]{3}|[0-9a-fA-F]{4}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})$")
        private val RGB_PATTERN = Regex("^rgb\\s*\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)$", RegexOption.IGNORE_CASE)
        private val RGBA_PATTERN = Regex("^rgba\\s*\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*([0-1](?:\\.\\d+)?)\\s*\\)$", RegexOption.IGNORE_CASE)
    }
}

