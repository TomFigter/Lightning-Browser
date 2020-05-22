package com.hardcore.browser.view.webrtc

import com.hardcore.browser.extensions.requiredPermissions
import android.annotation.TargetApi
import android.os.Build
import android.webkit.PermissionRequest
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 请求来自一个web页面的模型管理权限
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@Singleton
class WebRtcPermissionsModel @Inject constructor() {

    private val resourceGrantMap = mutableMapOf<String, HashSet<String>>()

    /**
     *请求许可用户使用特定的设备资源
     * 由用户来决定是否允许
     * @param permissionRequest the request being made.
     * @param view the view that will delegate requesting permissions or resources from the user.
     */
    fun requestPermission(permissionRequest: PermissionRequest, view: WebRtcPermissionsView) {
        val host = permissionRequest.origin.host ?: ""
        val requiredResources = permissionRequest.resources
        val requiredPermissions = permissionRequest.requiredPermissions()

        if (resourceGrantMap[host]?.containsAll(requiredResources.asList()) == true) {
            view.requestPermissions(requiredPermissions) { permissionsGranted ->
                if (permissionsGranted) {
                    permissionRequest.grant(requiredResources)
                } else {
                    permissionRequest.deny()
                }
            }
        } else {
            view.requestResources(host, requiredResources) { resourceGranted ->
                if (resourceGranted) {
                    view.requestPermissions(requiredPermissions) { permissionsGranted ->
                        if (permissionsGranted) {
                            resourceGrantMap[host]?.addAll(requiredResources)
                                ?: resourceGrantMap.put(host, requiredResources.toHashSet())
                            permissionRequest.grant(requiredResources)
                        } else {
                            permissionRequest.deny()
                        }
                    }
                } else {
                    permissionRequest.deny()
                }
            }
        }
    }

}
