package com.privates.browser.html.privacy;

import com.anthonycr.mezzanine.FileStream;

/**
 * @author LiShiChuang
 * @packageName com.privates.browser.html.privacy
 * @描述
 */
@FileStream("app/src/main/html/homepage.html")
interface PrivacyPageReader {
    fun provideHtml(): String
}
