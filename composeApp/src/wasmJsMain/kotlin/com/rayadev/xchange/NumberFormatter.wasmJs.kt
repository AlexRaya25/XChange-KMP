package com.rayadev.xchange

import kotlin.math.round

actual class NumberFormatter {
    actual fun format(number: Double): String {
        //return number.toString()
        return (round(number * 100) / 100).toString()
    }

    actual companion object {
        actual fun getInstance(): NumberFormatter {
            return NumberFormatter()
        }
    }
}