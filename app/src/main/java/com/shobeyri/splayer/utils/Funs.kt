package com.shobeyri.splayer.utils

import com.shobeyri.splayer.R
import com.shobeyri.splayer.TagModel
import java.util.*

class Funs
{
    companion object {
        fun generateImageId() : Int
        {
            val random = Random()
            val covers = intArrayOf(R.drawable.cover_1,R.drawable.cover_2,R.drawable.cover_3)
            val n = random.nextInt(covers.size)
            return n
        }
    }
}