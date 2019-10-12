package com.shobeyri.splayer.listFragments.albumFragments


import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.shobeyri.splayer.CallBackMenu

import com.shobeyri.splayer.listFragments.fileManager.FileModel
import kotlinx.android.synthetic.main.fragment_album.*
import android.content.ContentUris
import android.net.Uri
import com.shobeyri.splayer.R


class AlbumFragment : Fragment() , AdapterAlbum.CallBack {
    lateinit var menu : CallBackMenu
    lateinit var list:MutableList<AlbumModel>
    lateinit var songs:MutableList<FileModel>
    lateinit var adapterAlbum : AdapterAlbum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_albums.layoutManager = GridLayoutManager(context,2)
        getAlbums()
    }

    fun getAlbums()
    {
        list = ArrayList()
        var uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
        var columns: Array<String>? = null
        uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
        columns = arrayOf(
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ALBUM_ART,
            MediaStore.Audio.Albums.ARTIST
        )
        val cursor = context!!.contentResolver.query(uri, columns, null,
            null, null)
        if (cursor != null && cursor.moveToFirst())
        {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID))
                val num = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS))
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM))
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST))
                var coverPath : String? = "null"
                coverPath =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
                list.add(AlbumModel(id,num,title,artist,coverPath))
            }while (cursor.moveToNext())
        }
        adapterAlbum = AdapterAlbum(context!!,list,this)
        recycler_albums.adapter = adapterAlbum
        adapterAlbum.notifyDataSetChanged()
    }

    companion object {
        public fun newInstance(menu : CallBackMenu): AlbumFragment {
            val fragment : AlbumFragment = AlbumFragment()
            fragment.menu = menu
            return fragment;
        }
    }

}
