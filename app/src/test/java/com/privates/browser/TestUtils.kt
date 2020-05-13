@file:Suppress("NOTHING_TO_INLINE")

package com.privates.browser

/**
 * Use to implement an unimplemented method.
 */
inline fun unimplemented(): Nothing {
    throw NotImplementedError("Not implemented")
}
