package com.privates.browser.di

import com.privates.browser.BrowserApp
import com.privates.browser.adblock.BloomFilterAdBlocker
import com.privates.browser.adblock.NoOpAdBlocker
import com.privates.browser.browser.SearchBoxModel
import com.privates.browser.browser.activity.BrowserActivity
import com.privates.browser.browser.activity.ThemableBrowserActivity
import com.privates.browser.browser.bookmarks.BookmarksDrawerView
import com.privates.browser.device.BuildInfo
import com.privates.browser.dialog.LightningDialogBuilder
import com.privates.browser.download.LightningDownloadListener
import com.privates.browser.reading.activity.ReadingActivity
import com.privates.browser.search.SuggestionsAdapter
import com.privates.browser.settings.activity.SettingsActivity
import com.privates.browser.settings.activity.ThemableSettingsActivity
import com.privates.browser.settings.fragment.*
import com.privates.browser.view.LightningChromeClient
import com.privates.browser.view.LightningView
import com.privates.browser.view.LightningWebClient
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
