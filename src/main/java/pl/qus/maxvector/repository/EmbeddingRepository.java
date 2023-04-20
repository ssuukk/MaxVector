package pl.qus.maxvector.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.qus.maxvector.hibernate.customtypes.EVector;
import pl.qus.maxvector.model.Embedding;


/*
To nam załatwia dostęp do bazy danych City, z różnymi metodami:
findAll, findById, delete, count etc.

W CityService injectujemy je przez
    @Autowired
    private CityRepository repository;

 */
@Repository
public interface EmbeddingRepository extends CrudRepository<Embedding, Long> {

    @Query(value = "SELECT * FROM items ORDER BY embedding <-> CAST(:vec AS vector) LIMIT :pval", nativeQuery = true)
    Iterable<Embedding> findClosest(@Param("vec")String v, @Param("pval") int p);

    @Query(value = "SELECT * FROM items ORDER BY embedding <-> '[3,1,2]'::vector LIMIT (?1)", nativeQuery = true)
    Iterable<Embedding> findClosest(int p);

}