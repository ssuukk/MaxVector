package pl.qus.maxvector.service

import com.aallam.openai.api.embedding.EmbeddingRequest
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import org.springframework.stereotype.Service
import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import kotlin.time.Duration.Companion.seconds

@Service
class OpenAIService {
    private val openAI: OpenAI by lazy {
        val openAiConfig = OpenAIConfig(
            token = "kluczyk",
            timeout = Timeout(socket = 60.seconds),
            // additional configurations...
        )
        OpenAI(openAiConfig)
    }

    suspend fun getEmbedding(entries: List<String>): List<PostgresVector> {
        // https://github.com/aallam/openai-kotlin/tree/main

        val response = openAI.embeddings(
            request = EmbeddingRequest(
                model = ModelId("text-similarity-babbage-001"),
                input = entries
            )
        )

        return response.embeddings.map {
            PostgresVector(it.embedding.toMutableList())
        }
    }

    suspend fun getEmbedding(query: String): PostgresVector {
        val response = openAI.embeddings(
            request = EmbeddingRequest(
                model = ModelId("text-similarity-babbage-001"),
                input = listOf(query)
            )
        )

        return PostgresVector(response.embeddings.first().embedding.toMutableList())
    }
}