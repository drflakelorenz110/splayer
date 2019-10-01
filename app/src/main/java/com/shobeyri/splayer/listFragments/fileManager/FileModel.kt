package com.shobeyri.splayer.listFragments.fileManager

import com.shobeyri.splayer.BaseModel

class FileModel(name : String ,
                path : String ,
                isSong : Boolean) : BaseModel(name)
{
    var path : String
    var isSong : Boolean

    init {
        this.path = path
        this.isSong = isSong
    }

    override fun toString(): String {
        return "Name : $name , Path : $path , IsMP3 : $isSong"
    }
}