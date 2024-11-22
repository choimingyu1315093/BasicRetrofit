package com.example.basicretrofit

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.basicretrofit.model.Albums
import com.example.basicretrofit.model.AlbumsItem
import com.example.basicretrofit.network.AlbumService
import com.example.basicretrofit.network.RetrofitInstance
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var retService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)
        getRequestWithQueryParameters()
        getRequestWithPathParameters()
        uploadAlbum()
    }

    private fun getRequestWithQueryParameters(){
        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retService.getAlbums()
            emit(response)
        }

        responseLiveData.observe(this){
            val albumsList = it.body()?.listIterator()
            if(albumsList != null){
                while (albumsList.hasNext()){
                    val albumsItem = albumsList.next()
                    val result = " "+"Album Title : ${albumsItem.title}"+"\n"+
                            " "+"Album id : ${albumsItem.id}"+"\n"+
                            " "+"User id : ${albumsItem.userId}"+"\n\n\n"
                    val textView: TextView = findViewById(R.id.textView)
                    textView.append(result)
                }
            }
        }
    }

    private fun getRequestWithPathParameters(){
        val pathResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.getAlbum(3)
            emit(response)
        }

        pathResponse.observe(this){
            val title: String? = it.body()?.title
            Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadAlbum(){
        val album = AlbumsItem(0, "My title", 3)
        val postResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.uploadAlbum(album)
            emit(response)
        }

        postResponse.observe(this){
            val receivedAlbumsItem = it.body()
            Log.d("TAG", "uploadAlbum: receivedAlbumsItem $receivedAlbumsItem")
        }
    }
}