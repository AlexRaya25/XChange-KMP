package com.rayadev.xchange

import java.text.DecimalFormat

// androidMain y desktopMain
actual class NumberFormatter {
    actual fun format(number: Double): String {
        val decimalFormat = DecimalFormat("#,##0.##")
        return decimalFormat.format(number)
    }

    actual companion object {
        actual fun getInstance(): NumberFormatter {
            return NumberFormatter()
        }
    }
}

