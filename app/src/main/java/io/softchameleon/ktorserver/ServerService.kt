package io.softchameleon.ktorserver

import android.R
import android.app.Service
import android.content.Intent
import android.os.*
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import java.util.concurrent.TimeUnit

class ServerService : Service() {
    private var thread: ServerThread? = null
    private var handler: Handler? = null

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        thread = ServerThread("ServerThread", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()
            this@ServerService.handler = handler
        }

}
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler?.dispatchMessage(Message().apply { arg1 = 1 })
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        handler?.dispatchMessage(Message().apply { arg1 = 0 })
    }
}
