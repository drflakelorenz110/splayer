package com.shobeyri.splayer

import androidx.fragment.app.Fragment
import com.shobeyri.splayer.listFragments.albumFragments.AlbumFragment
import com.shobeyri.splayer.listFragments.fileManager.FileManagerFragment

open class BaseFragment{
    companion object {
        public fun newInstance(type : String , menu : MainActivity): Fragment {
            var fragment : Fragment
            when(type) {
                TAGS.SONG -> fragment = FileManagerFragment.newInstance(menu);
                TAGS.ALBUM -> fragment = AlbumFragment.newInstance(menu);
                TAGS.ARTIST -> fragment = FileManagerFragment.newInstance(menu);
                TAGS.FILE -> fragment = FileManagerFragment.newInstance(menu);
                else -> fragment = FileManagerFragment.newInstance(menu);
            }
            return fragment;
        }
    }
}