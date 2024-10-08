# vec2compose
![Github Actions Status](https://github.com/lennartegb/vec2compose/actions/workflows/ci.yml/badge.svg)
[![codecov](https://codecov.io/github/LennartEgb/vec2compose/branch/main/graph/badge.svg?token=3GZE97P1A2)](https://codecov.io/github/LennartEgb/vec2compose)

<p align=center>
    <img width=400 src="composeApp/src/commonMain/composeResources/drawable/logo.png" alt="random logo showing svg and xml logo pointing to jetpack compose logo"/> 
</p>

A CLI tool to create `ImageVector` from Android vector XML and SVG.

## Setup

### CLI
Clone this repository and run `./gradlew assembleCli` to generate the native binary to the `dist` folder.

#### Usage

There are two options to create the `ImageVector`. The first option is to create a Kotlin file.

```bash
vec2compose -i ic_done.xml -o DoneIcon.kt
```

The second option is to print the `ImageVector` to the console.

```bash
vec2compose -i ic_done.xml
```

```kotlin
ImageVector.Builder(
    name = "ic_done",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).path(
    fill = SolidColor(Color.Black),
    fillAlpha = 1f,
    stroke = null,
    strokeAlpha = 1f,
    strokeLineWidth = 1f,
    strokeLineCap = StrokeCap.Butt,
    strokeLineJoin = StrokeJoin.Bevel,
    strokeLineMiter = 1f,
    pathFillType = PathFillType.NonZero
) {
    moveTo(9.0f, 16.2f)
    lineTo(4.8f, 12.0f)
    lineToRelative(-1.4f, 1.4f)
    lineTo(9.0f, 19.0f)
    lineTo(21.0f, 7.0f)
    lineToRelative(-1.4f, -1.4f)
    lineTo(9.0f, 16.2f)
    close()
}.build()
```

### Desktop
Clone this repository and run `./gradlew desktopRun -DmainClass=MainKt --quiet`.

## Motivation

This is a project to play around with CLI tools and support the Jetpack Compose development.
A custom icon set can be generated as the [material icon set](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material/material-icons-core/src/commonMain/kotlin/androidx/compose/material/icons/Icons.kt;l=65?q=Icons&sq=) without adding XML resources.
