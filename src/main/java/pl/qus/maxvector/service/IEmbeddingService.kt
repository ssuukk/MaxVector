package pl.qus.maxvector.service

import pl.qus.maxvector.hibernate.customtypes.EVector
import pl.qus.maxvector.model.Embedding

interface IEmbeddingService {
    fun findAll(): List<Embedding>
    fun findClosest(ev: EVector): List<Embedding>
}