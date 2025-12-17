package com.github.djrasmusp.oklchconverter

import com.github.djrasmusp.oklchconverter.services.ColorConversionService
import com.intellij.openapi.components.service
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class MyPluginTest : BasePlatformTestCase() {

    fun testColorConversionService() {
        val service = project.service<ColorConversionService>()

        val red = service.convert("#ff0000")
        assertEquals("oklch(0.63 0.26 29)", red)

        val rgba = service.convert("rgba(50, 100, 150, 0.5)")
        assertEquals("oklch(0.49 0.1 250 / 0.50)", rgba)

        val noAlphaShown = service.convert("#000999")
        assertEquals("oklch(0.31 0.21 264)", noAlphaShown)

        val zeroValues = service.convert("rgba(0,0,0,0.2)")
        assertEquals("oklch(0 0 0 / 0.20)", zeroValues)
    }
}