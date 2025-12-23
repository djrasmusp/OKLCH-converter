# OKLCH-converter

![Build](https://github.com/djrasmusp/OKLCH-converter/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/29405-oklch-converter.svg)](https://plugins.jetbrains.com/plugin/29405-oklch-converter)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/29405-oklch-converter.svg)](https://plugins.jetbrains.com/plugin/29405-oklch-converter)

## What it does
- Converts CSS colors from `hex`, `rgb()`, and `rgba()` into `oklch()` format
- Supports short and long hex formats (e.g., `#f00`, `#ff0000`, `#f00f`, `#ff0000ff`)
- Handles alpha channels in hex and rgba formats
- Validates input with clear error messages
- Replaces selected text directly in the editor with the converted `oklch()` value

<!-- Plugin description -->
Convert common CSS color notations (hex, rgb, rgba) to OKLCH directly inside WebStorm and other JetBrains IDEs. Simply select a color code in your editor, right-click, and choose **Convert to OKLCH** from the context menu. The selected color will be instantly replaced with its `oklch()` equivalent, preserving alpha values when present.
<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "OKLCH-converter"</kbd> >
  <kbd>Install</kbd>

- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/djrasmusp/OKLCH-converter/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
