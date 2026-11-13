package org.github.ringoame196

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

class WebServer(private val port: Int) {
    fun start() {
        embeddedServer(Netty, port = port) {
            routing {
                get("/") {
                    call.respondText("test")
                }
            }
        }.start(wait = true)
    }
}