package damet.android.v2ray.core

internal interface V2RayVPNServiceSupportsSet {
    fun onEmitStatus(code: Long, message: String): Long
    fun prepare(): Long
    fun protect(fd: Long): Long
    fun sendFd(): Long
    fun setup(vpnSetupArg: String): Long
    fun shutdown(): Long
}