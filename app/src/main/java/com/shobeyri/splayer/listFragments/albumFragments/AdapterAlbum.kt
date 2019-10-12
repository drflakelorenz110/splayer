package com.shobeyri.splayer.listFragments.albumFragments

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.media.MediaMetadataRetriever
import com.shobeyri.splayer.R
import java.io.File


class AdapterAlbum(context: Context , list: MutableList<AlbumModel> , fragment: CallBack) : RecyclerView.Adapter<AdapterAlbum.VH>() {

    lateinit var context : Context
    lateinit var list : MutableList<AlbumModel>
    lateinit var fragment : CallBack

    init {
        this.context = context
        this.list = list
        this.fragment = fragment
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var view : View = LayoutInflater.from(context).inflate(R.layout.item_album,parent,false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        var backgroundRes : Int = R.drawable.background_rounded_1
        when(position % 4)
        {
            0 -> {backgroundRes = R.drawable.background_rounded_1}
            1 -> {backgroundRes = R.drawable.background_rounded_2}
            2 -> {backgroundRes = R.drawable.background_rounded_3}
            3 -> {backgroundRes = R.drawable.background_rounded_4}
        }
        holder.view_background.setBackgroundResource(backgroundRes)

        holder.txt_artist.text = list[position].artist
        holder.txt_title.text = list[position].title

        var num : Int = list[position].num

        if(num < 2)
            holder.txt_num.text =  "$num song"
        else
            holder.txt_num.text =  "$num songs"


        if(list[position].coverPath.equals(null))
            holder.img_albumCover.setImageResource(R.drawable.icon_album)
        else
        {
            val cover = BitmapFactory.decodeFile(list[position].coverPath)
            holder.img_albumCover.setImageBitmap(cover)
            holder.img_albumCover.setPadding(2,2,2,2)
        }

    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class VH(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        lateinit var txt_artist : TextView
        lateinit var txt_title : TextView
        lateinit var img_albumCover : ImageView
        lateinit var txt_num : TextView
        lateinit var view_background : View

        init {
            txt_artist = itemView.findViewById(R.id.txt_artist)
            txt_title = itemView.findViewById(R.id.txt_title)
            img_albumCover = itemView.findViewById(R.id.img_albumCover)
            txt_num = itemView.findViewById(R.id.txt_num)
            view_background = itemView.findViewById(R.id.view_background)
        }
    }



    interface CallBack
    {
//        fun goToNextPage(fileModel : FileModel)
    }

}