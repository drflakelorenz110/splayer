package com.shobeyri.splayer.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.shobeyri.splayer.R
import ealvatag.audio.AudioFileIO
import ealvatag.tag.FieldKey
import ealvatag.tag.NullTag
import kotlinx.android.synthetic.main.dialog_edit.*
import java.io.File

class DialogEdit(context: Context , callBack : CallBackDialogEdit , dir : String , title : String , artist : String) : Dialog(context) {
    var dir : String
    var artist : String
    var title : String
    var callBack : CallBackDialogEdit
    init {
        this.artist = artist
        this.title = title
        this.dir = dir
        this.callBack = callBack
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit)
        getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);

        edit_artist.setText(artist)
        edit_title.setText(title)
        btn_change.setOnClickListener(View.OnClickListener
        {
            if(!edit_title.text.toString().isEmpty() && !edit_artist.text.toString().isEmpty())
            {
                val file = File(dir)
                try {
                    val audioFile = AudioFileIO.read(file)
                    val audioHeader = audioFile.audioHeader
                    val tag = audioFile.tag.or(NullTag.INSTANCE)
                    tag.setField(FieldKey.TITLE, edit_title.getText().toString())
                    tag.setField(FieldKey.ARTIST, edit_artist.getText().toString())
                    audioFile.save()
                    Toast.makeText(context, "saved !", Toast.LENGTH_SHORT).show()
                    callBack.change(edit_title.text.toString(),edit_artist.text.toString());
                    dismiss()
                } catch (e: Exception) {
                    Toast.makeText(context, "error ! ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            else
                Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
        })
    }

    public interface CallBackDialogEdit
    {
        fun change(title : String , artist : String)
    }
}