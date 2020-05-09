package com.bignerdranch.android.sequencer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timerTask

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var bpmView: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var playButton: ImageButton
    private lateinit var met: Metronome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // retrieve references
        bpmView = findViewById(R.id.bpm_view)
        seekBar = findViewById(R.id.seek_bar)
        playButton = findViewById(R.id.play_button)
        met = Metronome

        seekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                met.setBpm(progress+1)
                bpmView.text = "" +  (progress + 1)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {

            }

            override fun onStopTrackingTouch(seek: SeekBar) {

            }
        })

        playButton.setOnClickListener {view: View ->
            if (! met.isRunning()) {
                Log.d(TAG, "is running")
                met.run()

                // change to stop button
                playButton.setBackgroundResource(R.drawable.ic_stop);
            } else {
                Log.d(TAG, "met stopped")
                met.end()

                // change to stop button
                playButton.setBackgroundResource(R.drawable.ic_play);
            }
        }
    }

}

object Metronome {

    private lateinit var click: MediaPlayer
    private lateinit var metronome: Timer
    private var running: Boolean
    private var bpm: Int

    init {
        metronome = Timer("metronome", true)
        running = false
        bpm = 1
    }

    fun Metronome(){ this.running = true }

    fun run(): Boolean {
        if (running) {
            return false
        } else {
            metronome.schedule(
                timerTask {
                    Log.d(TAG, "tic")
                    click.start()
                    click.stop()
                },
                0L,
                (((1000*(60.0/bpm)).toLong()))
            )
            return true
        }
    }

    fun end() { running = false }
    fun isRunning(): Boolean {return this.running}
    fun setBpm(bpm: Int){ this.bpm = bpm }
    fun getBpm(): Int { return this.bpm }
}
