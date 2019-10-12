package com.shobeyri.splayer.listFragments.fileManager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shobeyri.splayer.R
import com.shobeyri.splayer.TagModel
import java.io.File
import java.util.*

class AdapterFileManager(fragment: FileManagerFragment,list: MutableList<FileModel>
    ) : RecyclerView.Adapter<AdapterFileManager.VH>()
{
    var list : MutableList<FileModel>
    var context : Context
    var fragment : CallBack

    init {
        this.list = list
        this.context = fragment.context!!
        this.fragment = fragment
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var view : View = LayoutInflater.from(context).inflate(R.layout.item_file,parent,false)
        return VH (view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int)
    {
        if(position % 2 == 0)
            holder.lin_main.setBackgroundResource(R.drawable.background_2)

        if(list[position].isSong)
        {
            holder.img_type.setImageResource(R.drawable.icon_music)

            holder.lin_song.visibility = View.VISIBLE
            holder.txt_file.visibility = View.GONE

            var tags : TagModel = TagModel(list[position].path)
            holder.txt_title.text = tags.title
            holder.txt_artist.text = tags.artist

            holder.itemView.setOnClickListener(View.OnClickListener {
                fragment.play(list,position)
            })
        }
        else
        {
            holder.img_type.setImageResource(R.drawable.icon_folder)
            holder.lin_song.visibility = View.GONE
            holder.txt_file.visibility = View.VISIBLE
            holder.txt_file.text = list[position].name

            holder.itemView.setOnClickListener(View.OnClickListener
            {
                if(list[position].name.contains(".."))
                    fragment.prePage()
                else
                    fragment.goToNextPage(list[position])
            })
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    class VH(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        var txt_artist : TextView = itemView.findViewById(R.id.txt_artist);
        var txt_title : TextView = itemView.findViewById(R.id.txt_title);
        var img_type : ImageView = itemView.findViewById(R.id.img_type);
        var lin_song : LinearLayout = itemView.findViewById(R.id.lin_song)
        var lin_main : LinearLayout = itemView.findViewById(R.id.lin_main)
        var txt_file : TextView = itemView.findViewById(R.id.txt_file)
    }
}


interface CallBack
{
    fun goToNextPage(fileModel : FileModel)
    fun prePage() : Boolean
    fun play(list : MutableList<FileModel> , position: Int)
}