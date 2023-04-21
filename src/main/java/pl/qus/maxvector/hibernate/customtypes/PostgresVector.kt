package pl.qus.maxvector.hibernate.customtypes

class PostgresVector(var points: MutableList<Float> = mutableListOf()) {
    override fun toString(): String = points.joinToString(",","[","]")

    companion object {
        @JvmStatic
        fun from(value: String): PostgresVector =
            PostgresVector(value.substring(1, value.length - 1).split(",").map { it.toFloat() }.toMutableList())
    }
}
