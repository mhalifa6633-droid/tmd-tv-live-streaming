package com.tmdtv.live

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var channelRecyclerView: RecyclerView

    private val channels = listOf(
        Channel("TMD News", "https://storage.googleapis.com/exoplayer-test-media-1/m3u8/ED_1024_1080p_60fps_5mbps.m3u8"),
        Channel("TMD Sports", "https://storage.googleapis.com/exoplayer-test-media-1/m3u8/ED_4096_2160p_60fps_60mbps.m3u8"),
        Channel("TMD Movies", "https://storage.googleapis.com/exoplayer-test-media-1/m3u8/ED_1024_1080p_60fps_5mbps.m3u8"),
        Channel("TMD Music", "https://storage.googleapis.com/exoplayer-test-media-1/m3u8/ED_4096_2160p_60fps_60mbps.m3u8")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerView = findViewById(R.id.playerView)
        channelRecyclerView = findViewById(R.id.channelRecyclerView)

        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        channelRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ChannelAdapter(channels) { channel ->
            playStream(channel)
        }
        channelRecyclerView.adapter = adapter

        playStream(channels.first())
    }

    private fun playStream(channel: Channel) {
        val mediaItem = MediaItem.fromUri(channel.streamUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
        player.repeatMode = androidx.media3.common.C
    }

    override fun onStop() {
        super.onStop()
        player.pause()
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }
}
