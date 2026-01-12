<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# OKLCH-converter Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.2.0] - 2026-01-12

### Added
- File conversion functionality: Convert all colors in an entire file to OKLCH format
- `ConvertFileAction`: New action to convert all colors in a file via context menu or Tools menu
- `ColorConverter.convertFile()`: Method to scan and convert all HEX, RGB, and RGBA colors in file content
- Multiple color conversion in selection: Convert all colors found in a text selection at once
- Tools menu integration: Actions now available in Tools menu in addition to context menus
- Project View integration: Convert file action available when right-clicking files in Project View
- Comprehensive file conversion tests covering multiple scenarios
- Localization strings for file conversion features and error messages

### Changed
- `ConvertColorAction`: Now scans entire selection for all color codes and converts them simultaneously instead of requiring exact color match
- Plugin description updated to reflect new multi-color and file conversion capabilities
- README.md updated with usage examples and new feature documentation
- Error handling improved with detailed feedback on conversion results

### Fixed
- ConvertFileAction now works correctly when invoked from Tools menu by using active editor file

## [1.1.1] - 2025-12-23

### Changed
- Update plugin description
- Update README to remove tool window reference

### Fixed
- Update dependencies: Qodana to 2025.3.1

## [1.1.0] - 2025-12-17

### Added
- Plugin icon for improved visual representation

### Changed
- Remove app from tool window
- Update README documentation

### Fixed
- Refactor test cleanup: remove unused imports and obsolete test data paths
- Remove obsolete XML test files for renaming functionality
- Remove unused testRename method and correct color conversion assertions

## [1.0.0] - 2025-12-10

### Added
- OKLCH color conversion action in plugin.xml
- Notification group in plugin.xml for better user feedback
- Enhanced ConvertColorAction to use project context for document updates
- Localization support in MyBundle.properties for OKLCH color conversion action
- Color conversion functionality in tool window
- UI components for input, output, and error handling
- Copy-to-clipboard feature
- Improved error messaging

### Changed
- Refactor ColorConverter formatting methods
- Update MyPluginTest with refined color conversion assertions and new test cases for edge scenarios

### Removed
- Unused MyProjectService and MyProjectActivity classes
- Kotlin JVM plugin from build configuration (replaced with Kotlin plugin)

