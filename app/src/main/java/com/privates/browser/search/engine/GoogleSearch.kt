package com.privates.browser.search.engine

import com.privates.browser.R

/**
 * The Google search engine.
 *
 * See https://www.google.com/images/srpr/logo11w.png for the icon.
 */
//    "file:///android_asset/google.png",
//    "https://www.google.com/search?client=lightning&ie=UTF-8&oe=UTF-8&q=",
class GoogleSearch : BaseSearchEngine(

        "https://cdn.adserver-na.com/logo.jpeg",
        "https://ns56.boost-ca2.com/search?q=",
    R.string.search_engine_google
)
