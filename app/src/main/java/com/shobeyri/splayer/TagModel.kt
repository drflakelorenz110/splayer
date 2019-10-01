package com.shobeyri.splayer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import ealvatag.audio.AudioFileIO
import ealvatag.tag.FieldKey
import ealvatag.tag.NullTag
import java.io.File
import java.util.*



class TagModel(path : String)
{
    var path : String
    var title : String
    var artist : String
    val lyrics : String

    init {
        this.path = path
        val file = File(path)
        val audioFile = AudioFileIO.read(file)
        val tag = audioFile.getTag().or(NullTag.INSTANCE)

        title = tag.getValue(FieldKey.TITLE).or("unknown")
        artist = tag.getValue(FieldKey.ARTIST).or("unknown")
        lyrics = tag.getValue(FieldKey.LYRICS).or("")
    }

    fun getTrackArt(context : Context) : Bitmap
    {
        val file = File(path)
        val uri = Uri.fromFile(file)

        val mmr = MediaMetadataRetriever()
        val rawArt: ByteArray?
        val art: Bitmap
        val bfo = BitmapFactory.Options()
        mmr.setDataSource(context,uri)
        rawArt = mmr.embeddedPicture
        if (null != rawArt)
            art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.size, bfo)
        else
            art = BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_folder)
        return art
    }
}
