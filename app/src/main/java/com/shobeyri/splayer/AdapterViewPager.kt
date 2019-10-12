package com.shobeyri.splayer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.shobeyri.splayer.BaseFragment
import com.shobeyri.splayer.MainActivity
import com.shobeyri.splayer.TAGS
import com.shobeyri.splayer.listFragments.fileManager.FileManagerFragment

class AdapterViewPager (fragmentManager: FragmentManager , menu : MainActivity) :
    FragmentStatePagerAdapter(fragmentManager)
{
    lateinit var fileFragment : Fragment
    lateinit var albumFragment : Fragment

    lateinit var menu : MainActivity

    init {
        this.menu = menu
    }
    val pages = arrayOf(TAGS.ALBUM,TAGS.ARTIST,TAGS.SONG,TAGS.GENRE,TAGS.FILE)
    override fun getItem(position: Int): Fragment {
        when (position)
        {
            4 ->
            {
                fileFragment = BaseFragment.newInstance(TAGS.FILE,menu)
                return fileFragment
            };
            0 ->
            {
                albumFragment = BaseFragment.newInstance(TAGS.ALBUM,menu)
                return albumFragment
            }
            else -> return BaseFragment.newInstance(pages[position],menu);
        }
        return return BaseFragment.newInstance(pages[0],menu)
    }

    override fun getCount(): Int {
        return pages.size
    }
}