package id.tiooooo.pokedata.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
