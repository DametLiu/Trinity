package damet.android.ext.kotlin

infix fun Byte.and(mask: Int): Int = toInt() and mask