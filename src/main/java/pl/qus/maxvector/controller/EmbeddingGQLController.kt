package pl.qus.maxvector.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import pl.qus.maxvector.model.VectorMetadata
import pl.qus.maxvector.model.PostgresVector
import pl.qus.maxvector.model.*
import pl.qus.maxvector.service.IVectorDatabaseService
import pl.qus.maxvector.service.IEmbeddingService

///////////////////////////////////////////////////////////////////////////
// KONTROLER - ten odpowiada na zapytania GraphQL
///////////////////////////////////////////////////////////////////////////

@Controller
class EmbeddingGQLController {

    @Autowired
    lateinit var database: IVectorDatabaseService

    @Autowired
    lateinit var openAI: IEmbeddingService

    // The @SchemaMapping annotation maps a handler method to a field in the GraphQL schema and declares it to be the
    // DataFetcher for that field. The field name defaults to the method name,
    // and the type name defaults to the simple class name of the source/parent object injected into the method.
    // In this example, the field defaults to VectorMetadata and the type defaults to Embedding.
    @SchemaMapping
    fun metadata(rec: EmbeddingRecord): VectorMetadata? {
        return database.getMetadataById(rec.id)
    }

    @QueryMapping
    fun findClosestByVector(@Argument vec: List<Double>, @Argument k: Int, @Argument measure: DistanceType): List<EmbeddingRecord> =
        database.findClosest(PostgresVector(vec), k, measure)

    @QueryMapping
    suspend fun findClosest(@Argument prompt: String, @Argument k: Int, @Argument measure: DistanceType): List<EmbeddingRecord> {
        val res = openAI.getEmbedding(prompt)
        return database.findClosest(res, k, measure)
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