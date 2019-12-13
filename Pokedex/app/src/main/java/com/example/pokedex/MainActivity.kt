package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btPesquisar.setOnClickListener {
            pesquisar()
        }
    }

    private fun pesquisar() {
        val httpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build();

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        val pokeService = retrofit.create(PokeService::class.java)
        pokeService.buscarPokemon(etNumero.text.toString())
            .enqueue(object : Callback<Pokemon> {
                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        t.message, Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if (response.isSuccessful) {
                        val pokemon = response?.body()
                        Picasso.get()
                            .load(pokemon?.sprites?.back_default)
                            .error(R.mipmap.ic_launcher)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .into(ivImage)
                        Picasso.get()
                            .load(pokemon?.sprites?.back_shiny)
                            .error(R.mipmap.ic_launcher)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .into(ivImage2)

                        tvNome.text = pokemon?.name
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Deu Ruim", Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            })
    }
}
