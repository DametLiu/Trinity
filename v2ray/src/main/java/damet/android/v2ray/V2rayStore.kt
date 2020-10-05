package damet.android.v2ray

import android.content.Context
import androidx.core.app.NotificationCompat
import com.alibaba.fastjson.TypeReference
import damet.android.mpp.ObjectDelegate
import damet.android.mpp.PrimitiveDelegate

internal object V2rayStore {
    var appPackageList by ObjectDelegateImpl(arrayListOf<String>(), object : TypeReference<ArrayList<String>>(){})
    var appPackageEnable by PrimitiveDelegateImpl(false)
    var enableLocalDNS by PrimitiveDelegateImpl(false)
    var forwardIpv6 by PrimitiveDelegateImpl(false)
    var domainName by PrimitiveDelegateImpl("v2ray")
    var configureFileContent by PrimitiveDelegateImpl("")

    var notificationChannelId by PrimitiveDelegateImpl("damet.android.v2ray.channelId")
    var notificationChannelName by PrimitiveDelegateImpl("V2ray Background Service")
    var notificationIcon by PrimitiveDelegateImpl(0)
    var notificationContentTitle by PrimitiveDelegateImpl("V2ray")
    var notificationPriority by PrimitiveDelegateImpl(NotificationCompat.PRIORITY_HIGH)
    var notificationOngoing by PrimitiveDelegateImpl(true)
    var notificationShowWhen by PrimitiveDelegateImpl(true)
    var notificationOnlyAlertOnce by PrimitiveDelegateImpl(true)
    var notificationId by PrimitiveDelegateImpl(1)

    private class PrimitiveDelegateImpl<T>(default: T, val crypt: Boolean = true) : PrimitiveDelegate<T>(default) {
        override val context: Context
            get() = V2rayManager.v2rayService.get()!!
        override val authority: String = "damet.android.v2ray.provider"
        override val name: String = "v2ray"
        override val cryptKey: String
            get() = if (crypt) authority else ""
    }

    private class ObjectDelegateImpl<T>(default: T, type: TypeReference<T>, val crypt: Boolean = true) : ObjectDelegate<T>(default, type) {
        override val context: Context
            get() = V2rayManager.v2rayService.get()!!
        override val authority: String = "damet.android.v2ray.provider"
        override val name: String = "v2ray"
        override val cryptKey: String
            get() = if (crypt) authority else ""
    }
}