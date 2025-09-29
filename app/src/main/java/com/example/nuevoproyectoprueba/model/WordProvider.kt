package com.example.nuevoproyectoprueba.model

object WordProvider {
    private val categories = listOf(
        Category("Animales", listOf("Perro", "Gato", "Elefante")),
        Category("Películas", listOf("Titanic", "Avatar", "Batman")),
        Category("Profesiones", listOf("Doctor", "Ingeniero", "Profesor"))
    )

    fun getWords(category: String): List<String> {
        return categories.find { it.name == category }?.words ?: emptyList()
    }
}
