package pl.qus.maxvector.dao

import pl.qus.maxvector.model.EmbVector
import pl.qus.maxvector.model.DistanceType
import pl.qus.maxvector.model.EmbeddingRecord
import pl.qus.maxvector.model.VectorMetadata

interface PostgresVectorDAO {

    fun deleteById(id: Long): Boolean
    fun upsert(emb: EmbeddingRecord): Boolean
    fun insert(emb: EmbeddingRecord): Boolean
    fun upsertAll(emb: List<EmbeddingRecord>): Boolean
    fun selectClosest(vec: EmbVector, kval: Int, measure: DistanceType): List<EmbeddingRecord>
    fun findAll(): List<EmbeddingRecord>
    fun getMetadataById(id: Long): VectorMetadata
    fun updateById(id: Long, emb: EmbVector, lab: String): Boolean
}