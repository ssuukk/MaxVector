package pl.qus.maxvector.hibernate.customtypes

class EVector(var points: MutableList<Float> = mutableListOf()) {
    override fun toString(): String = points.joinToString(",","[","]")

    companion object {
        @JvmStatic
        fun from(value: String): EVector =
            EVector(value.substring(1, value.length - 1).split(",").map { it.toFloat() }.toMutableList())
    }
}
