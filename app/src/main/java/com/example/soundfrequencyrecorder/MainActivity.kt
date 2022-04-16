package com.example.soundfrequencyrecorder

import android.annotation.SuppressLint
import android.media.AudioFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlin.concurrent.thread
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    private val SAMPLING_RATE:Int=44100

    private var bufSize: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bufSize = AudioRecord.getMinBufferSize(SAMPLING_RATE,
            AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT)
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()

        //Audio recordの生成
        var audioRec = AudioRecord(MediaRecorder.AudioSource.MIC,
            SAMPLING_RATE,AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,bufSize * 2)

        audioRec.startRecording()

        var fft =Thread (Runnable {
            run(audioRec)
        })

        fft.start()
    }

    private fun run(audioRec:AudioRecord){
        var buf:ByteArray = ByteArray(bufSize*2)

        //レコーディング中
        while(audioRec.recordingState==AudioRecord.RECORDSTATE_RECORDING){
            //録音データ読み込み
            audioRec.read(buf,0,buf.size)

            //エンディアン変換
        }

        //録音停止
        audioRec.stop()
        audioRec.release()
    }
}