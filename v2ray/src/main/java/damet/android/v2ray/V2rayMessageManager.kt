package damet.android.v2ray

import android.content.Context
import android.content.Intent

internal object V2rayMessageManager {
    internal const val NOTIFICATION_PENDING_INTENT_START_MAIN = 10086

    internal const val BROADCAST_ACTION_START_MAIN = "damet.android.v2ray.action.startMain"
    internal const val BROADCAST_ACTION_SERVICE = "damet.android.v2ray.action.service"

    private fun sendMessage(context: Context, act: String, what: Int, content: String) {
        context.sendBroadcast(Intent().apply {
            action = act
//            `package` =
        })
    }
}