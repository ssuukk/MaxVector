package pl.qus.maxvector.service

import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.DAOEmbedding

interface IEmbeddingService {
    fun findAll(): List<DAOEmbedding>
    fun findClosest(ev: PostgresVector): List<DAOEmbedding>
}