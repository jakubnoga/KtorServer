package io.softchameleon.ktorserver

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer


class MainActivity : Activity() {
    private lateinit var server: ApplicationEngine
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button
    private var buttons = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn = findViewById<Button>(R.id.run_button)
            .also { it?.setOnClickListener { v -> server = startServer() } }
            .also { buttons.add(it) }

        stopBtn = findViewById<Button>(R.id.stop_button)
            .also { it?.setOnClickListener { v -> stopServer() } }
            .also { buttons.add(it) }
    }

    private fun stopServer() {
        if (!this::server.isInitialized)
            return
        server.stop(gracePeriod = 0, timeout = 0, timeUnit = TimeUnit.SECONDS)
        toggleButtonsEnabled()
    }

    private fun startServer(): ApplicationEngine {
        val server = embeddedServer(CIO, port = 8080) {
            val routing = routing {
                get("/") {
                    call.respondText("Hello World!", ContentType.Text.Plain)
                }
                get("/demo") {
                    call.respondText("HELLO WORLD!")
                }
            }
        }
        server.start(wait = false);
        toggleButtonsEnabled()
        return server
    }

    private fun toggleButtonsEnabled() {
        buttons.forEach { btn -> btn.isEnabled = !btn.isEnabled }
    }
}
