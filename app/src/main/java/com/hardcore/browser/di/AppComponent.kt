package com.hardcore.browser.di

import com.hardcore.browser.BrowserApp
import com.hardcore.browser.adblock.BloomFilterAdBlocker
import com.hardcore.browser.adblock.NoOpAdBlocker
import com.hardcore.browser.browser.SearchBoxModel
import com.hardcore.browser.browser.activity.BrowserActivity
import com.hardcore.browser.browser.activity.ThemableBrowserActivity
import com.hardcore.browser.browser.bookmarks.BookmarksDrawerView
import com.hardcore.browser.device.BuildInfo
import com.hardcore.browser.dialog.LightningDialogBuilder
import com.hardcore.browser.download.LightningDownloadListener
import com.hardcore.browser.reading.activity.ReadingActivity
import com.hardcore.browser.search.SuggestionsAdapter
import com.hardcore.browser.settings.activity.SettingsActivity
import com.hardcore.browser.settings.activity.ThemableSettingsActivity
import com.hardcore.browser.settings.fragment.*
import com.hardcore.browser.view.LightningChromeClient
import com.hardcore.browser.view.LightningView
import com.hardcore.browser.view.LightningWebClient
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (AppBindsModule::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun buildInfo(buildInfo: BuildInfo): Builder

        fun build(): AppComponent
    }

    fun inject(activity: BrowserActivity)

    fun inject(fragment: BookmarkSettingsFragment)

    fun inject(builder: LightningDialogBuilder)

    fun inject(lightningView: LightningView)

    fun inject(activity: ThemableBrowserActivity)

    fun inject(advancedSettingsFragment: AdvancedSettingsFragment)

    fun inject(app: BrowserApp)

    fun inject(activity: ReadingActivity)

    fun inject(webClient: LightningWebClient)

    fun inject(activity: SettingsActivity)

    fun inject(activity: ThemableSettingsActivity)

    fun inject(listener: LightningDownloadListener)

    fun inject(fragment: PrivacySettingsFragment)

    fun inject(fragment: DebugSettingsFragment)

    fun inject(suggestionsAdapter: SuggestionsAdapter)

    fun inject(chromeClient: LightningChromeClient)

    fun inject(searchBoxModel: SearchBoxModel)

    fun inject(generalSettingsFragment: GeneralSettingsFragment)

    fun inject(displaySettingsFragment: DisplaySettingsFragment)

    fun inject(adBlockSettingsFragment: AdBlockSettingsFragment)

    fun inject(bookmarksView: BookmarksDrawerView)

    fun provideBloomFilterAdBlocker(): BloomFilterAdBlocker

    fun provideNoOpAdBlocker(): NoOpAdBlocker

}
