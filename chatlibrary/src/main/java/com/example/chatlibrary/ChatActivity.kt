package com.example.chatlibrary


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatlibrary.databinding.ActivityChatBinding
import okhttp3.*
import okio.ByteString

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatAdapter
    private var webSocket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        connectWebSocket()

        binding.sendButton.setOnClickListener {
            val message = binding.messageInput.text.toString()
            if (message.isNotBlank()) {
                adapter.addMessage(Message(message, true))
                webSocket?.send(message)
                binding.messageInput.text.clear()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun connectWebSocket() {
        val client = OkHttpClient()
        val request = Request.Builder().url("wss://echo.websocket.org").build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                runOnUiThread {
                    val displayText = if (text == "203 = 0xcb") "🎉 Special Code Received!" else text
                    adapter.addMessage(Message(displayText, false))
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                runOnUiThread {
                    adapter.addMessage(Message("📦 Binary: ${bytes.hex()}", false))
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocket?.close(1000, null)
    }
}