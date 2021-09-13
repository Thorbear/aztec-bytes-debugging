# Aztec Bytes Debugger

## Introduction

This app is adapted from "ML Kit Vision Quickstart Sample App", into an application that simply reads Aztec barcodes and outputs the bytes to screen as a hex string.
Main adaptions are found in BarcodeResultActivity and ThorBarcodeHandler, the rest is essentially from the quickstart app.

It uses the camera preview as input.
There's also a settings page that allows you to configure a few options:
* Preview Size - Specify the preview size of rear camera manually (Default size is chose appropriately based on screen size)
* Enable live viewport - Prevent the live camera preview from being blocked by API rendering speed


## Support

* [Documentation](https://developers.google.com/ml-kit/guides)
* [API Reference](https://developers.google.com/ml-kit/reference/android)


## buildSrc

* https://docs.gradle.org/current/userguide/kotlin_dsl.html#sec:kotlin-dsl_plugin
* https://medium.com/android-dev-hacks/kotlin-dsl-gradle-scripts-in-android-made-easy-b8e2991e2ba


## Updating the gradle wrapper

https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:upgrading_wrapper


## gradle-versions-plugin

This project includes a gradle plugin for checking for newer versions of all dependencies, since Android Studio isn't very good at doing that for Kotlin DSL buildscripts.
To run the checker: `gradlew dependencyUpdates`

https://github.com/ben-manes/gradle-versions-plugin
