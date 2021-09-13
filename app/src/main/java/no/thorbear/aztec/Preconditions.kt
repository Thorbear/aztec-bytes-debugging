package no.thorbear.aztec

object Preconditions {

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalArgumentException if `expression` is false
     */
    @JvmStatic
    fun checkArgument(expression: Boolean) {
        require(expression)
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance,
     * but not involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a string using null-safe [toString].
     * @throws IllegalStateException if `expression` is false
     */
    @JvmStatic
    fun checkState(expression: Boolean, errorMessage: Any?) {
        check(expression) { errorMessage.toString() }
    }
}
