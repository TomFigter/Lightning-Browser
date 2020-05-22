package com.hardcore.browser.device

/**
 * A representation of the info for the current build.
 */
data class BuildInfo(val buildType: BuildType)

/**
 *枚举 app构建模式
 */
enum class BuildType {
    DEBUG, //Debug模式
    RELEASE //Release模式
}
