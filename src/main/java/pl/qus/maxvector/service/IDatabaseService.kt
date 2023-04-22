package pl.qus.maxvector.service

import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.DistanceType
import pl.qus.maxvector.model.EmbeddingRecord

interface IDatabaseService {
    fun findAll(): List<EmbeddingRecord>
    fun upsertAll(embs: List<EmbeddingRecord>): Boolean
    fun insert(emb: EmbeddingRecord): Boolean
    fun deleteById(id: Long): Boolean
    fun findClosest(ev: PostgresVector, k: Int, t: DistanceType): List<EmbeddingRecord>
}