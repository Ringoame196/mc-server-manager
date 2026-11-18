package com.github.ringoame196

fun main() {
    val port = 8080
    val dbManager = DataBaseManager("./data.db")
    dbManager.init()
    val webServer = WebServer(port)
    webServer.start()
}
