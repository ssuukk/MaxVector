package pl.qus.maxvector.model

import pl.qus.maxvector.model.Author
import java.util.*

data class Author(
    val id: String,
    val firstName: String,
    val lastName: String
) {
    companion object {
        private val authors = listOf<Author>(
            Author("author-1", "Joshua", "Bloch"),
            Author("author-2", "Douglas", "Adams"),
            Author("author-3", "Bill", "Bryson")
        )

        fun getById(id: String): Author? {
            return authors.stream()
                .filter { author: Author -> author.id == id }
                .findFirst()
                .orElse(null)
        }
    }
}