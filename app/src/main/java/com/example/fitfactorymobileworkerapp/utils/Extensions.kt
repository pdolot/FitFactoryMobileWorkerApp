package com.example.fitfactorymobileworkerapp.utils

fun Float.scaleValue(a: Float, b: Float, c: Float, d: Float): Float {
    return (this - a) * (d - c) / (b - a) + c
}

fun ByteArray.limit(x: Int, y: Int, width: Int, height: Int, originalWidth: Int, originalHeight: Int): ByteArray{
    val byteArray = ArrayList<Byte>()

    for (i in 0 until originalHeight){
        if (i >= y && i < y + height){
            for (j in 0 until originalWidth){
                if (j >= x && j < x + width){
                    byteArray.add(this[i * originalWidth + j])
                }
            }
        }
    }

    return byteArray.toByteArray()
}