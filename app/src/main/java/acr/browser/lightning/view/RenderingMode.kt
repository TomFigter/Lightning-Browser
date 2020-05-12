package acr.browser.lightning.view

import acr.browser.lightning.preference.IntEnum

/**
 * An enum representing the browser's available rendering modes.
 * 枚举浏览器的渲染模式
 */
enum class RenderingMode(override val value: Int) : IntEnum {
    NORMAL(0), //正常
    INVERTED(1),//反转
    GRAYSCALE(2),//灰度
    INVERTED_GRAYSCALE(3),//倒灰度
    INCREASE_CONTRAST(4)//增加对比度
}
