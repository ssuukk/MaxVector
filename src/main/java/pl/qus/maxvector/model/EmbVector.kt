package pl.qus.maxvector.model

class EmbVector(var points: List<Double> = listOf()) {
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
        fun from(value: String): EmbVector =
            EmbVector(value.substring(1, value.length - 1).split(",").map { it.toDouble() })
    }
}
