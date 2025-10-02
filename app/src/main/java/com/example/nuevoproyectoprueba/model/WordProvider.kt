package com.example.nuevoproyectoprueba.model

object WordProvider {
    private val categories = listOf(
        Category("Animales", listOf("Perro", "Gato", "Elefante", "León", "Tigre", "Jirafa", "Cebra", "Panda")),
        Category("Películas", listOf("Titanic", "Avatar", "Batman", "Inception", "Matrix", "Jurassic Park", "El Rey León")),
        Category("Profesiones", listOf("Doctor", "Ingeniero", "Profesor", "Chef", "Arquitecto", "Abogado", "Bombero"))
    )

    fun getWords(category: String): List<String> {
        return categories.find { it.name == category }?.words ?: emptyList()
    }
}
