package com.example.estitweg.asciiart

import android.content.Context
import android.widget.ImageView
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.util.Log
import kotlinx.android.synthetic.main.content_main.*
import android.graphics.ColorMatrixColorFilter
import android.graphics.ColorMatrix
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable


class ImageConverter {

    fun convert(imageView: ImageView): String{
        var txt = ""

        val img = toGreyScale(imageView)

        val btm = (img.drawable as BitmapDrawable).bitmap

        val h = (btm.height*0.028).toInt()
        val w = (btm.width*0.028).toInt()
        var bitmap = getResizedBitmap(btm, h, w)

        //https://stackoverflow.com/questions/7807360/how-to-get-pixel-color-in-android


        Log.d("TAG","h: {$h} w: {$w}")
        for(y in 0..h-1){
            for(x in 0..w-1){
                val pixel = bitmap.getPixel(x, y)
                txt += pixelToChar(pixel)
            }
            txt += "\n"
        }
        return txt
    }


    //https://stackoverflow.com/questions/3373860/convert-a-bitmap-to-grayscale-in-android
    fun toGreyScale(img: ImageView): ImageView{
        val matrix = ColorMatrix()
        matrix.setSaturation(0f)  //0 means grayscale
        val cf = ColorMatrixColorFilter(matrix)
        img.setColorFilter(cf)
        img.setImageAlpha(128)
        return img
    }


    //https://github.com/EstiT/AsciiArtMaker/blob/Example/Make.py
    fun pixelToChar(pixel:Int): String{
        val r = Color.red(pixel)
        val b = Color.blue(pixel)
        val g = Color.green(pixel)
        if (r>250 && g>250 && b>250){
            return " . "
        }
        else if(r>245 && g>245 && b>245){
            return " , "
        }
        else if(r>224 && g>224 && b>224){
            return " : "
        }
        else if(r>192 && g>192 && b>192){
            return "~"
        }
        else if(r>160 && g>160 && b>160){
            return "="
        }
        else if(r>128 && g>128 && b>128){
            return "/"
        }
        else if(r>96 && g>96 && b>96){
            return "?"
        }
        else if(r>64 && g>64 && b>64){
            return "X"
        }
        else if(r>32 && g>32 && b>32){
            return "@"
        }
        else if(r>15 && g>15 && b>15){
            return "#"
        }
        else {
            return "■"
        }
    }

    //https://stackoverflow.com/questions/10773511/how-to-resize-an-image-i-picked-from-the-gallery-in-android
    fun getResizedBitmap(bm: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height

        // create a matrix for the manipulation
        val matrix = Matrix()

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight)

        // recreate the new Bitmap

        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
    }

}