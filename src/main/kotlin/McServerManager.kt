package org.github.ringoame196

import java.io.OutputStreamWriter
import kotlin.concurrent.thread

object McServerManager {
    fun start(mcServer: McServer) : ActiveMcServer? {
        try {
            val processBuilder = ProcessBuilder(
                "java",
                "-Xmx2G", "-Xms2G",
                "-jar", mcServer.jarName,
                "nogui"              // GUIなしで起動
            ).apply {
                directory(mcServer.dir)
                redirectErrorStream(true) // 標準エラー出力を標準出力に統合する
            }

            // 3. プロセスを開始
            val process = processBuilder.start()

            // 4. 別スレッドでサーバーのログ（標準出力）をリアルタイムに読み取る
            thread(start = true, name = "ServerOutputReader") {
                process.inputStream.bufferedReader().use { reader ->
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        // ここでサーバーからの出力をリアルタイムに取得・処理できる
                        println("[Minecraft] $line")

                        // 例: 特定のログが出たら自動で処理したい場合など
                        if (line!!.contains("Done")) {
                            println(">>> マイクラサーバーの起動が完了しました！")
                        }
                    }
                }
            }

            val writer = OutputStreamWriter(process.outputStream, Charsets.UTF_8)

            val activeMcServer = ActiveMcServer(writer,process)
            return activeMcServer
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun stop(activeMcServer: ActiveMcServer) {
        val stopCommand = "stop"
        runCommand(activeMcServer,stopCommand)
    }

    fun runCommand(activeMcServer: ActiveMcServer,command: String) {
        val writer = activeMcServer.writer
        val process = activeMcServer.process
        writer.write("$command\n")
        writer.flush()

        val exitCode = process.waitFor()
        println("サーバープロセスが終了しました。終了コード: $exitCode")
    }
}