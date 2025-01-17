package com.izivia.ocpp.wamp

import kotlin.time.Duration

fun main(args:Array<String>) {
    WampLoadTest().loadTest(
        server = WampLoadTest.NoopServerManager,
        port = 54003,
        startFromCSNumber = args.firstOrNull()?.toInt()?: 0,
        chargingStationNumber = 3000,
        heartbeatsNumber = 100000,
        delayAfterConnection = Duration.parse("15s")
    )
    System.exit(0)
}
