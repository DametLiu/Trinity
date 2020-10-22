package damet.android.apkinstaller

import android.app.DownloadManager
import android.app.DownloadManager.Request.*
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment.DIRECTORY_DOWNLOADS
import damet.android.crypt.md5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class APKInstaller(private val context: Context, private val url: String, private val title: String) {
    fun download(autoInstall: Boolean = true, callback: (url: String, title: String, filePath: String, state: Int, downloaded: Int, total: Int) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            // 文件不存在， 下载
            if (!File(context.getExternalFilesDir(DIRECTORY_DOWNLOADS), "${url.md5()}.apk").exists()) {
                val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                val request = DownloadManager.Request(Uri.parse(url)).apply {
                    setTitle(title) // 通知的标题
                    setAllowedNetworkTypes(NETWORK_WIFI or NETWORK_MOBILE) // wifi 和 移动 网络下载
                    setMimeType("application/vnd.android.package-archive") // 类型为apk
                    setNotificationVisibility(VISIBILITY_VISIBLE) // 下载过程显示通知

                    // 创建并设置下载路径
                    context.getExternalFilesDir(DIRECTORY_DOWNLOADS)!!.mkdir()
                    setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, "${url.md5()}.apk")

                    // 下载任务的id
                    val id = downloadManager.enqueue(this)
                    // 每秒更新进度
                    Timer().apply {
                        schedule(object : TimerTask() {
                            override fun run() {
                                val cursor = downloadManager.query(DownloadManager.Query().setFilterById(id))
                                if (cursor != null && cursor.moveToFirst()) {
                                    val state = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                                    val title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE))
                                    val filePath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                                    val downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                                    val total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                                    callback.invoke(url, title, filePath, state, downloaded, total)
                                    if (state == DownloadManager.STATUS_SUCCESSFUL) {
                                        cancel()
                                        if (autoInstall) installApk()
                                    }
                                    cursor.close()
                                }
                            }
                        }, 0, 1000)
                    }
                }
            } else { // 文件已存在直接安装
                if (autoInstall) installApk()
            }
        }
    }

    fun installApk() {

    }
}