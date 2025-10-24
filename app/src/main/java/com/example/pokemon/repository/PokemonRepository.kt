package com.example.pokemon.repository

import androidx.compose.ui.geometry.Offset
import com.example.pokemon.data.remote.PokeApi
import com.example.pokemon.data.remote.response.Pokemon
import com.example.pokemon.data.remote.response.PokemonList
import com.example.pokemon.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {
    suspend fun getPokemonList(limit: Int,offset: Int) : Resource<PokemonList>{
        try {
            return Resource.Success<PokemonList>(date = api.getPokemonList(limit,offset))
        }catch (e : Exception){
            return Resource.Error(date = api.getPokemonList(limit,offset), message = "Some thing gone wrong!" )
        }
    }


    suspend fun getPokemon(name: String) : Resource<Pokemon>{
        try {
            return Resource.Success<Pokemon>(date = api.getPokemon(name))
        }catch (e : Exception){
            return Resource.Error(date = api.getPokemon(name), message = "Some thing gone wrong!" )
        }
    }
}