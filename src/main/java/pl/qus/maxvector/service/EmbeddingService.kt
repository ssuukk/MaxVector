package pl.qus.maxvector.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.qus.maxvector.dao.PostgresVectorDAO
import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.DAOEmbedding


///////////////////////////////////////////////////////////////////////////
// SERWIS - źródło danych np. dla kontrolera
///////////////////////////////////////////////////////////////////////////

@Service
class EmbeddingService : IEmbeddingService {
    @Autowired
    lateinit var postgresDAO: PostgresVectorDAO

    override fun findAll(): List<DAOEmbedding> {
        return postgresDAO.findAll()
    }
    override fun findClosest(ev: PostgresVector, k: Int): List<DAOEmbedding> {
        return postgresDAO.selectClosestEuclid(ev, k).toMutableList()
    }

    override fun upsertAll(embs: List<DAOEmbedding>): Boolean {
        return postgresDAO.upsertAll(embs)
    }

    override fun insert(emb: DAOEmbedding): Boolean {
        return postgresDAO.insert(emb)
    }
}
