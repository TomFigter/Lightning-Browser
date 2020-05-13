package com.privates.browser.html.bookmark

import com.anthonycr.mezzanine.FileStream

/**
 * The store for the bookmarks HTML.
 * 书签Html
 */
@FileStream("app/src/main/html/bookmarks.html")
interface BookmarkPageReader {

    fun provideHtml(): String

}