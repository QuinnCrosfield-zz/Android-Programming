package com.bignerdranch.android.sequencer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var bpmView: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var playButton: ImageButton
    private lateinit var click: MediaPlayer
    private var bpm: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // retrieve references
        bpmView = findViewById(R.id.bpm_view)
        seekBar = findViewById(R.id.seek_bar)
        playButton = findViewById(R.id.play_button)
        click = MediaPlayer.create(this, R.raw.click)

        // test sound
        click.start()

        seekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                bpm = progress
                bpmView.text = "" +  bpm
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })

        playButton.setOnClickListener {view: View ->
            // do something

        }
    }



}
