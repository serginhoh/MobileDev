package br.com.serginho.pingpong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var homeScore = 0
    private var awayScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpView()
    }

    private fun setUpView() {
        tvHomeName.text = intent.getStringExtra("HOME_NAME")
        tvAwayName.text = intent.getStringExtra("AWAY_NAME")
        setUpListener()
    }

    private fun setUpListener() {
        btHomeScore.setOnClickListener {
            homeScore ++
            tvHomeScore.text = homeScore.toString()
        }

        btAwayScore.setOnClickListener {
            awayScore ++
            tvAwayScore.text = awayScore.toString()
        }
    }
}
