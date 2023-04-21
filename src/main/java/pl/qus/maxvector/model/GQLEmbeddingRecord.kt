package pl.qus.maxvector.model

data class GQLEmbeddingRecord(
    val id: Long,
    val embedding: List<Double>,
    val label: String
)

{
    companion object {
        fun from(d: EmbeddingRecord): GQLEmbeddingRecord {
            return GQLEmbeddingRecord(d.id, d.embedding.points, d.label)
        }
    }
}