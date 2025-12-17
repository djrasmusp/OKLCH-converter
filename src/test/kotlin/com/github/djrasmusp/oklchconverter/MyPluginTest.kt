package com.github.djrasmusp.oklchconverter

import com.intellij.ide.highlighter.XmlFileType
import com.intellij.openapi.components.service
import com.intellij.psi.xml.XmlFile
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.PsiErrorElementUtil
import com.github.djrasmusp.oklchconverter.services.ColorConversionService

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class MyPluginTest : BasePlatformTestCase() {

    fun testXMLFile() {
        val psiFile = myFixture.configureByText(XmlFileType.INSTANCE, "<foo>bar</foo>")
        val xmlFile = assertInstanceOf(psiFile, XmlFile::class.java)

        assertFalse(PsiErrorElementUtil.hasErrors(project, xmlFile.virtualFile))

        assertNotNull(xmlFile.rootTag)

        xmlFile.rootTag?.let {
            assertEquals("foo", it.name)
            assertEquals("bar", it.value.text)
        }
    }

    fun testRename() {
        myFixture.testRename("foo.xml", "foo_after.xml", "a2")
    }

    fun testColorConversionService() {
        val service = project.service<ColorConversionService>()

        val red = service.convert("#ff0000")
        assertEquals("oklch(0.6280 0.2577 29.2339 / 1.0000)", red)

        val rgba = service.convert("rgba(50, 100, 150, 0.5)")
        assertEquals("oklch(0.4924 0.0971 250.4235 / 0.5000)", rgba)
    }

    override fun getTestDataPath() = "src/test/testData/rename"
}
