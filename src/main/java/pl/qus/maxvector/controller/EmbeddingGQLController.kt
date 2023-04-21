package pl.qus.maxvector.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.*
import pl.qus.maxvector.service.IDatabaseService
import pl.qus.maxvector.service.OpenAIService

///////////////////////////////////////////////////////////////////////////
// KONTROLER - ten odpowiada na zapytania GraphQL
///////////////////////////////////////////////////////////////////////////

@Controller
class EmbeddingGQLController {

    @Autowired
    lateinit var database: IDatabaseService

    @Autowired
    lateinit var openAI: OpenAIService

    // https://spring.io/guides/gs/graphql-server/
    // By defining a method named bookById annotated with @QuerMapping, this controller declares how to fetch a Book
    // as defined under the Query type. The query field is determined from the method name,
    // but can also be declared on the annotation itself.
    @QueryMapping
    fun bookById(@Argument id: String): Book? {
        return Book.getById(id)
    }

    // The @SchemaMapping annotation maps a handler method to a field in the GraphQL schema and declares it to be the
    // DataFetcher for that field. The field name defaults to the method name,
    // and the type name defaults to the simple class name of the source/parent object injected into the method.
    // In this example, the field defaults to author and the type defaults to Book.
    @SchemaMapping
    fun author(book: Book): Author? {
        return Author.getById(book.authorId)
    }

    @QueryMapping
    fun findClosestByVector(@Argument vec: List<Double>, @Argument k: Int): List<GQLEmbeddingRecord> {
        val found = database.findClosestEuclidean(PostgresVector(vec), k)
        return found.map {GQLEmbeddingRecord.from(it)}
    }

    @QueryMapping
    suspend fun findClosest(@Argument prompt: String, @Argument k: Int): List<GQLEmbeddingRecord> {
        val res = openAI.getEmbedding(prompt)
        val found = database.findClosestEuclidean(res, k)
        return found.map {GQLEmbeddingRecord.from(it)}
    }

    @QueryMapping
    fun deleteById(@Argument id: Long): Boolean {
        return database.deleteById(id)
    }

    @MutationMapping
    suspend fun storeEmbedding(@Argument queries:List<String>) : OpenAIStatus {
        return try {
            val zipped = openAI.getEmbedding(queries).zip(queries)

            zipped.forEach {
                database.insert(
                    EmbeddingRecord().apply {
                        this.embedding = it.first
                        this.label = it.second
                    }
                )
            }

            OpenAIStatus(true, "", queries.size)
        }
        catch (ex: Exception) {
            OpenAIStatus(false, ex.message ?: "Unknown error", 0)
        }

    }
}