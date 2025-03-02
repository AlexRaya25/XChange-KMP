package com.rayadev.xchange

// commonMain
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
// commonMain
expect class NumberFormatter {
    fun format(number: Double): String

    companion object {
        fun getInstance(): NumberFormatter
    }
}

