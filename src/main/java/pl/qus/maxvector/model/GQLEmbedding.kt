package pl.qus.maxvector.model

data class GQLEmbedding(
    val id: Long,
    val embedding: List<Double>
)

{
    companion object {
        fun from(d: EmbeddingRecord): GQLEmbedding {
            return GQLEmbedding(d.id, d.embedding.points)
        }
    }
}