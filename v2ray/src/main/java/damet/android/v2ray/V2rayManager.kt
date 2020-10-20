package damet.android.v2ray

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.LocalSocket
import android.net.LocalSocketAddress
import android.net.VpnService
import android.os.Build
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.core.app.NotificationCompat
import damet.android.v2ray.V2rayMessageManager.BROADCAST_ACTION_SERVICE
import damet.android.v2ray.V2rayMessageManager.BROADCAST_ACTION_START_MAIN
import damet.android.v2ray.V2rayMessageManager.NOTIFICATION_PENDING_INTENT_START_MAIN
import damet.android.v2ray.V2rayStore.notificationChannelId
import damet.android.v2ray.V2rayStore.notificationChannelName
import damet.android.v2ray.V2rayStore.notificationContentTitle
import damet.android.v2ray.V2rayStore.notificationIcon
import damet.android.v2ray.V2rayStore.notificationId
import damet.android.v2ray.V2rayStore.notificationOngoing
import damet.android.v2ray.V2rayStore.notificationOnlyAlertOnce
import damet.android.v2ray.V2rayStore.notificationPriority
import damet.android.v2ray.V2rayStore.notificationShowWhen
import go.Seq
import libv2ray.Libv2ray
import libv2ray.V2RayPoint
import libv2ray.V2RayVPNServiceSupportsSet
import java.io.File
import java.lang.ref.SoftReference

internal object V2rayManager : V2RayVPNServiceSupportsSet {
    val v2rayPoint: V2RayPoint = Libv2ray.newV2RayPoint(this)
    private val messageReceiver: V2rayMessageReceiver = V2rayMessageReceiver()

    internal lateinit var v2rayService: SoftReference<V2rayService>
    private lateinit var parcelFileDescriptor: ParcelFileDescriptor
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager

    internal fun onServiceCreate(service: V2rayService){
        v2rayService = SoftReference(service)
        notificationManager = service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        v2rayPoint.packageName = service.filesDir.absolutePath.replace("files", "")
        v2rayPoint.packageCodePath = service.applicationInfo.nativeLibraryDir + "/"
        Seq.setContext(service.applicationContext)
    }

    internal fun onServiceDestroy(service: V2rayService) {
        hideNotification()
    }

    override fun setup(s: String) : Long {
        val service = v2rayService.get() ?: return -1

        val prepare = VpnService.prepare(service)
        if (prepare != null) return -1

        val builder = service.Builder()
        s.split(" ")
            .map { it.split(",") }
            .forEach {
                when (it[0][0]) {
                    'm' -> builder.setMtu(java.lang.Short.parseShort(it[1]).toInt())
                    's' -> builder.addSearchDomain(it[1])
                    'a' -> builder.addAddress(it[1], Integer.parseInt(it[2]))
                    'r' -> builder.addRoute(it[1], Integer.parseInt(it[2]))
                    'd' -> builder.addDnsServer(it[1])
                }
            }
//        builder.setSession("")

        if (V2rayStore.appPackageEnable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val apps = V2rayStore.appPackageList
            apps.forEach {
                try { builder.addDisallowedApplication(it) }
                catch (e: Exception) { w(e) }
            }
        }

        try { parcelFileDescriptor.close() }
        catch (ignored: Exception) {}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) builder.setMetered(false)
            parcelFileDescriptor = builder.establish()!!

        return 0
    }

    override fun sendFd(): Long {
        val service = v2rayService.get() ?: return -1

        try {
            LocalSocket().use { localSocket ->
                localSocket.connect(LocalSocketAddress(File(service.filesDir.absolutePath.replace("files", ""), "net.cache").absolutePath, LocalSocketAddress.Namespace.FILESYSTEM))
                localSocket.setFileDescriptorsForSend(arrayOf(parcelFileDescriptor.fileDescriptor))
                localSocket.outputStream.write(42)
            }
        } catch (e: Exception) { return -1 }

        return 0
    }

    override fun protect(fd: Long): Long {
        val service = v2rayService.get() ?: return 1
        return if (service.protect(fd.toInt())) 0 else 1
    }

    override fun onEmitStatus(code: Long, message: String): Long {
        d(message)
        return 0
    }
    override fun prepare(): Long = 0

    override fun shutdown(): Long {
        v2rayService.get() ?: return -1
        return try {
            stopV2ray()
            0
        } catch (e: Exception) {
            w(e)
            -1
        }
    }

    internal fun startV2ray() {
        val service = v2rayService.get() ?: return
        if (!v2rayPoint.isRunning) {
            try {
                val mFilter = IntentFilter()
                mFilter.addAction(BROADCAST_ACTION_START_MAIN)
                service.registerReceiver(messageReceiver, mFilter)
            } catch (e: Exception) { w(e) }

            v2rayPoint.apply {
                configureFileContent = V2rayStore.configureFileContent
                enableLocalDNS = V2rayStore.enableLocalDNS
                forwardIpv6 = V2rayStore.forwardIpv6
                domainName = V2rayStore.domainName
            }

            try { v2rayPoint.runLoop() }
            catch (e: Exception) { w(e) }

            if (v2rayPoint.isRunning) {
//                MessageUtil.sendMsg2UI(service, AppConfig.MSG_STATE_START_SUCCESS, "")
                showNotification()
            } else {
//                MessageUtil.sendMsg2UI(service, AppConfig.MSG_STATE_START_FAILURE, "")
                hideNotification()
            }
        }
    }

    internal fun stopV2ray() {
        val service = v2rayService.get() ?: return

        if (v2rayPoint.isRunning) {
            try { v2rayPoint.stopLoop() }
            catch (e: Exception) { w(e) }
        }

        hideNotification()

        try { service.unregisterReceiver(messageReceiver) }
        catch (e: Exception) { w(e) }

        service.stopSelf()

        try { parcelFileDescriptor.close() }
        catch (e: Exception) { w(e) }
    }

    private fun showNotification() {
        val service = v2rayService.get() ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(notificationChannelId, notificationChannelName, NotificationManager.IMPORTANCE_HIGH)
            channel.lightColor = Color.LTGRAY
            channel.importance = NotificationManager.IMPORTANCE_NONE
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager.createNotificationChannel(channel)
        }

        notificationBuilder = NotificationCompat.Builder(service, notificationChannelId)
            .setSmallIcon(notificationIcon)
            .setContentTitle(notificationContentTitle)
            .setPriority(notificationPriority)
            .setOngoing(notificationOngoing)
            .setShowWhen(notificationShowWhen)
            .setOnlyAlertOnce(notificationOnlyAlertOnce)
            .setContentIntent(PendingIntent.getBroadcast(service, NOTIFICATION_PENDING_INTENT_START_MAIN, Intent(BROADCAST_ACTION_START_MAIN), PendingIntent.FLAG_UPDATE_CURRENT))

        service.startForeground(notificationId, notificationBuilder.build())
    }

    private fun hideNotification() {
        val service = v2rayService.get() ?: return
        service.stopForeground(true)
    }

    private fun w(exception: Exception) {
        val service = v2rayService.get()
        if (service != null) Log.w(service.packageName, exception.toString())
        else Log.w("damet.android.v2ray", exception.toString())
    }

    private fun d(message: String) {
        val service = v2rayService.get()
        if (service != null) Log.d(service.packageName, message)
        else Log.d("damet.android.v2ray", message)
    }
}