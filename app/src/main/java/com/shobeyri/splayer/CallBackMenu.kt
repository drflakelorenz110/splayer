package com.shobeyri.splayer

import com.shobeyri.splayer.listFragments.fileManager.FileModel

interface CallBackMenu {
    fun onBack()
    fun play(list: MutableList<FileModel>, position: Int)
}