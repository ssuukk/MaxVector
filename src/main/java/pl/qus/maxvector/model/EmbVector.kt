package pl.qus.maxvector.model

class EmbVector(var coords: List<Double> = listOf()) {
    override fun toString(): String = coords.joinToString(",","[","]")

    operator fun get(i: Int):Double {
        return coords[i]
    }

//    operator fun set(i: Int, d: Double) {
//        points[i]=d
//    }

    val dimension: Int
        get() = coords.size
    
    companion object {
        @JvmStatic
        fun from(value: String): EmbVector =
            EmbVector(value.substring(1, value.length - 1).split(",").map { it.toDouble() })
    }
}
