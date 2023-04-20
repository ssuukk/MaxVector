package pl.qus.maxvector.service;

import java.util.List;

import pl.qus.maxvector.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.qus.maxvector.model.City;

/*
CityService injectujemy w kontrolerze przez:

    @Autowired
    private ICityService cityService;

 */
@Service
public class CityService implements ICityService {
    @Autowired
    private CityRepository repository;

    @Override
    public List<City> findAll() {

        var cities = (List<City>) repository.findAll();

        return cities;
    }
}