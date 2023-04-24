package pl.qus.maxvector.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.qus.maxvector.dao.PostgresVectorDAO
import pl.qus.maxvector.model.EmbVector
import pl.qus.maxvector.model.DistanceType
import pl.qus.maxvector.model.EmbeddingRecord
import pl.qus.maxvector.model.VectorMetadata


///////////////////////////////////////////////////////////////////////////
// SERWIS - źródło danych np. dla kontrolera
///////////////////////////////////////////////////////////////////////////

@Service
class PostgresVectorDatabaseService : IVectorDatabaseService {
    @Autowired
    lateinit var postgresDAO: PostgresVectorDAO

    override fun findAll(): List<EmbeddingRecord> {
        return postgresDAO.findAll()
    }

    override fun findClosest(ev: EmbVector, k: Int, t: DistanceType): List<EmbeddingRecord> {
        return postgresDAO.selectClosest(ev, k, t)
    }

    override fun getMetadataById(id: Long): VectorMetadata {
        return postgresDAO.getMetadataById(id)
    }

    override fun upsertAll(embs: List<EmbeddingRecord>): Boolean {
        return postgresDAO.upsertAll(embs)
    }

    override fun insert(emb: EmbeddingRecord): Boolean {
        return postgresDAO.insert(emb)
    }

    override fun deleteById(id: Long): Boolean {
        return postgresDAO.deleteById(id)
    }

    override fun updateById(id: Long, emb: EmbVector, lab: String): Boolean {
        return postgresDAO.updateById(id, emb, lab)
    }

    override fun getDistance(embToFind: EmbVector, t: DistanceType):List<Double> {
        return postgresDAO.getDistance(embToFind, t)
    }
}
