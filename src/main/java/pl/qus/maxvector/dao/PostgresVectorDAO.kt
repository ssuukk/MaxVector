package pl.qus.maxvector.dao

import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.EmbeddingRecord

interface PostgresVectorDAO {

    fun deleteVectorById(id: Long): Boolean
    fun upsert(emb: EmbeddingRecord): Boolean
    fun insert(emb: EmbeddingRecord): Boolean
    fun upsertAll(emb: List<EmbeddingRecord>): Boolean
    fun selectClosestEuclid(vec: PostgresVector, kval: Int): List<EmbeddingRecord>
    fun selectClosestCosine(vec: PostgresVector, kval: Int): List<EmbeddingRecord>
    fun selectClosestInnerProduct(vec: PostgresVector, kval: Int): List<EmbeddingRecord>
    fun findAll(): List<EmbeddingRecord>
}