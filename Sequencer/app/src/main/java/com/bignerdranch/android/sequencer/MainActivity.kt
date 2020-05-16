package com.bignerdranch.android.sequencer

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timerTask

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var bpmView: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var playButton: ImageButton
    private lateinit var bottleButton: Button
    private lateinit var clickButton: Button
    private lateinit var tamborineButton: Button
    private lateinit var met: Metronome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // retrieve references
        bpmView = findViewById(R.id.bpm_view)
        seekBar = findViewById(R.id.seek_bar)
        playButton = findViewById(R.id.play_button)
        met = Metronome(this)
        // met.setContext(this)

        // init seek bar values
        seekBar.setMax(300)
        seekBar.setProgress(100)


        seekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                met.setBpm(progress)
                bpmView.text = "" +  (progress)
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

class Metronome(mainActivity: MainActivity) {

    private lateinit var click: MediaPlayer
    private var metronome: Timer
    private lateinit var context: Context
    private var running: Boolean
    private var bpm: Int
    private var audio: Int

    init {
        metronome = Timer("metronome", true)
        running = false
        bpm = 100
        audio = R.raw.click
    }

    fun Metronome(context: Context){
        this.context = context
        click = MediaPlayer.create(context, audio)
    }


    fun run(): Boolean {
        if (running) {
            return false
        } else {
            running = true
            metronome = Timer("metronome", true)
            metronome.schedule(
                timerTask {
                    Log.d(TAG, "tic")
                    click.start()
                },
                0L,
                (((1000*(60.0/bpm)).toLong()))
            )
            return true
        }
    }

    fun end() {
        metronome.cancel()
        running = false
    }

    fun isRunning(): Boolean {return this.running}
    fun setBpm(bpm: Int){ this.bpm = bpm }
    fun setAudio(audio: Int){this.audio = audio}
    fun getBpm(): Int { return this.bpm }

}

}
