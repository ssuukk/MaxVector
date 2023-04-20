package pl.qus.maxvector.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.qus.maxvector.hibernate.customtypes.EVector
import pl.qus.maxvector.model.Embedding
import pl.qus.maxvector.repository.EmbeddingRepository


///////////////////////////////////////////////////////////////////////////
// SERWIS - źródło danych do wyświetlania np. w kontrolerze
///////////////////////////////////////////////////////////////////////////

@Service
class EmbeddingService : IEmbeddingService {
    @Autowired
    lateinit var repository: EmbeddingRepository
    override fun findAll(): List<Embedding> {
        return repository.findAll() as List<Embedding>
    }
    override fun findClosest(v: EVector): List<Embedding> {
        return repository.findClosestEuclid(v.toString(), 4).toMutableList()
    }
}
