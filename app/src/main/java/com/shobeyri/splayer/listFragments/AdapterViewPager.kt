package com.shobeyri.splayer.listFragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.shobeyri.splayer.BaseFragment
import com.shobeyri.splayer.TAGS
import com.shobeyri.splayer.listFragments.fileManager.FileManagerFragment

class AdapterViewPager (fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager)
{
    lateinit var fileFragment : Fragment
    val pages = arrayOf(TAGS.ALBUM,TAGS.ARTIST,TAGS.SONG,TAGS.GENRE,TAGS.FILE)
    override fun getItem(position: Int): Fragment {
        when (position)
        {
            5 ->
            {
                fileFragment = BaseFragment.newInstance(TAGS.FILE)
                return fileFragment
            };
            else -> return BaseFragment.newInstance(pages[position]);
        }
        return return BaseFragment.newInstance(pages[0])
    }

    override fun getCount(): Int {
        return pages.size
    }
}