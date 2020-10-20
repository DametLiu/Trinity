package damet.android.v2ray.core

import android.content.Context

internal class Seq {
    companion object {
        init {
            System.loadLibrary("gojni")
            init()
        }

        fun setContext(context: Context) = setContext(context as Any)

        private external fun init()
        private external fun setContext(context: Any)
    }
}