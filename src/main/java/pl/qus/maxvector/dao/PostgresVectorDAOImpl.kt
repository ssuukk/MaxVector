package pl.qus.maxvector.dao

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.model.DAOEmbedding
import javax.persistence.EntityManager

@Component
class PostgresVectorDAOImpl @Autowired constructor(val dataSource: EntityManager) : PostgresVectorDAO {
    //var jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)

    private val logger = LoggerFactory.getLogger(PostgresVectorDAOImpl::class.java)

    // CAST(:vec AS vector)

    private val SQL_INSERT_VECTORS = "INSERT INTO items (embedding, label) VALUES (CAST(:emb AS vector),:label)"
    private val SQL_UPSERT_VECTORS = "INSERT INTO items (id, embedding, label) VALUES ? ON CONFLICT (id) DO UPDATE SET embedding = EXCLUDED.embedding"
    private val SQL_DELETE_BY_ID = "DELETE FROM items WHERE id = :id"
    private val SQL_NEAREST_EUCLID = "SELECT * FROM items ORDER BY embedding <-> CAST(:emb AS vector) LIMIT :kval"
    private val SQL_QUERY_ALL = "select * FROM items"
    override fun deleteVectorById(id: Long): Boolean {
        val test = dataSource.createNativeQuery(SQL_DELETE_BY_ID).setParameter("id", id).toString()
        logger.debug("======================================== $test")
        return dataSource.createNativeQuery(SQL_DELETE_BY_ID).setParameter("id", id).resultList.size > 0
    }

    override fun insert(emb: DAOEmbedding): Boolean {
        return dataSource.createNativeQuery(SQL_INSERT_VECTORS)
            .setParameter("emb", emb.embedding.toString())
            .setParameter("label", emb.label)
            .resultList.size != 1
    }

    override fun upsert(emb: DAOEmbedding): Boolean {
        return false
//        return jdbcTemplate.update(
//            SQL_INSERT_VECTORS, emb.toString()
//        ) > 0
    }
    override fun upsertAll(emb: List<DAOEmbedding>): Boolean {
        TODO("Not yet implemented")
    }

    override fun selectClosestEuclid(vec: PostgresVector, kval: Int): MutableList<DAOEmbedding> {
        return dataSource.createNativeQuery(SQL_NEAREST_EUCLID, DAOEmbedding::class.java)
            .setParameter("emb", vec.toString())
            .setParameter("kval", kval)
            .resultList as MutableList<DAOEmbedding>
    }

    override fun findAll(): List<DAOEmbedding> {
        return dataSource.createNativeQuery(SQL_QUERY_ALL, DAOEmbedding::class.java).resultList as MutableList<DAOEmbedding>
    }

}