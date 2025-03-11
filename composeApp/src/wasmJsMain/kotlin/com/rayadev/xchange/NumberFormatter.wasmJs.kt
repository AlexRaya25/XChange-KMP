package com.rayadev.xchange

actual class NumberFormatter {
    actual fun format(number: Double): String {
        return number.toString()
    }

    actual companion object {
        actual fun getInstance(): NumberFormatter {
            return NumberFormatter()
        }
    }
}