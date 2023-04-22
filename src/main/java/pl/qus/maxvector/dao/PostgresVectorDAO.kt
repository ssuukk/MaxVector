package pl.qus.maxvector.dao

import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.DistanceType
import pl.qus.maxvector.model.EmbeddingRecord
import pl.qus.maxvector.model.VectorMetadata

interface PostgresVectorDAO {

    fun deleteVectorById(id: Long): Boolean
    fun upsert(emb: EmbeddingRecord): Boolean
    fun insert(emb: EmbeddingRecord): Boolean
    fun upsertAll(emb: List<EmbeddingRecord>): Boolean
    fun selectClosest(vec: PostgresVector, kval: Int, measure: DistanceType): List<EmbeddingRecord>
    fun findAll(): List<EmbeddingRecord>
    fun getMetadataById(id: Long): VectorMetadata
}