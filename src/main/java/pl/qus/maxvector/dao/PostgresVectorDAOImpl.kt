package pl.qus.maxvector.dao

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.qus.maxvector.model.EmbVector
import pl.qus.maxvector.model.DistanceType
import pl.qus.maxvector.model.EmbeddingRecord
import pl.qus.maxvector.model.VectorMetadata
import java.awt.Dimension
import java.lang.IllegalStateException
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Component
class PostgresVectorDAOImpl @Autowired constructor(val dataSource: EntityManager) : PostgresVectorDAO {
    var dimensions = 2048
    var tableName = "items"

    private val logger = LoggerFactory.getLogger(PostgresVectorDAOImpl::class.java)

    private val SQL_INSERT_VECTORS = "INSERT INTO items (embedding, label) VALUES (CAST(:emb AS vector),:label)"
    private val SQL_UPSERT_VECTORS = "INSERT INTO items (id, embedding, label) VALUES (:id,CAST(:emb AS vector),:label) ON CONFLICT (id) DO UPDATE SET embedding = EXCLUDED.embedding"
    private val SQL_DELETE_BY_ID = "DELETE FROM items WHERE id = :id"

    private val SQL_NEAREST_EUCLID = "SELECT * FROM items ORDER BY embedding <-> CAST(:emb AS vector) LIMIT :kval"
    private val SQL_NEAREST_INNER = "SELECT * FROM items ORDER BY embedding <#> CAST(:emb AS vector) LIMIT :kval"
    private val SQL_NEAREST_COSINE = "SELECT * FROM items ORDER BY embedding <=> CAST(:emb AS vector) LIMIT :kval"
    //private val SQL_NEAREST_COSINE = "SELECT * FROM items ORDER BY embedding <=> :emb LIMIT :kval"
    private val SQL_DISTANCE_EUCLID = "SELECT -1 * (embedding <-> CAST(:emb AS vector)) AS inner_product FROM items"
    private val SQL_DISTANCE_INNER = "SELECT -1 * (embedding <#> CAST(:emb AS vector)) AS inner_product FROM items"
    private val SQL_DISTANCE_COSINE = "SELECT 1 - (embedding <=> CAST(:emb AS vector)) AS cosine_similarity FROM items"
    private val SQL_QUERY_ALL = "select * FROM items"

    private val SQL_DROP_TABLE = "DROP TABLE IF EXISTS items"
    private val SQL_CREATE_TABLE = "CREATE TABLE items (id bigserial PRIMARY KEY, embedding vector(2048), label text)"
    private val SQL_LOAD_EXTENSION = " CREATE EXTENSION vector"

    fun ensureDimensionality(emb: EmbVector) {
        if(emb.dimension != dimensions)
            throw IllegalStateException("Atempt to use ${emb.dimension}-dimension vector with $dimensions-dimension db!")
    }

    ///////////////////////////////////////////////////////////////////////////
    // Transactional update methods

    @Transactional
    fun recreateTable(name: String, dim: Int) {
        tableName = name
        dimensions = dim
        dataSource.createNativeQuery(SQL_DROP_TABLE).executeUpdate()
        dataSource.createNativeQuery(SQL_CREATE_TABLE).executeUpdate()
        dataSource.createNativeQuery(SQL_LOAD_EXTENSION).executeUpdate()
    }

    fun init(name: String, dim: Int) {
        tableName = name
        dimensions = dim
        dataSource.createNativeQuery(SQL_LOAD_EXTENSION).executeUpdate()
    }

    @Transactional
    override fun insert(emb: EmbeddingRecord): Boolean {
        ensureDimensionality(emb.embedding)
        return dataSource.createNativeQuery(SQL_INSERT_VECTORS)
            .setParameter("emb", emb.embedding.toString())
            .setParameter("label", emb.label)
            .executeUpdate() > 0
    }

    @Transactional
    override fun upsert(emb: EmbeddingRecord): Boolean {
        ensureDimensionality(emb.embedding)
        return dataSource.createNativeQuery(SQL_UPSERT_VECTORS)
            .setParameter("id", emb.id)
            .setParameter("emb", emb.embedding.toString())
            .setParameter("label", emb.label)
            .executeUpdate() > 0
    }

    @Transactional
    override fun upsertAll(embs: List<EmbeddingRecord>): Boolean {
        var i = 0
        embs.forEach { emb ->
            dataSource.createNativeQuery(SQL_UPSERT_VECTORS)
                .setParameter("id", emb.id)
                .setParameter("emb", emb.embedding.toString())
                .setParameter("label", emb.label)
                .executeUpdate()
            i++
        }
        return i == embs.size
    }

    @Transactional
    override fun deleteVectorById(id: Long): Boolean {
//        val test = dataSource.createNativeQuery(SQL_DELETE_BY_ID).setParameter("id", id).toString()
//        logger.debug("======================================== $test")
        return dataSource.createNativeQuery(SQL_DELETE_BY_ID).setParameter("id", id).executeUpdate() > 0
    }


    ///////////////////////////////////////////////////////////////////////////
    // Queries

    override fun selectClosest(vec: EmbVector, kval: Int, measure: DistanceType): List<EmbeddingRecord> {
        return when(measure) {
            DistanceType.EUCLIDEAN -> {
                dataSource.createNativeQuery(SQL_NEAREST_EUCLID, EmbeddingRecord::class.java)
                    .setParameter("emb", vec.toString())
                    .setParameter("kval", kval)
                    .resultList as MutableList<EmbeddingRecord>
            }
            DistanceType.COSINE -> {
                dataSource.createNativeQuery(SQL_NEAREST_COSINE, EmbeddingRecord::class.java)
                    .setParameter("emb", vec)
                    .setParameter("kval", kval)
                    .resultList as MutableList<EmbeddingRecord>
            }
            DistanceType.INNER_PRODUCT -> {
                dataSource.createNativeQuery(SQL_NEAREST_INNER, EmbeddingRecord::class.java)
                    .setParameter("emb", vec.toString())
                    .setParameter("kval", kval)
                    .resultList as MutableList<EmbeddingRecord>
            }
        }
    }

    override fun findAll(): List<EmbeddingRecord> {
        return dataSource.createNativeQuery(SQL_QUERY_ALL, EmbeddingRecord::class.java).resultList as MutableList<EmbeddingRecord>
    }

    override fun getMetadataById(id: Long): VectorMetadata {
        return VectorMetadata("example metadata for id=$id")
    }
}