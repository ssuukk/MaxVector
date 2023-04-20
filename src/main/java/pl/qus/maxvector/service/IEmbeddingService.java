package pl.qus.maxvector.service;


import pl.qus.maxvector.hibernate.customtypes.EVector;
import pl.qus.maxvector.model.City;
import pl.qus.maxvector.model.Embedding;

import java.util.List;

public interface IEmbeddingService {
    List<Embedding> findAll();
    List<Embedding> findClosest();
}