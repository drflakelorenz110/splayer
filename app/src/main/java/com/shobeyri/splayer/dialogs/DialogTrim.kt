package com.shobeyri.splayer.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import com.edmodo.rangebar.RangeBar
import com.shobeyri.splayer.R
import kotlinx.android.synthetic.main.dialog_trim.*

class DialogTrim(context: Context , callBack : CallBackDialogTrim ,
                 path : String) : Dialog(context) {
    var handlerEn : Boolean = true
    lateinit var player : MediaPlayer
    var callBack : CallBackDialogTrim
    var path : String

    init {
        this.callBack = callBack
        this.path = path

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_trim);

        initHandler()
        initSeekAndRange()
        btn_trim.setOnClickListener(View.OnClickListener
        {

        })
    }

    private fun initSeekAndRange() {
        rangbar_trim.setBarColor(Color.TRANSPARENT)
        seekbar_trim.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                var progress = progress
                player.seekTo(progress * 1000)
                if (progress < rangbar_trim.getLeftIndex()) {
                    progress = rangbar_trim.getLeftIndex()
                    player.seekTo(progress * 1000)
                }
                if (progress > rangbar_trim.getRightIndex()) {
                    progress = rangbar_trim.getRightIndex()
                    player.seekTo(progress * 1000)
                }
            }
        })
        rangbar_trim.setOnRangeBarChangeListener(RangeBar.OnRangeBarChangeListener { rangeBar, i, i1 ->
            seekbar_trim.setProgress(
                i
            )
        })
    }

    private fun initHandler() {
        handlerEn = true
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                seekbar_trim.setProgress(player.getCurrentPosition() / 1000)
                if (handlerEn)
                    handler.postDelayed(this, 1000)
            }
        }, 1000)
    }

    override fun dismiss() {
        handlerEn = false
        player.stop()
        player.release()
        super.dismiss()
    }

    public interface CallBackDialogTrim
    {

    }
}