package pl.qus.maxvector.service

import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.EmbeddingRecord

interface IDatabaseService {
    fun findAll(): List<EmbeddingRecord>
    fun findClosest(ev: PostgresVector, k: Int): List<EmbeddingRecord>
    abstract fun upsertAll(embs: List<EmbeddingRecord>): Boolean
    fun insert(emb: EmbeddingRecord): Boolean
}