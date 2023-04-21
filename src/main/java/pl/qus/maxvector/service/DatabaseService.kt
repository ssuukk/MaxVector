package pl.qus.maxvector.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.qus.maxvector.dao.PostgresVectorDAO
import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.EmbeddingRecord


///////////////////////////////////////////////////////////////////////////
// SERWIS - źródło danych np. dla kontrolera
///////////////////////////////////////////////////////////////////////////

@Service
class DatabaseService : IDatabaseService {
    @Autowired
    lateinit var postgresDAO: PostgresVectorDAO

    override fun findAll(): List<EmbeddingRecord> {
        return postgresDAO.findAll()
    }
    override fun findClosestEuclidean(ev: PostgresVector, k: Int): List<EmbeddingRecord> {
        return postgresDAO.selectClosestEuclid(ev, k).toMutableList()
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
