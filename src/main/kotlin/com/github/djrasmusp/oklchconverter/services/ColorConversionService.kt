package com.github.djrasmusp.oklchconverter.services

import com.github.djrasmusp.oklchconverter.converter.ColorConverter
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class ColorConversionService(project: Project) {

    private val logger = Logger.getInstance(ColorConversionService::class.java)
    private val converter = ColorConverter()

    init {
        logger.info("Initialized OKLCH converter for project \"${project.name}\"")
    }

    fun convert(input: String): String = converter.convert(input).formatted
}

