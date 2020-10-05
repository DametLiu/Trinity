package damet.android.v2ray

import android.content.Intent
import android.net.VpnService
import android.os.IBinder

internal class V2rayService : VpnService() {
    override fun onCreate() {
        super.onCreate()
        V2rayManager.onServiceCreate(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        V2rayManager.startV2ray()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onRevoke() = V2rayManager.stopV2ray()

    override fun onLowMemory() {
        V2rayManager.stopV2ray()
        super.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        V2rayManager.onServiceDestroy(this)
    }
}