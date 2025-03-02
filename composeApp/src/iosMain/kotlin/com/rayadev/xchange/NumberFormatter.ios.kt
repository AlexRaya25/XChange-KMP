package com.rayadev.xchange

// iosMain
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

actual class NumberFormatter {
    actual fun format(number: Double): String {
        // Crea un formatter para números
        val formatter = NSNumberFormatter()
        formatter.numberStyle = 3u // NSNumberFormatterDecimalStyle

        // Convierte el número en un NSNumber
        val nsNumber = NSNumber(number)

        // Convierte el NSNumber en un String con el formato adecuado
        return formatter.stringFromNumber(nsNumber)?.toString() ?: "0.00"
    }

    actual companion object {
        actual fun getInstance(): NumberFormatter {
            return NumberFormatter()
        }
    }
}



