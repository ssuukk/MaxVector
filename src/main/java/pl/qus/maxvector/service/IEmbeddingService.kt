package pl.qus.maxvector.service

import pl.qus.maxvector.model.EmbVector

interface IEmbeddingService {
    suspend fun getEmbedding(entries: List<String>): List<EmbVector>
    suspend fun getEmbedding(query: String): EmbVector
}