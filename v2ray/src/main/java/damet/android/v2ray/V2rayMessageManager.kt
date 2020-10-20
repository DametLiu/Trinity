package damet.android.v2ray

import android.content.Context
import android.content.Intent

internal object V2rayMessageManager {
    internal const val NOTIFICATION_PENDING_INTENT_START_MAIN = 10086

    internal const val BROADCAST_ACTION_START_MAIN = "damet.android.v2ray.action.startMain"
    internal const val BROADCAST_ACTION_START_SERVICE = "damet.android.v2ray.action.startService"
    internal const val BROADCAST_ACTION_STOP_SERVICE = "damet.android.v2ray.action.stopService"

    internal const val BROADCAST_ACTION_SERVICE_STARTED = "damet.android.v2ray.action.serviceStarted"
    internal const val BROADCAST_ACTION_SERVICE_START_FAILED = "damet.android.v2ray.action.serviceStartFailed"
    internal const val BROADCAST_ACTION_SERVICE_STOPPED = "damet.android.v2ray.action.serviceStopped"
    internal const val BROADCAST_ACTION_SERVICE_STOP_FAILED = "damet.android.v2ray.action.serviceStopFailed"

    private fun sendMessage(context: Context, act: String, what: Int, content: String) {
        context.sendBroadcast(Intent().apply {
            action = act
//            `package` =
        })
    }
}