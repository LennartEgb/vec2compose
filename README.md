# vec2compose

<p align=center>
<img width=400 src="https://user-images.githubusercontent.com/26793300/182307782-c5173c33-c65d-4a02-a7f0-99c08ffa5a8d.png" alt="vec2compose"/>
</p>


A CLI tool to create `ImageVector` from Android vector XML and SVG.

## Setup

Run `./gradlew assembleCli` to generate the native binary that is generated in `dist` folder.
Add the `dist` folder to `PATH` and use it everywhere.

## Usage
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

## Motivation
This is a project to play around with CLI tools and support the Jetpack Compose development to move further away from XML files in Android projects.
A custom icon set can be generated as the [material icon set](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material/material-icons-core/src/commonMain/kotlin/androidx/compose/material/icons/Icons.kt;l=65?q=Icons&sq=) without adding XML resources.
