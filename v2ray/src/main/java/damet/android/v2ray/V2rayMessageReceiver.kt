package damet.android.v2ray

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import damet.android.v2ray.V2rayManager.v2rayService
import damet.android.v2ray.V2rayMessageManager.BROADCAST_ACTION_START_SERVICE
import damet.android.v2ray.V2rayMessageManager.BROADCAST_ACTION_STOP_SERVICE

internal class V2rayMessageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val service = v2rayService.get() ?: return

        when (intent.action) {
            BROADCAST_ACTION_START_SERVICE -> {
                V2rayManager.startV2ray()
                if (V2rayManager.v2rayPoint.isRunning)
                    //TODO 发送启动完成
                else
                    //TODO 发送启动失败
            }
            BROADCAST_ACTION_STOP_SERVICE -> {
                V2rayManager.stopV2ray()
                if (!V2rayManager.v2rayPoint.isRunning)
                    //TODO 发送停止完成
                else
                    //TODO 发送停止失败
            }
        }
    }
}