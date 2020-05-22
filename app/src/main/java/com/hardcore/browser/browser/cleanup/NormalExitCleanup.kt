package com.hardcore.browser.browser.cleanup

import com.hardcore.browser.browser.activity.BrowserActivity
import com.hardcore.browser.database.history.HistoryDatabase
import com.hardcore.browser.di.DatabaseScheduler
import com.hardcore.browser.log.Logger
import com.hardcore.browser.preference.UserPreferences
import com.hardcore.browser.utils.WebUtils
import android.webkit.WebView
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Exit cleanup that should run whenever the main browser process is exiting.
 */
class NormalExitCleanup @Inject constructor(
    private val userPreferences: UserPreferences,
    private val logger: Logger,
    private val historyDatabase: HistoryDatabase,
    @DatabaseScheduler private val databaseScheduler: Scheduler
) : ExitCleanup {
    override fun cleanUp(webView: WebView?, context: BrowserActivity) {
        if (userPreferences.clearCacheExit) {
            WebUtils.clearCache(webView)
            logger.log(TAG, "Cache Cleared")
        }
        if (userPreferences.clearHistoryExitEnabled) {
            WebUtils.clearHistory(context, historyDatabase, databaseScheduler)
            logger.log(TAG, "History Cleared")
        }
        if (userPreferences.clearCookiesExitEnabled) {
            WebUtils.clearCookies(context)
            logger.log(TAG, "Cookies Cleared")
        }
        if (userPreferences.clearWebStorageExitEnabled) {
            WebUtils.clearWebStorage()
            logger.log(TAG, "WebStorage Cleared")
        }
    }

    companion object {
        const val TAG = "NormalExitCleanup"
    }
}
