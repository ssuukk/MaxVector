package pl.qus.maxvector.service

import pl.qus.maxvector.hibernate.customtypes.PostgresVector

interface IEmbeddingService {
    suspend fun getEmbedding(entries: List<String>): List<PostgresVector>
    suspend fun getEmbedding(query: String): PostgresVector
}