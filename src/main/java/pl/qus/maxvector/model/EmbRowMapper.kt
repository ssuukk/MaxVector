package pl.qus.maxvector.model

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

class EmbRowMapper : RowMapper<EmbeddingRecord> {
    @Throws(SQLException::class)
    override fun mapRow(resultSet: ResultSet, i: Int): EmbeddingRecord {
        val embedding = EmbeddingRecord()
        embedding.id = resultSet.getLong("id")
        embedding.embedding = EmbVector.from(resultSet.getString("embedding"))
        embedding.label = resultSet.getString("label")
        return embedding
    }
}