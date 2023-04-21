package pl.qus.maxvector.dao

import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.DAOEmbedding

interface PostgresVectorDAO {

    fun deleteVectorById(id: Long): Boolean
    fun upsert(emb: DAOEmbedding): Boolean
    fun insert(emb: DAOEmbedding): Boolean
    fun upsertAll(emb: List<DAOEmbedding>): Boolean
    fun selectClosestEuclid(vec: PostgresVector, kval: Int): List<DAOEmbedding>
    fun findAll(): List<DAOEmbedding>
}