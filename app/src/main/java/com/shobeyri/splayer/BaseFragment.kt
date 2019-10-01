package com.shobeyri.splayer

import androidx.fragment.app.Fragment
import com.shobeyri.splayer.listFragments.fileManager.FileManagerFragment

open class BaseFragment{
    companion object {
        public fun newInstance(type : String): Fragment {
            var fragment : Fragment
            when(type) {
                TAGS.SONG -> fragment = FileManagerFragment.newInstance();
                TAGS.ALBUM -> fragment = FileManagerFragment.newInstance();
                TAGS.ARTIST -> fragment = FileManagerFragment.newInstance();
                TAGS.FILE -> fragment = FileManagerFragment.newInstance();
                else -> fragment = FileManagerFragment.newInstance();
            }
            return fragment;
        }
    }
}