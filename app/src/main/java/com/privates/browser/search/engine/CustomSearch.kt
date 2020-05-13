package com.privates.browser.search.engine

import com.privates.browser.R

/**
 * A custom search engine.
 */
class CustomSearch(queryUrl: String) : BaseSearchEngine(
    "file:///android_asset/lightning.png",
    queryUrl,
    R.string.search_engine_custom
)
