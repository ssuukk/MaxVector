package pl.qus.maxvector.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.qus.maxvector.model.City;


/*
To nam załatwia dostęp do bazy danych City, z różnymi metodami:
findAll, findById, delete, count etc.

W CityService injectujemy je przez
    @Autowired
    private CityRepository repository;

 */
@Repository
public interface CityRepository extends CrudRepository<City, Long> {

}