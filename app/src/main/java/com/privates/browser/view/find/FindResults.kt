package com.privates.browser.view.find


/**
 * 搜索页面中关键字的结果
 */
interface FindResults {

    /**
     * 下一个搜索结果
     */
    fun nextResult()

    /**
     * 上一个搜索结果
     */
    fun previousResult()

    /**
     * 清除搜索结果
     */
    fun clearResults()

}
