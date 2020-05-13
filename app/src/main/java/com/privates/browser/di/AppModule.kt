package com.privates.browser.di

import com.privates.browser.device.BuildInfo
import com.privates.browser.device.BuildType
import com.privates.browser.html.ListPageReader
import com.privates.browser.html.bookmark.BookmarkPageReader
import com.privates.browser.html.homepage.HomePageReader
import com.privates.browser.js.InvertPage
import com.privates.browser.js.TextReflow
import com.privates.browser.js.ThemeColor
import com.privates.browser.log.AndroidLogger
import com.privates.browser.log.Logger
import com.privates.browser.log.NoOpLogger
import com.privates.browser.search.suggestions.RequestFactory
import com.privates.browser.utils.FileUtils
import android.app.Application
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ShortcutManager
import android.content.res.AssetManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import com.anthonycr.mezzanine.MezzanineGenerator
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.i2p.android.ui.I2PAndroidHelper
import okhttp3.*
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class AppModule {
    //对外提供主线程
    @Provides
    @MainHandler
    fun provideMainHandler() = Handler(Looper.getMainLooper())
    //对外提供上下文环境
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
    //对外提供 用户SP
    @Provides
    @UserPrefs
    fun provideUserPreferences(application: Application): SharedPreferences = application.getSharedPreferences("settings", 0)
    //对外提供 Debug SP
    @Provides
    @DevPrefs
    fun provideDebugPreferences(application: Application): SharedPreferences = application.getSharedPreferences("developer_settings", 0)
    //对外提供 广告SP
    @Provides
    @AdBlockPrefs
    fun provideAdBlockPreferences(application: Application): SharedPreferences = application.getSharedPreferences("ad_block_settings", 0)
    //对外提供Asset 管理器
    @Provides
    fun providesAssetManager(application: Application): AssetManager = application.assets
    //对外提供 剪切板管理器
    @Provides
    fun providesClipboardManager(application: Application) = application.getSystemService<ClipboardManager>()!!
    //对外提供 输入法管理器
    @Provides
    fun providesInputMethodManager(application: Application) = application.getSystemService<InputMethodManager>()!!
    //对外提供 下载管理器
    @Provides
    fun providesDownloadManager(application: Application) = application.getSystemService<DownloadManager>()!!

    @Provides
    fun providesConnectivityManager(application: Application) = application.getSystemService<ConnectivityManager>()!!
    //对外提供 通知管理器
    @Provides
    fun providesNotificationManager(application: Application) = application.getSystemService<NotificationManager>()!!
    //对外提供 window管理器
    @Provides
    fun providesWindowManager(application: Application) = application.getSystemService<WindowManager>()!!
    //对外提供 快捷键管理器
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    @Provides
    fun providesShortcutManager(application: Application) = application.getSystemService<ShortcutManager>()!!
    //对外提供 IO线程
    @Provides
    @DatabaseScheduler
    @Singleton
    fun providesIoThread(): Scheduler = Schedulers.from(Executors.newSingleThreadExecutor())
    //对外提供 磁盘线程
    @Provides
    @DiskScheduler
    @Singleton
    fun providesDiskThread(): Scheduler = Schedulers.from(Executors.newSingleThreadExecutor())
    //对外提供 网络线程
    @Provides
    @NetworkScheduler
    @Singleton
    fun providesNetworkThread(): Scheduler = Schedulers.from(ThreadPoolExecutor(0, 4, 60, TimeUnit.SECONDS, LinkedBlockingDeque()))
    //对外提供 主线程
    @Provides
    @MainScheduler
    @Singleton
    fun providesMainThread(): Scheduler = AndroidSchedulers.mainThread()
    //对外提供 缓存控制
    @Singleton
    @Provides
    fun providesSuggestionsCacheControl(): CacheControl = CacheControl.Builder().maxStale(1, TimeUnit.DAYS).build()
    //对外提供 请求工厂
    @Singleton
    @Provides
    fun providesSuggestionsRequestFactory(cacheControl: CacheControl): RequestFactory = object : RequestFactory {
        override fun createSuggestionsRequest(httpUrl: HttpUrl, encoding: String): Request {
            return Request.Builder().url(httpUrl)
                .addHeader("Accept-Charset", encoding)
                .cacheControl(cacheControl)
                .build()
        }
    }
    //最大缓存Age
    private fun createInterceptorWithMaxCacheAge(maxCacheAgeSeconds: Long) = Interceptor { chain ->
        chain.proceed(chain.request()).newBuilder()
            .header("cache-control", "max-age=$maxCacheAgeSeconds, max-stale=$maxCacheAgeSeconds")
            .build()
    }

    @Singleton
    @Provides
    @SuggestionsClient
    fun providesSuggestionsHttpClient(application: Application): Single<OkHttpClient> = Single.fromCallable {
        val intervalDay = TimeUnit.DAYS.toSeconds(1)
        val suggestionsCache = File(application.cacheDir, "suggestion_responses")

        return@fromCallable OkHttpClient.Builder()
            .cache(Cache(suggestionsCache, FileUtils.megabytesToBytes(1)))
            .addNetworkInterceptor(createInterceptorWithMaxCacheAge(intervalDay))
            .build()
    }.cache()

    @Singleton
    @Provides
    @HostsClient
    fun providesHostsHttpClient(application: Application): Single<OkHttpClient> = Single.fromCallable {
        val intervalYear = TimeUnit.DAYS.toSeconds(365)
        val suggestionsCache = File(application.cacheDir, "hosts_cache")

        return@fromCallable OkHttpClient.Builder()
            .cache(Cache(suggestionsCache, FileUtils.megabytesToBytes(5)))
            .addNetworkInterceptor(createInterceptorWithMaxCacheAge(intervalYear))
            .build()
    }.cache()
    //对外提供 Log打印
    @Provides
    @Singleton
    fun provideLogger(buildInfo: BuildInfo): Logger = if (buildInfo.buildType == BuildType.DEBUG) {
        AndroidLogger()
    } else {
        NoOpLogger()
    }

    @Provides
    @Singleton
    fun provideI2PAndroidHelper(application: Application): I2PAndroidHelper = I2PAndroidHelper(application)
    //对外提供 列表页面
    @Provides
    fun providesListPageReader(): ListPageReader = MezzanineGenerator.ListPageReader()
    //对外提供 主页面
    @Provides
    fun providesHomePageReader(): HomePageReader = MezzanineGenerator.HomePageReader()
    //对外提供 书签页面
    @Provides
    fun providesBookmarkPageReader(): BookmarkPageReader = MezzanineGenerator.BookmarkPageReader()
    //对外提供 文本流
    @Provides
    fun providesTextReflow(): TextReflow = MezzanineGenerator.TextReflow()
    //对外提供 主题颜色
    @Provides
    fun providesThemeColor(): ThemeColor = MezzanineGenerator.ThemeColor()
    //对外提供 无痕浏览
    @Provides
    fun providesInvertPage(): InvertPage = MezzanineGenerator.InvertPage()

}

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class SuggestionsClient

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class HostsClient

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class MainHandler

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class UserPrefs

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class AdBlockPrefs

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class DevPrefs

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class MainScheduler

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class DiskScheduler

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class NetworkScheduler

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class DatabaseScheduler
