package com.hardcore.browser.view.webrtc

/**
 * 此视图专门处理用户请求设备权限和资源申请
 */
interface WebRtcPermissionsView {
    /**
     *请求权限
     * @param permissions the permissions to request.
     * @param onGrant the callback to invoke when the user indicates their intent to grant or deny.
     */
    fun requestPermissions(permissions: Set<String>, onGrant: (Boolean) -> Unit)

    /**
     * 请求资源
     *
     * @param source the domain from which the request originated.
     * @param resources the device resources being requested.
     * @param onGrant the callback to invoke when the user indicates their intent to grant or deny.
     */
    fun requestResources(source: String, resources: Array<String>, onGrant: (Boolean) -> Unit)
}

