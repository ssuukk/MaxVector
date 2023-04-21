package pl.qus.maxvector.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.DAOEmbedding
import pl.qus.maxvector.repository.EmbeddingRepository


///////////////////////////////////////////////////////////////////////////
// SERWIS - źródło danych do wyświetlania np. w kontrolerze
///////////////////////////////////////////////////////////////////////////

@Service
class EmbeddingService : IEmbeddingService {
    @Autowired
    lateinit var repository: EmbeddingRepository
    override fun findAll(): List<DAOEmbedding> {
        return repository.findAll() as List<DAOEmbedding>
    }
    override fun findClosest(v: PostgresVector): List<DAOEmbedding> {
        return repository.findClosestEuclid(v.toString(), 4).toMutableList()
    }
}
