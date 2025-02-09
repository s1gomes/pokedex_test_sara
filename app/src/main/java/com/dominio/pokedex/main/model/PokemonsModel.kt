package com.dominio.pokedex.main.model

import com.google.gson.annotations.SerializedName

data class PokemonsModel(
    val results: List<Pokemon>
)

data class Pokemon(
    val name: String,
    val url: String
)

data class PokemonDetail(
    val name: String,
    val base_experience: String,
    val id: String,
    val moves: List<MoveItem>,
    val abilities: List<AbilityItem>,
    val types: List<TypeItem>,
    val sprites: Other,

)

data class MoveItem(
    val move: Move
)
data class Move(
    val name: String
)
data class TypeItem(
    val slot: String,
    val type: Type
)
data class Type(
    val name: String,
)
data class AbilityItem(
    val ability: Ability,
)
data class Ability(
    val name: String
)

data class Other(
    val other: ArtItem

)
data class ArtItem(
    @SerializedName("official-artwork")
    val officialArtwork: OficialArtWork,
    val dream_world: Dream_world,
    val home: Home
)
data class OficialArtWork(
    val front_default: String,
    val front_shiny: String
)
data class Dream_world(
    val front_default: String
)
data class Home(
    val front_default: String
)