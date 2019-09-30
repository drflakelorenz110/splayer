package com.shobeyri.splayer.listFragments.fileManager


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shobeyri.splayer.R

/**
 * A simple [Fragment] subclass.
 */
class FileManagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_manager, container, false)
    }


}
