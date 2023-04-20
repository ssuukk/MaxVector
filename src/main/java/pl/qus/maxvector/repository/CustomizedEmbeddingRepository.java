//package pl.qus.maxvector.repository;
//
//import org.springframework.data.jpa.repository.Query;
//import pl.qus.maxvector.hibernate.customtypes.EVector;
//import pl.qus.maxvector.model.Embedding;
//
//public interface CustomizedEmbeddingRepository {
//    @Query("SELECT -1 * (embedding <#> '?1') AS inner_product FROM items;")
//    public Iterable<Embedding> findClosest(EVector v, int p);
//}
