package pl.qus.maxvector.hibernate.customtypes

class PostgresVector(var points: List<Double> = listOf()) {
    override fun toString(): String = points.joinToString(",","[","]")

    operator fun get(i: Int):Double {
        return points[i]
    }

//    operator fun set(i: Int, d: Double) {
//        points[i]=d
//    }

    val dimension: Int
        get() = points.size
    
    companion object {
        @JvmStatic
        fun from(value: String): PostgresVector =
            PostgresVector(value.substring(1, value.length - 1).split(",").map { it.toDouble() }.toMutableList())
    }
}
