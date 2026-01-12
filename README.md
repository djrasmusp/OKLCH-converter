# OKLCH-converter

![Build](https://github.com/djrasmusp/OKLCH-converter/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/29405-oklch-converter.svg)](https://plugins.jetbrains.com/plugin/29405-oklch-converter)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/29405-oklch-converter.svg)](https://plugins.jetbrains.com/plugin/29405-oklch-converter)

## What it does
- Converts CSS colors from `hex`, `rgb()`, and `rgba()` into `oklch()` format
- Supports short and long hex formats (e.g., `#f00`, `#ff0000`, `#f00f`, `#ff0000ff`)
- Handles alpha channels in hex and rgba formats
- **Convert multiple colors at once** - Select a block of text and convert all colors in the selection
- **Convert entire files** - Convert all colors in a file with a single action
- Validates input with clear error messages
- Replaces selected text directly in the editor with the converted `oklch()` values
- Available in context menu, Tools menu, and Project View

<!-- Plugin description -->
Convert common CSS color notations (hex, rgb, rgba) to OKLCH directly inside WebStorm and other JetBrains IDEs. 

**Convert multiple colors at once**: Select a block of text containing multiple color codes, right-click, and choose **Convert to OKLCH** from the context menu. All colors in the selection will be automatically detected and converted to their `oklch()` equivalents.

**Convert entire files**: Right-click on a file in the Project View or use the Tools menu to convert all colors in the entire file at once.

All conversions preserve alpha values when present, and the plugin provides clear feedback on how many colors were converted. Available in the context menu, Tools menu, and Project View.
<!-- Plugin description end -->

## Usage

### Convert Multiple Colors in Selection

1. Select a block of text containing color codes:
   ```css
   .my-class {
       background: #ff0000;
       color: rgb(0, 255, 0);
       border: rgba(0, 0, 255, 0.5);
   }
   ```

2. Right-click and choose **Convert to OKLCH** (or use **Tools** → **Convert to OKLCH**)

3. All colors will be converted:
   ```css
   .my-class {
       background: oklch(0.63 0.26 29);
       color: oklch(0.88 0.18 142);
       border: oklch(0.45 0.31 264 / 0.50);
   }
   ```

### Convert Entire File

1. Right-click on a file in the Project View
2. Choose **Convert File Colors to OKLCH** (or use **Tools** → **Convert File Colors to OKLCH**)
3. All colors in the file will be converted automatically

### Supported Color Formats

- **Hex**: `#f00`, `#ff0000`, `#f00f`, `#ff0000ff`
- **RGB**: `rgb(255, 0, 0)`, `rgb( 255 , 0 , 0 )` (spaces are handled)
- **RGBA**: `rgba(255, 0, 0, 0.5)`, `rgba(255, 0, 0, 1)`

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
