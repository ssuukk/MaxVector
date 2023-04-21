package pl.qus.maxvector.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.Author
import pl.qus.maxvector.model.Book
import pl.qus.maxvector.model.DAOEmbedding
import pl.qus.maxvector.model.GQLEmbedding
import pl.qus.maxvector.repository.EmbeddingRepository
import pl.qus.maxvector.service.IEmbeddingService
import pl.qus.maxvector.service.OpenAIService

///////////////////////////////////////////////////////////////////////////
// KONTROLER - ten odpowiada na zapytania GraphQL
///////////////////////////////////////////////////////////////////////////

@Controller
class EmbeddingGQLController {

    @Autowired
    lateinit var embeddingService: IEmbeddingService

    @Autowired
    lateinit var openAIService: OpenAIService

    @Autowired
    lateinit var repository: EmbeddingRepository

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
    fun embeddingByClosest(@Argument vec: Double, @Argument k: Int): List<GQLEmbedding> {
        val vec = PostgresVector(mutableListOf(vec,2.0,3.0))
        val found = embeddingService.findClosest(vec, k)
        return found.map {GQLEmbedding.from(it)}
    }

    @MutationMapping
    suspend fun storeEmbedding(@Argument queries:List<String>) {
        val embs = openAIService.getEmbedding(queries).map {
            DAOEmbedding().apply {
                this.embedding = it
            }
        }
        repository.saveAll(embs)

    }
}