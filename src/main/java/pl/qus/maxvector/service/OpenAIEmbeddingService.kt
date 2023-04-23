package pl.qus.maxvector.service

import com.aallam.openai.api.embedding.EmbeddingRequest
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pl.qus.maxvector.model.EmbVector
import kotlin.time.Duration.Companion.seconds

@Service
class OpenAIEmbeddingService(@Value("\${openAIapiKey}")
                    private val openAIapiKey: String) : IEmbeddingService {

    private val openAI: OpenAI by lazy {
        val openAiConfig = OpenAIConfig(
            token = openAIapiKey,
            timeout = Timeout(socket = 60.seconds),
            // additional configurations...
        )
        OpenAI(openAiConfig)
    }

    override suspend fun getEmbedding(entries: List<String>): List<EmbVector> {
        // https://github.com/aallam/openai-kotlin/tree/main

        val response = openAI.embeddings(
            request = EmbeddingRequest(
                model = ModelId("text-similarity-babbage-001"),
                input = entries
            )
        )

        return response.embeddings.map {
            EmbVector(it.embedding.toMutableList())
        }
    }

    override suspend fun getEmbedding(query: String): EmbVector {
        val response = openAI.embeddings(
            request = EmbeddingRequest(
                model = ModelId("text-similarity-babbage-001"),
                input = listOf(query)
            )
        )

        return EmbVector(response.embeddings.first().embedding.toMutableList())
    }
}