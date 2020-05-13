package com.privates.browser.browser.cleanup

import com.privates.browser.browser.activity.BrowserActivity
import android.webkit.WebView

/**
 * A command that runs as the browser instance is shutting down to clean up anything that needs to
 * be cleaned up. For instance, if the user has chosen to clear cache on exit or if incognito mode
 * is closing.
 */
interface ExitCleanup {

    /**
     * Clean up the instance of the browser with the provided [webView] and [context].
     * 清理浏览器实例提供的(web视图)和(上下文)。
     */
    fun cleanUp(webView: WebView?, context: BrowserActivity)

}
