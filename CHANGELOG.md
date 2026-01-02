<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# OKLCH-converter Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed
- Update plugin description
- Update README to remove tool window reference

### Fixed
- Update dependencies: Qodana to 2025.3.1

## [1.1.1] - 2025-12-23

### Added
- Plugin icon for improved visual representation

### Changed
- Remove app from tool window
- Update README documentation

### Fixed
- Refactor test cleanup: remove unused imports and obsolete test data paths
- Remove obsolete XML test files for renaming functionality
- Remove unused testRename method and correct color conversion assertions

## [1.1.0] - 2025-12-17

### Added
- OKLCH color conversion action in plugin.xml
- Notification group in plugin.xml for better user feedback
- Enhanced ConvertColorAction to use project context for document updates
- Localization support in MyBundle.properties for OKLCH color conversion action

### Changed
- Refactor ColorConverter formatting methods
- Update MyPluginTest with refined color conversion assertions and new test cases for edge scenarios

### Removed
- Unused MyProjectService and MyProjectActivity classes
- Kotlin JVM plugin from build configuration (replaced with Kotlin plugin)

## [1.0.0] - 2025-12-10

### Added
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)
- Color conversion functionality in tool window
- UI components for input, output, and error handling
- Copy-to-clipboard feature
- Improved error messaging
