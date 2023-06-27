package com.example.myalblum_retrofit

import Album
import AlbumItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumService {

    @GET("/albums")
    suspend fun getAllAlbum(): Response<Album>

    @GET("/albums")
    suspend fun getUserSpecificAlbum(@Query("userId") userId: Int): Response<Album>

    @GET("/albums/{id}")
    suspend fun getIndexspecificAlbum(@Path(value = "id")index: Int):Response<AlbumItem>
}