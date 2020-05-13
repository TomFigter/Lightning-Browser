package com.privates.browser.di

import com.privates.browser.adblock.allowlist.AllowListModel
import com.privates.browser.adblock.allowlist.SessionAllowListModel
import com.privates.browser.adblock.source.AssetsHostsDataSource
import com.privates.browser.adblock.source.HostsDataSource
import com.privates.browser.adblock.source.HostsDataSourceProvider
import com.privates.browser.adblock.source.PreferencesHostsDataSourceProvider
import com.privates.browser.browser.cleanup.DelegatingExitCleanup
import com.privates.browser.browser.cleanup.ExitCleanup
import com.privates.browser.database.adblock.HostsDatabase
import com.privates.browser.database.adblock.HostsRepository
import com.privates.browser.database.allowlist.AdBlockAllowListDatabase
import com.privates.browser.database.allowlist.AdBlockAllowListRepository
import com.privates.browser.database.bookmark.BookmarkDatabase
import com.privates.browser.database.bookmark.BookmarkRepository
import com.privates.browser.database.downloads.DownloadsDatabase
import com.privates.browser.database.downloads.DownloadsRepository
import com.privates.browser.database.history.HistoryDatabase
import com.privates.browser.database.history.HistoryRepository
import com.privates.browser.ssl.SessionSslWarningPreferences
import com.privates.browser.ssl.SslWarningPreferences
import dagger.Binds
import dagger.Module

/**
 * Dependency injection module used to bind implementations to interfaces.
 */
@Module
interface AppBindsModule {
    //清除记录
    @Binds
    fun bindsExitCleanup(delegatingExitCleanup: DelegatingExitCleanup): ExitCleanup
    //书签记录
    @Binds
    fun bindsBookmarkModel(bookmarkDatabase: BookmarkDatabase): BookmarkRepository
    //下载记录
    @Binds
    fun bindsDownloadsModel(downloadsDatabase: DownloadsDatabase): DownloadsRepository
    //历史记录
    @Binds
    fun bindsHistoryModel(historyDatabase: HistoryDatabase): HistoryRepository
    //广告块允许列表存储库
    @Binds
    fun bindsAdBlockAllowListModel(adBlockAllowListDatabase: AdBlockAllowListDatabase): AdBlockAllowListRepository
    //允许列表模式
    @Binds
    fun bindsAllowListModel(sessionAllowListModel: SessionAllowListModel): AllowListModel
    //SSL警告偏好
    @Binds
    fun bindsSslWarningPreferences(sessionSslWarningPreferences: SessionSslWarningPreferences): SslWarningPreferences
    //从主机列表中读取的资源
    @Binds
    fun bindsHostsDataSource(assetsHostsDataSource: AssetsHostsDataSource): HostsDataSource
    //存储库存储(主机)。
    @Binds
    fun bindsHostsRepository(hostsDatabase: HostsDatabase): HostsRepository
    //主机提供商的数据源
    @Binds
    fun bindsHostsDataSourceProvider(preferencesHostsDataSourceProvider: PreferencesHostsDataSourceProvider): HostsDataSourceProvider
}
