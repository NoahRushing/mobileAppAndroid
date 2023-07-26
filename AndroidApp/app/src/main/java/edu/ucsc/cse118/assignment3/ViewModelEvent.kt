package edu.ucsc.cse118.assignment3

open class ViewModelEvent<out T>(private val content: T) {

    private var handled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getUnhandledContent(): T? {
        return if (handled) {
            null
        } else {
            handled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun getRawContent(): T = content
}