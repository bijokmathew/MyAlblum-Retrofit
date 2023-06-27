package com.example.myalblum_retrofit

import Album
import AlbumItem
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.myalblum_retrofit.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val retrofitService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)
        val resposeLivedata = liveData<Response<Album>> {
            val response = retrofitService.getUserSpecificAlbum(3)
            emit(response)
        }

        val pathResponse = liveData<Response<AlbumItem>> {
            val response = retrofitService.getIndexspecificAlbum(3)
            emit(response)
        }
        pathResponse.observe(this, Observer {
            val album = it.body()
            Toast.makeText(applicationContext,"title is ${album?.title}", Toast.LENGTH_LONG).show()
        })

        resposeLivedata.observe(this, Observer {
            val albumList: MutableListIterator<AlbumItem>? = it.body()?.listIterator()
            if(albumList != null) {
                while(albumList.hasNext()){
                    val albumdetails = albumList.next()
                    val results = " " + "Album Id : ${albumdetails.id} " +"\n"+
                                  " " + "Album name : ${albumdetails.title}" + "\n"+
                                  " " + "User Id : ${albumdetails.userId}"+"\n\n\n"
                    Log.i("MYTAG", results)
                    viewBinding.tvAllbum.append(results)
                }
            }
        })
    }
}