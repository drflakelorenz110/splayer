package com.shobeyri.splayer.listFragments.albumFragments

import com.shobeyri.splayer.BaseModel

class AlbumModel(id:Long,num:Int,title:String,artist:String
,coverPath:String ?= "null")
{
    var id: Long = 0
    var num: Int = 0
    var title: String
    var artist: String
    var coverPath: String? = "null"

    init {
        this.id = id;
        this.num = num;
        this.title = title;
        this.artist = artist;
        this.coverPath = coverPath;
    }
}