package pl.qus.maxvector.service

import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.DAOEmbedding

interface IEmbeddingService {
    fun findAll(): List<DAOEmbedding>
    fun findClosest(ev: PostgresVector, k: Int): List<DAOEmbedding>
    abstract fun upsertAll(embs: List<DAOEmbedding>): Boolean
    fun insert(emb: DAOEmbedding): Boolean
}