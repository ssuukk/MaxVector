package pl.qus.maxvector.dao

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.qus.maxvector.model.DistanceType
import pl.qus.maxvector.model.EmbVector
import pl.qus.maxvector.model.EmbeddingRecord
import pl.qus.maxvector.model.VectorMetadata
import javax.annotation.PostConstruct
import javax.persistence.EntityManager
import javax.transaction.Transactional


@Component
class PostgresVectorDAOImpl @Autowired constructor(val dataSource: EntityManager) : PostgresVectorDAO {
    var dimensions = -1
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
    //private val SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS items (id bigserial PRIMARY KEY, embedding vector(2048), label text)"
    private val SQL_LOAD_EXTENSION = "CREATE EXTENSION vector"
    private val SQL_UPDATE_BY_ID = "UPDATE items SET embedding = CAST(:emb AS vector), label = :lab WHERE id = :id"

    private val SQL_CREATE_INDEX_EUCLID = "CREATE INDEX ON items USING ivfflat (embedding vector_l2_ops) WITH (lists = :lists)"
    private val SQL_CREATE_INDEX_INNER = "CREATE INDEX ON items USING ivfflat (embedding vector_ip_ops) WITH (lists = :lists)"
    private val SQL_CREATE_INDEX_COSINE = "CREATE INDEX ON items USING ivfflat (embedding vector_cosine_ops) WITH (lists = :lists)"

    @PostConstruct
    @Transactional
    fun init() {
        loadExtension()

        // check if table exists and try to obtain vector size from it
        try {
            val record = dataSource.createNativeQuery(
                SQL_QUERY_ALL,
                EmbeddingRecord::class.java
            ).resultList as MutableList<EmbeddingRecord>
            dimensions = record.firstOrNull()?.embedding?.dimension ?: -1
            logger.debug("Vector dimension set to $dimensions")
        } catch (ex: Exception) {
            logger.error("Problem checking for dimensionality:${ex.message}")
        }
    }

    fun ensureDimensionality(emb: EmbVector) {
        if(dimensions == -1) {
            // table wasn't initialized, gotta be recreated
            try {
                prepareTable("items", emb.dimension)
                logger.error("Table of $dimensions created")
            } catch (ex: Exception) {
                logger.error("Problem creating table:${ex.message}")

            }
        }
        else if(emb.dimension != dimensions)
            throw IllegalStateException("Atempt to use ${emb.dimension}-dimension vector with $dimensions-dimension db!")
    }

    @Transactional
    fun loadExtension() {
        try {
            val res = dataSource.createNativeQuery(SQL_LOAD_EXTENSION).executeUpdate()
            logger.debug("vector extension: $res")
        } catch (ex: Exception) {
            logger.debug("Couldn't load vector extension: ${ex.message}")
        }
    }

    @Transactional
    fun prepareTable(name: String, dim: Int) {
        tableName = name
        dimensions = dim
        dataSource.createNativeQuery("CREATE TABLE IF NOT EXISTS items (id bigserial PRIMARY KEY, embedding vector($dim), label text)").executeUpdate()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Transactional update methods

    // create index

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
    override fun deleteById(id: Long): Boolean {
//        val test = dataSource.createNativeQuery(SQL_DELETE_BY_ID).setParameter("id", id).toString()
//        logger.debug("======================================== $test")
        return dataSource.createNativeQuery(SQL_DELETE_BY_ID).setParameter("id", id).executeUpdate() > 0
    }

    // UPDATE items SET embedding = '[1,2,3]' WHERE id = 1
    @Transactional
    override fun updateById(id: Long, emb: EmbVector, lab: String): Boolean {
        return dataSource.createNativeQuery(SQL_UPDATE_BY_ID)
            .setParameter("id", id)
            .setParameter("emb", emb.toString())
            .setParameter("label", lab)
            .executeUpdate() > 0
    }

    override fun createIndex(lists: Int, measure: DistanceType): Boolean {
        return when(measure) {
            DistanceType.EUCLIDEAN -> {
                dataSource.createNativeQuery(SQL_CREATE_INDEX_EUCLID, EmbeddingRecord::class.java)
                    .setParameter("lists", lists)
                    .executeUpdate() > 0
            }
            DistanceType.COSINE -> {
                dataSource.createNativeQuery(SQL_CREATE_INDEX_COSINE, EmbeddingRecord::class.java)
                    .setParameter("lists", lists)
                    .executeUpdate() > 0
            }
            DistanceType.INNER_PRODUCT -> {
                dataSource.createNativeQuery(SQL_CREATE_INDEX_INNER, EmbeddingRecord::class.java)
                    .setParameter("lists", lists)
                    .executeUpdate() > 0
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Queries

    // getById

    override fun selectClosest(vec: EmbVector, kval: Int, measure: DistanceType): List<EmbeddingRecord> {
        ensureDimensionality(vec)
        return when(measure) {
            DistanceType.EUCLIDEAN -> {
                dataSource.createNativeQuery(SQL_NEAREST_EUCLID, EmbeddingRecord::class.java)
                    .setParameter("emb", vec.toString())
                    .setParameter("kval", kval)
                    .resultList as List<EmbeddingRecord>
            }
            DistanceType.COSINE -> {
                dataSource.createNativeQuery(SQL_NEAREST_COSINE, EmbeddingRecord::class.java)
                    .setParameter("emb", vec.toString())
                    .setParameter("kval", kval)
                    .resultList as List<EmbeddingRecord>
            }
            DistanceType.INNER_PRODUCT -> {
                dataSource.createNativeQuery(SQL_NEAREST_INNER, EmbeddingRecord::class.java)
                    .setParameter("emb", vec.toString())
                    .setParameter("kval", kval)
                    .resultList as List<EmbeddingRecord>
            }
        }
    }

    override fun getDistance(vec: EmbVector, measure: DistanceType): List<Double> {
        ensureDimensionality(vec)
        return when(measure) {
            DistanceType.EUCLIDEAN -> {
                dataSource.createNativeQuery(SQL_DISTANCE_EUCLID)
                    .setParameter("emb", vec.toString())
                    .resultList as List<Double>
            }
            DistanceType.COSINE -> {
                dataSource.createNativeQuery(SQL_DISTANCE_COSINE)
                    .setParameter("emb", vec.toString())
                    .resultList as List<Double>
            }
            DistanceType.INNER_PRODUCT -> {
                dataSource.createNativeQuery(SQL_DISTANCE_INNER)
                    .setParameter("emb", vec.toString())
                    .resultList as List<Double>
            }
        }
    }

    override fun findAll(): List<EmbeddingRecord> {
        return dataSource.createNativeQuery(SQL_QUERY_ALL, EmbeddingRecord::class.java).resultList as List<EmbeddingRecord>
    }

    override fun getMetadataById(id: Long): VectorMetadata {
        return VectorMetadata("example metadata for id=$id")
    }
}