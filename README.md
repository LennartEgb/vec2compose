# vec2compose

<p center>
<img width=400 src="https://user-images.githubusercontent.com/26793300/182098686-b0c5d225-5751-496d-88a2-ccaf49e48cc9.png" alt="xml to compose"/>
</p>


A CLI tool to create `ImageVector` from Android vector XML files.

## Setup
Currently this is only available after a local gradle build
```bash
$ ./gradlew clean jar packageDistribution
```
A `dist` folder is created with the script and the `jar`.

## Usage
There are two options to create the `ImageVector`. The first option is to create a Kotlin file
directly
```bash
$ ./vec2compose -i ic_done.xml -o DoneIcon.kt
```
And the second option is to print the `ImageVector` directly to the console.
```bash
$ ./vec2compose -i ic_done.xml

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
This is a small project to play around with CLI tools and support the Jetpack Compose development to move further away from XML files in Android projects. A custom icon set can be generated as the [material icon set](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material/material-icons-core/src/commonMain/kotlin/androidx/compose/material/icons/Icons.kt;l=65?q=Icons&sq=) without adding XML resources.
