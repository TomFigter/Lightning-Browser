package com.privates.browser.html.privacy;

import android.app.Application
import com.privates.browser.html.HtmlPageFactory
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author LiShiChuang
 * @packageName com.privates.browser.html.privacy
 * @描述
 */
@Reusable
class PrivacyPageFactory @Inject constructor(
        private val application: Application,
        private val privacyPageReader: PrivacyPageReader
):HtmlPageFactory{
    override fun buildPage(): Single<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
