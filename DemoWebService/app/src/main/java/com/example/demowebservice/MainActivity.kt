package com.example.demowebservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient


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
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        val gitHubService = retrofit.create(GitHubService::class.java)
        gitHubService.buscarUsuario(etUsuario.text.toString())
            .enqueue(object : Callback<Usuario>{
                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(this@MainActivity,
                        t.message, Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if(response.isSuccessful){
                        val usuario = response?.body()
                        Picasso.get()
                            .load(usuario?.imagem)
                            .error(R.mipmap.ic_launcher)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .into(ivUsuario)
                        tvUsuario.text = usuario?.nome
                    }
                    else {
                        Toast.makeText(this@MainActivity,
                            "Deu Ruim", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            })
    }
}
