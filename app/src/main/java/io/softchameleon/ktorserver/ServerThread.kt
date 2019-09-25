package io.softchameleon.ktorserver

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.util.KtorExperimentalAPI
import java.util.concurrent.TimeUnit

class ServerThread(name: String, priority: Int) : HandlerThread(name, priority) {
    @KtorExperimentalAPI
    private var server: ApplicationEngine = embeddedServer(CIO, port = 8080) {
        routing {
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            get("/demo") {
                call.respondText("HELLO WORLD!")
            }
        }
    }

    var handler: Handler? = null
        get() {
            if (field == null) {
                field = object : Handler(looper) {
                    override fun handleMessage(msg: Message) {
                        if (msg.arg1 == 1)
                            startServer()
                        else
                            stopServer()
                    }
                }
            }
            return field
        }

    override fun start() {
        super.start()

    }

    private fun stopServer() {
        server.stop(gracePeriod = 0, timeout = 0, timeUnit = TimeUnit.SECONDS)
    }

    private fun startServer() {
        server.start(wait = false);
    }
}