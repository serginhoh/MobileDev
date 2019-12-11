package br.com.serginho.pingpong.extensions

import android.widget.EditText

fun EditText.value() = this.text.toString()