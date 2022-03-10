package com.example.demo

import com.google.gson.Gson
import org.springframework.web.bind.annotation.*

@RestController
class PokemonController(private val pokemonRepository: PokemonRepository) {

    // curl -v localhost:8083/prueba1
    @GetMapping("prueba1")
    fun prueba1() {
        println("En la base de datos hay ${pokemonRepository.count()}")
        val pokemon = Pokemon("P1",1)
        pokemonRepository.save(pokemon)
        println("En la base de datos ahora hay ${pokemonRepository.count()}")
    }

    // curl -v localhost:8083/mostrarPokemon
    @GetMapping("mostrarPokemon")
    fun mostrarPokemon(): MutableList<Pokemon> {
        return pokemonRepository.findAll()
    }

    // curl -v localhost:8083/actualizarPokemon/1/25
    @GetMapping("actualizarPokemon/{id}/{nuevoNivel}")
    fun actualizarPokemon(@PathVariable id : Int, @PathVariable nuevoNivel : Int) {
        val posiblePokemon = pokemonRepository.findById(id)
        if (posiblePokemon.isPresent) {
            val pokemon = posiblePokemon.get()
            pokemon.nivel = nuevoNivel
            pokemonRepository.save(pokemon)
        }
        pokemonRepository.findAll().forEach { println(it) }
    }

    // curl -v localhost:8083/insertarPokemon/Raticatte/17
    @GetMapping("insertarPokemon/{nombre}/{nivel}")
    fun insertarPokemon(@PathVariable nombre : String, @PathVariable nivel : Int): Pokemon {
        val pokemon = Pokemon(nombre, nivel)
        pokemonRepository.save(pokemon)
        return pokemon
    }

    // curl -v localhost:8083/getPokemonById/3
    @GetMapping("getPokemonById/{pokemonId}")
    fun getPokemonById(@PathVariable pokemonId : Int): Pokemon {
        val pokemon = pokemonRepository.getById(pokemonId)
        return pokemon
    }

    // curl --request GET  --header "Content-type:application/json" --data "{\"nombre\":\"Ditto\", \"nivel\":1}" localhost:8083/insertPokemonBody
    @GetMapping("insertPokemonBody")
    fun insertPokemonBody(@RequestBody texto : String) {
        val gson = Gson()
        val pokemon = gson.fromJson(texto, Pokemon::class.java)
        pokemonRepository.save(pokemon)
        pokemonRepository.findAll().forEach { println(it) }
    }

    // curl --request GET  --header "Content-type:application/json" --data "{\"nombre\":\"Ditto\", \"nivel\":2}" localhost:8083/insertPokemonJson
    @GetMapping("insertPokemonJson")
    fun insertPokemonJson(@RequestBody pokemon : Pokemon) {
        pokemonRepository.save(pokemon)
        pokemonRepository.findAll().forEach { println(it) }
    }

    // curl --request POST  --header "Content-type:application/json" --data "{\"nombre\":\"Ditto\", \"nivel\":3}" localhost:8083/pokemonBody
    @PostMapping("pokemonBody")
    fun insertStudent(@RequestBody pokemon : Pokemon){
        pokemonRepository.save(pokemon)
        pokemonRepository.findAll().forEach { println(it) }
    }
}