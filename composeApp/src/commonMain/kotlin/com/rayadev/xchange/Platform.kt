package com.rayadev.xchange

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform