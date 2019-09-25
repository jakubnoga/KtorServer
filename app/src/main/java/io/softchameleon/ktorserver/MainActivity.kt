package io.softchameleon.ktorserver

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button


class MainActivity : Activity() {
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var serviceIntent: Intent
    private var buttons = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn = findViewById<Button>(R.id.run_button)
            .also {
                it?.setOnClickListener {
                    toggleButtonsEnabled()
                    serviceIntent = Intent(this, ServerService::class.java)
                    startService(serviceIntent)
                }
            }
            .also { buttons.add(it) }

        stopBtn = findViewById<Button>(R.id.stop_button)
            .also {
                it?.setOnClickListener {
                    toggleButtonsEnabled()
                    if (this::serviceIntent.isInitialized)
                        stopService(serviceIntent)
                }
            }
            .also { buttons.add(it) }

    }

    private fun toggleButtonsEnabled() {
        buttons.forEach { btn -> btn.isEnabled = !btn.isEnabled }
    }
}
