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

    override fun findClosest(): List<Embedding> {
        return repository!!.findClosest(EVector(mutableListOf(4.0, 5.0, 6.0)).toString(), 4).toMutableList()
        //return repository!!.findClosest(4).toMutableList()
    }
}

/*
package pl.qus.maxvector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.qus.maxvector.hibernate.customtypes.EVector;
import pl.qus.maxvector.model.City;
import pl.qus.maxvector.model.Embedding;
import pl.qus.maxvector.repository.CityRepository;
import pl.qus.maxvector.repository.EmbeddingRepository;

import java.util.List;

/*
CityService injectujemy w kontrolerze przez:

    @Autowired
    private ICityService cityService;

 */
@Service
public class EmbeddingService implements IEmbeddingService {
    @Autowired
    private EmbeddingRepository repository;

    @Override
    public List<Embedding> findAll() {
        var emb = (List<Embedding>) repository.findAll();
        return emb;
    }

    public Iterable<Embedding> findClosest() {
        var emb = repository.findClosest(new EVector(), 4);
        return emb;
    }
}
 */