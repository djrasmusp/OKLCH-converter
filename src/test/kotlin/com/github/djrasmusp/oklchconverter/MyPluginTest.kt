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

    fun testConvertFileWithMultipleColors() {
        val service = project.service<ColorConversionService>()

        // First verify individual conversions
        val red = service.convert("#ff0000")
        val green = service.convert("rgb(0, 255, 0)")
        val blue = service.convert("rgba(0, 0, 255, 0.5)")

        val fileContent = """
            .my-class {
                background: #ff0000;
                color: rgb(0, 255, 0);
                border: rgba(0, 0, 255, 0.5);
            }
        """.trimIndent()

        val result = service.convertFile(fileContent)

        assertEquals(3, result.replacements.size)
        assertTrue(result.convertedContent.contains(red))
        assertTrue(result.convertedContent.contains(green))
        assertTrue(result.convertedContent.contains(blue))
        assertTrue(result.errors.isEmpty())
    }

    fun testConvertFileWithHexVariants() {
        val service = project.service<ColorConversionService>()

        val fileContent = """
            .colors {
                short: #f00;
                long: #ff0000;
                short-alpha: #f00f;
                long-alpha: #ff0000ff;
            }
        """.trimIndent()

        val result = service.convertFile(fileContent)

        assertEquals(4, result.replacements.size)
        assertTrue(result.convertedContent.contains("oklch(0.63 0.26 29)"))
        assertTrue(result.errors.isEmpty())
    }

    fun testConvertFileWithNoColors() {
        val service = project.service<ColorConversionService>()

        val fileContent = """
            .my-class {
                background: none;
                color: inherit;
            }
        """.trimIndent()

        val result = service.convertFile(fileContent)

        assertEquals(0, result.replacements.size)
        assertEquals(fileContent, result.convertedContent)
        assertTrue(result.errors.isEmpty())
    }

    fun testConvertFileWithMixedContent() {
        val service = project.service<ColorConversionService>()

        val fileContent = """
            /* CSS file with colors */
            .header {
                background: #ffffff;
                text-color: rgb(34, 34, 34);
            }
            
            .footer {
                border: rgba(128, 128, 128, 0.8);
            }
            
            // Some comment with #notacolor
            .other {
                margin: 10px;
            }
        """.trimIndent()

        val result = service.convertFile(fileContent)

        assertEquals(3, result.replacements.size)
        assertTrue(result.convertedContent.contains("oklch("))
        assertTrue(result.convertedContent.contains("#notacolor")) // Should not be converted
        assertTrue(result.errors.isEmpty())
    }

    fun testConvertFilePreservesStructure() {
        val service = project.service<ColorConversionService>()

        val fileContent = """
            .container {
                padding: 20px;
                background: #e0e0e0;
            }
            
            .button {
                color: rgb(255, 255, 255);
                background: rgba(0, 123, 255, 1);
            }
        """.trimIndent()

        val result = service.convertFile(fileContent)

        // Check that structure is preserved
        assertTrue(result.convertedContent.contains(".container {"))
        assertTrue(result.convertedContent.contains(".button {"))
        assertTrue(result.convertedContent.contains("padding: 20px;"))
        assertEquals(3, result.replacements.size)
    }
}