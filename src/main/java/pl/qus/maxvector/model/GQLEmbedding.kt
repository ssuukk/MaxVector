package pl.qus.maxvector.model

data class GQLEmbedding(
    val id: Long,
    val embedding: List<Float>
)

{
    companion object {
        fun from(d: DAOEmbedding): GQLEmbedding {
            return GQLEmbedding(d.id, d.embedding.points)
        }
    }
}