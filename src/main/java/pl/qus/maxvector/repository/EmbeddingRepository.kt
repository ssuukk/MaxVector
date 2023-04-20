package pl.qus.maxvector.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.qus.maxvector.model.Embedding

///////////////////////////////////////////////////////////////////////////
// REPOSITORY - gada z bazą danych
///////////////////////////////////////////////////////////////////////////

@Repository
interface EmbeddingRepository : CrudRepository<Embedding?, Long?> {
    /*
    To nam załatwia dostęp do bazy danych z różnymi metodami:
    findAll, findById, delete, count etc.

    Ponadto możemy dodać swoje:
     */

    @Query(value = "SELECT * FROM items ORDER BY embedding <-> CAST(:vec AS vector) LIMIT :pval", nativeQuery = true)
    fun findClosestEuclid(@Param("vec") v: String?, @Param("pval") p: Int): Iterable<Embedding>

    @Query(value = "SELECT * FROM items ORDER BY embedding <#> CAST(:vec AS vector) LIMIT :pval", nativeQuery = true)
    fun findClosestInnerProd(@Param("vec") v: String?, @Param("pval") p: Int): Iterable<Embedding>

    @Query(value = "SELECT * FROM items ORDER BY embedding <=> CAST(:vec AS vector) LIMIT :pval", nativeQuery = true)
    fun findClosestCosine(
        @Param("vec") v: String?,
        @Param("pval") p: Int
    ): Iterable<Embedding>

// upsert: INSERT INTO items (id, embedding) VALUES (1, '[1,2,3]'), (2, '[4,5,6]')
    //    ON CONFLICT (id) DO UPDATE SET embedding = EXCLUDED.embedding;
    // pobranie w odległości:
    // SELECT * FROM items WHERE embedding <-> '[3,1,2]' < 5;
    //    Get the distance
    //
    //    SELECT embedding <-> '[3,1,2]' AS distance FROM items;
    //    For inner product, multiply by -1 (since <#> returns the negative inner product)
    //
    //    SELECT -1 * (embedding <#> '[3,1,2]') AS inner_product FROM items;
    //    For cosine similarity, use 1 - cosine distance
    //
    //    SELECT 1 - (embedding <=> '[3,1,2]') AS cosine_similarity FROM items;
}