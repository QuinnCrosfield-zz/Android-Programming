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
    private lateinit var pauseButton: ImageButton
    private lateinit var stopButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // retrieve references
        bpmView = findViewById(R.id.bpm_view)
        seekBar = findViewById(R.id.seek_bar)
        playButton = findViewById(R.id.play_button)
        pauseButton = findViewById(R.id.pause_button)
        stopButton = findViewById(R.id.stop_button)


        // create metronome
        var met = Metronome()

        seekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                met.setBpm(progress)
                bpmView.text = "" +  progress
            }

            override fun onStartTrackingTouch(seek: SeekBar) {

            }

            override fun onStopTrackingTouch(seek: SeekBar) {

            }
        })

        playButton.setOnClickListener {view: View ->
            if (! met.isRunning()) {
                val t = Thread(met)
                t.start()
                // met.run()
            }
        }

        pauseButton.setOnClickListener {view: View ->
            // met.pause()
        }

        stopButton.setOnClickListener {view: View ->
            met.stop()
        }
    }

    class Metronome : Thread() {

        private var running: Boolean = false
        private lateinit var click: MediaPlayer
        private var bpm: Int = 0

        fun Metronome(){
            this.running = true
        }

        override fun run() {
            while (running) {
                click.start()
                Thread.sleep(((1000*(60.0/bpm)).toLong()))
            }
        }

        public fun end() { running = false }

        fun setBpm(bpm: Int){ this.bpm = bpm }
        fun getBpm(): Int { return this.bpm }
        fun isRunning(): Boolean {return this.running}
    }



}
