package com.rayadev.xchange

expect class NumberFormatter {
    fun format(number: Double): String

    companion object {
        fun getInstance(): NumberFormatter
    }
}

