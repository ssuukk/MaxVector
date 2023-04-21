package pl.qus.maxvector.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.DAOEmbedding
import pl.qus.maxvector.repository.EmbeddingRepository


///////////////////////////////////////////////////////////////////////////
// SERWIS - źródło danych np. dla kontrolera
///////////////////////////////////////////////////////////////////////////

@Service
class EmbeddingService : IEmbeddingService {
    @Autowired
    lateinit var repository: EmbeddingRepository
    override fun findAll(): List<DAOEmbedding> {
        return repository.findAll() as List<DAOEmbedding>
    }
    override fun findClosest(ev: PostgresVector, k: Int): List<DAOEmbedding> {
        return repository.findClosestEuclid(ev.toString(), k).toMutableList()
    }
}
