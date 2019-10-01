package com.shobeyri.splayer.utils

import com.shobeyri.splayer.TagModel

class FireManager
{
    companion object {
        fun getFileTags(path : String) : TagModel
        {
            return TagModel(path)
        }
    }
}