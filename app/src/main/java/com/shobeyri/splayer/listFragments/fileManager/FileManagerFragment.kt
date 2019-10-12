package com.shobeyri.splayer.listFragments.fileManager


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.shobeyri.splayer.CallBackMenu

import com.shobeyri.splayer.R
import kotlinx.android.synthetic.main.fragment_file_manager.*
import java.io.File
import java.lang.Exception
import java.util.*

class FileManagerFragment : Fragment() , CallBack {


    lateinit var root : View
    var TAGS : String = "Ali FileManagerFragment"
    var levelStack : Stack<String> = Stack();
    var level = 0;
    lateinit var adapter : AdapterFileManager ;
    lateinit var menu : CallBackMenu
    companion object {
        public fun newInstance(menu : CallBackMenu): FileManagerFragment {
            var fragment : FileManagerFragment = FileManagerFragment()
            fragment.menu = menu
            return fragment;
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        root = inflater.inflate(R.layout.fragment_file_manager, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_files.layoutManager = LinearLayoutManager(context)
        getFiles("root",++level);
    }

    fun getFiles (path : String, n : Int)
    {
        txt_path.text = path
        var list : MutableList<FileModel> = ArrayList();

        if(n == 1)
        {
            try
            {
                val appsDir = ContextCompat.getExternalFilesDirs(activity!!, null)
                val extRootPaths = ArrayList<File>()
                for (file in appsDir)
                    extRootPaths.add(file.parentFile.parentFile.parentFile.parentFile)

                try
                {
                    for(file in extRootPaths)
                    {
                        if(file.name.endsWith(".mp3") ||
                            file.name.endsWith(".wav") ||
                            file.name.endsWith(".ogg")||
                            file.name.endsWith(".aac"))
                            list.add(FileModel(file.name,file.absolutePath,true))
                        else// if(anySongHere(file.absolutePath))
                               list.add(FileModel(file.name,file.absolutePath,false))
                    }
                }
                catch (e : Exception)
                {

                }
            }
            catch (e : Exception)
            {

            }
            levelStack.push(path)
        }
        else
        {
            try {
                if(levelStack.size > 0)
                    list.add(FileModel("..","..",false))
                val rootFolder: File = File(path)
                val files = rootFolder.listFiles()
                for(file in files)
                {
                    if(file.name.endsWith(".mp3") ||
                        file.name.endsWith(".wav") ||
                        file.name.endsWith(".ogg")||
                        file.name.endsWith(".aac"))
                        list.add(FileModel(file.name,file.absolutePath,true))
                    else if(anySongHere(file.absolutePath))
                        list.add(FileModel(file.name,file.absolutePath,false))
                }
            }
            catch (e : Exception)
            {

            }
        }
        initRecycler(list)
    }

    fun anySongHere(rootPath: String): Boolean {
        val ret = false
        val rootFolder = File(rootPath)
        val files = rootFolder.listFiles()
        for (file in files!!) {
            if (file.name.endsWith(".mp3") ||
                    file.name.endsWith(".wav")||
                    file.name.endsWith(".ogg")||
                    file.name.endsWith(".aac")) {
                return true
            } else if (file.isDirectory) {
                return anySongHere(file.absolutePath)
            }
        }
        return ret
    }

    fun initRecycler(list : MutableList<FileModel>)
    {
        adapter = AdapterFileManager(FileManagerFragment@this , list)
        recycler_files.adapter = adapter
        recycler_files.smoothScrollToPosition(0)
    }

    override fun goToNextPage(fileModel: FileModel)
    {
        if(fileModel.name.equals(".."))
        {
            prePage()
        }
        else
        {
            getFiles(fileModel.path,++level)
            levelStack.push(fileModel.path)
        }
    }

    override fun prePage() : Boolean
    {
        if(level == 1)
            return true
        else
        {
            levelStack.pop()
            getFiles(levelStack.peek(),--level)
        }

        return  false
    }

    override fun play(list: MutableList<FileModel>, position: Int) {

        var listSongs: MutableList<FileModel> = ArrayList();
        var pos = -1;
        for((i,item) in list.withIndex())
        {
            if(item.isSong)
                listSongs.add(item)
        }

        for((i,item) in listSongs.withIndex())
        {
            if(item.path.equals(list[position].path))
                pos = i;
        }

        menu.play(listSongs,pos)
    }

}
