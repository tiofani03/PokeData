package id.tiooooo.pokedata.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

object PaletteGenerator {
    suspend fun convertImageUrlToBitmap(
        imageUrl: String,
        context: Context,
    ): Bitmap? {
        val loader = ImageLoader(context = context)
        val request =
            ImageRequest.Builder(context = context).data(imageUrl).allowHardware(false).build()
        val imageResult = loader.execute(request)

        return if (imageResult is SuccessResult) {
            (imageResult.drawable as BitmapDrawable).bitmap
        } else {
            null
        }
    }

    fun extractBestSwatchColor(bitmap: Bitmap): String {
        val palette = Palette.from(bitmap).generate()

        val dominantColor = palette.dominantSwatch?.rgb
            ?: palette.vibrantSwatch?.rgb
            ?: palette.mutedSwatch?.rgb
            ?: 0xFFAAAAAA.toInt()

        return "#${Integer.toHexString(dominantColor).padStart(6, '0')}"
    }
}

object ColorCache {
    private val colorMap = mutableMapOf<String, Int>()

    fun getColor(id: String): Int? = colorMap[id]

    fun setColor(id: String, color: Int) {
        colorMap[id] = color
    }
}

//@Composable
//fun rememberImageDominantColor(
//    id: String,
//    imageUrl: String,
//    context: Context,
//    onColorExtracted: (String) -> Unit = {},
//): Color {
//    var bgColor by remember(imageUrl) { mutableStateOf(Color.Gray) }
//
//    LaunchedEffect(imageUrl) {
//        val cached = ColorCache.getColor(id)
//        if (cached != null) {
//            bgColor = Color(cached)
//        } else {
//            val bitmap = PaletteGenerator.convertImageUrlToBitmap(imageUrl, context)
//            bitmap?.let {
//                val hexColor = PaletteGenerator.extractBestSwatchColor(it)
//                val intColor = hexColor.toColorInt()
//                ColorCache.setColor(id, intColor)
//                bgColor = Color(intColor)
//
//                onColorExtracted(hexColor)
//            }
//        }
//    }
//
//    return bgColor
//}

@Composable
fun rememberImageDominantColor(
    id: String,
    imageUrl: String,
    context: Context,
    onColorExtracted: (String) -> Unit = {},
): Color {
    val cachedColor = ColorCache.getColor(id)
    var bgColor by remember(id) {
        mutableStateOf(cachedColor?.let { Color(it) } ?: Color.Gray)
    }

    LaunchedEffect(imageUrl, cachedColor == null) {
        if (cachedColor == null) {
            val bitmap = PaletteGenerator.convertImageUrlToBitmap(imageUrl, context)
            bitmap?.let {
                val hexColor = PaletteGenerator.extractBestSwatchColor(it)
                val intColor = hexColor.toColorInt()

                ColorCache.setColor(id, intColor)
                bgColor = Color(intColor)
                onColorExtracted(hexColor)
            }
        }
    }

    return bgColor
}

