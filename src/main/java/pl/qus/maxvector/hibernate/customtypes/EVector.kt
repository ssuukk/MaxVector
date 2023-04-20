package pl.qus.maxvector.hibernate.customtypes

class EVector(var points: MutableList<Double> = mutableListOf()) {
    override fun toString(): String = points.joinToString(",","[","]")

    companion object {
        @JvmStatic
        fun from(value: String): EVector {
            val noBrackets = value.substring(1, value.length - 1)
            val newVector = EVector()
            newVector.points = noBrackets.split(",").map { it.toDouble() }.toMutableList()
            return newVector
        }
    }
}
