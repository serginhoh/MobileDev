package com.example.pokedex

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {
    @GET("pokemon/{numero}/")
    fun buscarPokemon(@Path("numero")id: String) :
            Call<Pokemon>
}