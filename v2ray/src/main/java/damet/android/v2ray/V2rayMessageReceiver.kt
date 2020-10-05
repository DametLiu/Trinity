package damet.android.v2ray

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import damet.android.v2ray.V2rayManager.v2rayService
import damet.android.v2ray.V2rayMessageManager.BROADCAST_ACTION_START_MAIN

internal class V2rayMessageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val service = v2rayService.get() ?: return

//        when (intent.action) {
//            BROADCAST_ACTION_START_MAIN ->
//        }
    }
}