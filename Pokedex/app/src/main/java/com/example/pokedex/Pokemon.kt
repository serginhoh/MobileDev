package com.example.pokedex

data class Pokemon (
    val id : Int,
    val name : String,
    val sprites: Sprites
)

data class Sprites(
    val back_default : String,
    val back_shiny : String
)