package damet.android.ext

infix fun Byte.and(mask: Int): Int = toInt() and mask