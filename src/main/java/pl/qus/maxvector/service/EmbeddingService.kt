package pl.qus.maxvector.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.qus.maxvector.hibernate.customtypes.EVector
import pl.qus.maxvector.model.Embedding
import pl.qus.maxvector.repository.EmbeddingRepository

/*
CityService injectujemy w kontrolerze przez:

   @Autowired
   private ICityService cityService;

*/
@Service
class EmbeddingService : IEmbeddingService {
    @Autowired
    private val repository: EmbeddingRepository? = null
    override fun findAll(): List<Embedding> {
        return repository!!.findAll() as List<Embedding>
    }



    override fun findClosest(v: EVector): List<Embedding> {
        return repository!!.findClosestEuclid(EVector(mutableListOf(4.0f, 5.0f, 6.0f)).toString(), 4).toMutableList()
        //return repository!!.findClosest(4).toMutableList()
    }
}
