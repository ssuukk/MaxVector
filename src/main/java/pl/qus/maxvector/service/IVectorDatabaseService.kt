package pl.qus.maxvector.service

import pl.qus.maxvector.model.EmbVector
import pl.qus.maxvector.model.DistanceType
import pl.qus.maxvector.model.EmbeddingRecord
import pl.qus.maxvector.model.VectorMetadata

interface IVectorDatabaseService {
    fun findAll(): List<EmbeddingRecord>
    fun upsertAll(embs: List<EmbeddingRecord>): Boolean
    fun insert(emb: EmbeddingRecord): Boolean
    fun deleteById(id: Long): Boolean
    fun findClosest(ev: EmbVector, k: Int, t: DistanceType): List<EmbeddingRecord>
    fun getMetadataById(id: Long): VectorMetadata
    fun updateById(id: Long, emb: EmbVector, lab: String): Boolean
    fun getDistance(embToFind: EmbVector, euclidean: DistanceType): List<Double>
}