package com.shobeyri.splayer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.OnDismissListener
import com.orhanobut.dialogplus.ViewHolder
import com.shobeyri.splayer.components.TextViewNormal
import com.shobeyri.splayer.dialogs.DialogEdit
import com.shobeyri.splayer.dialogs.DialogTrim
import com.shobeyri.splayer.listFragments.fileManager.FileManagerFragment
import com.shobeyri.splayer.listFragments.fileManager.FileModel
import com.shobeyri.splayer.utils.Funs
import ealvatag.audio.AudioFileIO
import ealvatag.tag.FieldKey
import ealvatag.tag.NullTag
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_player.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() , CallBackMenu , DialogEdit.CallBackDialogEdit , DialogTrim.CallBackDialogTrim {


    lateinit var adapterViewPager: AdapterViewPager

    lateinit var player : MediaPlayer
    var list: MutableList<FileModel> = ArrayList();
    var position : Int = -1;
    var isShuffle = false
    var listShufflePos: MutableList<Int> = ArrayList();
    var timerSeekbarEn : Boolean = false

    var artist = "UnKnown"
    var title = "UnKnown"
    lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(getPermission())
        {
            adapterViewPager = AdapterViewPager(supportFragmentManager, this)
            pager.adapter = adapterViewPager
        }
    }

    fun getPermission() : Boolean
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true
            } else {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
                return false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            adapterViewPager = AdapterViewPager(supportFragmentManager, this)
            pager.adapter = adapterViewPager
        }
    }

    override fun onBack() {
        onBackPressed()
    }

    override fun onBackPressed() {
        if(pager.currentItem == 4)
        {
            if((adapterViewPager.fileFragment as FileManagerFragment).prePage())
                finishApp()
        }
    }


    var playerDialog : DialogPlus ?= null
    override fun play(list: MutableList<FileModel> , position: Int)
    {
        val file = File(list.get(position).path)
        if(file.exists())
        {
            lin_player.visibility = View.VISIBLE
            MainActivity@this.list = list
            MainActivity@this.position = position

            uri = Uri.fromFile(file)
            player = MediaPlayer.create(applicationContext,uri)

            btn_play_main.alpha = 1f;
            btn_play_main.setImageResource(R.drawable.icon_play)
            btn_play_main.isEnabled = true
            btn_stop_main.isEnabled = true

            // Init buttons
            btn_play_main.setOnClickListener(View.OnClickListener
            {
                if(player.isPlaying)
                {
                    btn_play.setImageResource(R.drawable.icon_pause)
                    player.pause()
                }
                else
                {
                    btn_play.setImageResource(R.drawable.icon_play)
                    player.start()
                }
            })
            btn_stop_main.setOnClickListener(View.OnClickListener
            {
                player.stop()
                btn_play_main.alpha = TAGS.ALPHA;
                btn_play_main.setImageResource(R.drawable.icon_pause)
                btn_play_main.isEnabled = false
                btn_stop_main.isEnabled = false
            })
            btn_next_main.setOnClickListener(View.OnClickListener
            {
                if(!isShuffle)
                {
                    if(position +1 < list.size)
                        play(list,position+1)
                    else
                        play(list,0)
                }
                else
                {
                    if(listShufflePos.size == list.size)
                        listShufflePos = ArrayList();
                    val rand : Random = Random();
                    while(true)
                    {
                        val n : Int = rand.nextInt(list.size)
                        if(!listShufflePos.contains(n))
                        {
                            listShufflePos.add(n)
                            play(list,n)
                            break
                        }
                    }
                }

                if(playerDialog != null && playerDialog!!.isShowing)
                {
                    txt_title.text = title
                    txt_artist.text = artist
                }
            })
            btn_pre_main.setOnClickListener(View.OnClickListener
            {
                if(!isShuffle)
                {
                    if(position -1 > 0)
                        play(list,position-1)
                    else
                        play(list,list.size-1)
                }
                else
                {
                    if(!listShufflePos.isEmpty())
                    {
                        if(listShufflePos.size > 1)
                        {
                            play(list,listShufflePos[listShufflePos.size-2])
                            listShufflePos.removeAt(listShufflePos.size-1)
                        }
                        else
                        {
                            listShufflePos = ArrayList()
                            val rand : Random = Random();
                            while(true)
                            {
                                val n : Int = rand.nextInt(list.size)
                                if(!listShufflePos.contains(n))
                                {
                                    listShufflePos.add(n)
                                    play(list,n)
                                    break
                                }
                            }

                        }
                    }
                    else // useLess else
                    {
                        listShufflePos = ArrayList()
                        val rand : Random = Random();
                        while(true)
                        {
                            val n : Int = rand.nextInt(list.size)
                            if(!listShufflePos.contains(n))
                            {
                                listShufflePos.add(n)
                                play(list,n)
                                break
                            }
                        }
                    }
                }

                if(playerDialog != null && playerDialog!!.isShowing)
                {
                    txt_title.text = title
                    txt_artist.text = artist
                }
            })

            // Init image cover

            val mmr = MediaMetadataRetriever()
            val rawArt: ByteArray?
            val art: Bitmap
            val bfo = BitmapFactory.Options()
            mmr.setDataSource(applicationContext, uri)
            rawArt = mmr.embeddedPicture
            if (null != rawArt)
            {
                art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.size, bfo)
                img_cover_main.setImageBitmap(art)
            }
            else
                img_cover_main.setImageResource(Funs.generateImageId())

            val audioFile = AudioFileIO.read(file)
            val audioHeader = audioFile.audioHeader
            val tag = audioFile.tag.or(NullTag.INSTANCE)
            title = tag.getValue(FieldKey.TITLE).or("unknown")
            artist = tag.getValue(FieldKey.ARTIST).or("unknown")
        }
        else
        {
            // TODO handle else
        }

        img_cover_main.setOnClickListener(View.OnClickListener {
            showPlayer()
        })

        if(playerDialog == null)
            showPlayer()

        timerSeekbarEn = true
        timerSeekbar = Handler()
        runnable = Runnable {
            if(playerDialog!= null && playerDialog!!.isShowing)
                seekBar.setProgress(player.currentPosition/1000)

            player.setOnCompletionListener {
                btn_next_main.performClick()
            }
            if(timerSeekbarEn)
                timerSeekbar.postDelayed(runnable,1000)
        }
        timerSeekbar = Handler()
        timerSeekbar.postDelayed(runnable,1000)

    }

    lateinit var timerSeekbar : Handler
    lateinit var runnable : Runnable



    lateinit var seekBar : SeekBar
    lateinit var btn_play : ImageView
    lateinit var btn_stop : ImageView
    lateinit var btn_next : ImageView
    lateinit var btn_pre : ImageView
    lateinit var btn_edit : ImageView
    lateinit var btn_share : ImageView
    lateinit var btn_shuffle : ImageView
    lateinit var txt_title : TextViewNormal
    lateinit var txt_artist : TextViewNormal
    fun showPlayer()
    {
        val v = LayoutInflater.from(MainActivity@this).inflate(R.layout.dialog_player, null)
        playerDialog = DialogPlus.newDialog(MainActivity@this)
            .setContentHolder(ViewHolder(v))
            .setCancelable(true)
            .setContentBackgroundResource(android.R.color.transparent)
            .setMargin(32, 0, 32, 0)
            .create();


        seekBar = v.findViewById(R.id.seekBar)
        btn_play = v.findViewById(R.id.btn_play)
        btn_stop = v.findViewById(R.id.btn_stop)
        btn_next = v.findViewById(R.id.btn_next)
        btn_pre = v.findViewById(R.id.btn_pre)
        txt_title = v.findViewById(R.id.txt_title)
        txt_artist = v.findViewById(R.id.txt_artist)
        btn_edit = v.findViewById(R.id.btn_edit)
        btn_share = v.findViewById(R.id.btn_share)
        btn_shuffle = v.findViewById(R.id.btn_shuffle)
        seekBar.max = player.duration / 1000
        btn_play.setOnClickListener(View.OnClickListener
        {
            if(player.isPlaying)
            {
                btn_play.setImageResource(R.drawable.icon_pause)
                player.pause()
            }
            else
            {
                btn_play.setImageResource(R.drawable.icon_play)
                player.start()
            }
        })
        btn_stop.setOnClickListener(View.OnClickListener
        {
            player.stop()
            btn_play.alpha = TAGS.ALPHA;
            btn_play.setImageResource(R.drawable.icon_play)
            btn_play.isEnabled = false
            btn_stop.isEnabled = false
        })
        btn_next.setOnClickListener(View.OnClickListener
        {
            if(!isShuffle)
            {
                if(position +1 < list.size)
                    play(list,position+1)
                else
                    play(list,0)
            }
            else
            {
                if(listShufflePos.size == list.size)
                    listShufflePos = ArrayList();
                val rand : Random = Random();
                while(true)
                {
                    val n : Int = rand.nextInt(list.size)
                    if(!listShufflePos.contains(n))
                    {
                        listShufflePos.add(n)
                        play(list,n)
                        break
                    }
                }
            }

            if(playerDialog != null && playerDialog!!.isShowing)
            {
                txt_title.text = title
                txt_artist.text = artist
            }
        })
        btn_pre.setOnClickListener(View.OnClickListener
        {
            if(!isShuffle)
            {
                if(position -1 > 0)
                    play(list,position-1)
                else
                    play(list,list.size-1)
            }
            else
            {
                if(!listShufflePos.isEmpty())
                {
                    if(listShufflePos.size > 1)
                    {
                        play(list,listShufflePos[listShufflePos.size-2])
                        listShufflePos.removeAt(listShufflePos.size-1)
                    }
                    else
                    {
                        listShufflePos = ArrayList()
                        val rand : Random = Random();
                        while(true)
                        {
                            val n : Int = rand.nextInt(list.size)
                            if(!listShufflePos.contains(n))
                            {
                                listShufflePos.add(n)
                                play(list,n)
                                break
                            }
                        }

                    }
                }
                else // useLess else
                {
                    listShufflePos = ArrayList()
                    val rand : Random = Random();
                    while(true)
                    {
                        val n : Int = rand.nextInt(list.size)
                        if(!listShufflePos.contains(n))
                        {
                            listShufflePos.add(n)
                            play(list,n)
                            break
                        }
                    }
                }
            }

            if(playerDialog != null && playerDialog!!.isShowing)
            {
                txt_title.text = title
                txt_artist.text = artist
            }
        })
        btn_edit.setOnClickListener(View.OnClickListener
        {
            val dialog : DialogEdit = DialogEdit(MainActivity@this,this,list.get(position).path,
                title,artist)
            dialog.show()
        })
        btn_share.setOnClickListener(View.OnClickListener
        {
            val intentShareFile = Intent(Intent.ACTION_SEND)
            val fileWithinMyDir = File(list.get(position).path)

            if (fileWithinMyDir.exists()) {
                intentShareFile.type = "application/pdf"
                intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$list.get(position).path"))

                intentShareFile.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Sharing File..."
                )
                intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...")
                startActivity(Intent.createChooser(intentShareFile, "Share File"))
            }
        })
        btn_shuffle.setOnClickListener(View.OnClickListener
        {
            isShuffle = !isShuffle
            if(isShuffle)
                Toast.makeText(MainActivity@this,"Shuffle On",Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(MainActivity@this,"Shuffle Off",Toast.LENGTH_SHORT).show()
        })
        btn_trim.setOnClickListener(View.OnClickListener
        {
            if(player.isPlaying)
                player.pause()
            val dialog : DialogTrim = DialogTrim(MainActivity@this,this,list.get(position).path)
            dialog.show()
        })
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser && btn_stop.isEnabled)
                    player.seekTo(progress * 1000)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        val mmr = MediaMetadataRetriever()
        val rawArt: ByteArray?
        val art: Bitmap
        val bfo = BitmapFactory.Options()
        mmr.setDataSource(applicationContext,uri)
        rawArt = mmr.embeddedPicture
        if (null != rawArt)
        {
            art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.size, bfo)
            img_cover_main.setImageBitmap(art)
        }
        else
            img_cover_main.setImageResource(Funs.generateImageId())

        txt_title.text = title
        txt_artist.text = artist

        playerDialog!!.show()
    }

    override fun change(titleNew: String, artistNew: String) {
        if(playerDialog != null && playerDialog!!.isShowing)
        {
            txt_artist.setText(artistNew)
            txt_title.setText(titleNew)

            artist = artistNew
            title = titleNew
        }
    }

    fun finishApp()
    {
        finish()
    }
}
