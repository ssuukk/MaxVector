package pl.qus.maxvector.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.qus.maxvector.dao.PostgresVectorDAO
import pl.qus.maxvector.model.PostgresVector
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

    override fun findClosest(ev: PostgresVector, k: Int, t: DistanceType): List<EmbeddingRecord> {
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
        return postgresDAO.deleteVectorById(id)
    }
}
