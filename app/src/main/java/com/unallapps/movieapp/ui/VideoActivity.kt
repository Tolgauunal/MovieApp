package com.unallapps.movieapp.ui

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import com.unallapps.movieapp.R
import com.unallapps.movieapp.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {
    private lateinit var binding:ActivityVideoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val link = intent.getStringExtra("videoLink")
        val videoUrl = link
        val videoView: VideoView = binding.youtubePlayer
        val uri: Uri = Uri.parse(videoUrl)
        videoView.setVideoURI(uri)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        mediaController.setMediaPlayer(videoView)
        videoView.setMediaController(mediaController)
        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE)
        videoView.start()
    }
}