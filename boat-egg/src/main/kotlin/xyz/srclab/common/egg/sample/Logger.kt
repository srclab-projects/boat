package xyz.srclab.common.egg.sample

/**
 * @author sunqian
 */
interface Logger {

    fun info(pattern: String, vararg args: Any?)

    fun debug(pattern: String, vararg args: Any?)
}