package pl.qus.maxvector

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.qus.maxvector.model.DistanceType
import pl.qus.maxvector.model.EmbVector
import pl.qus.maxvector.service.IEmbeddingService
import pl.qus.maxvector.service.IVectorDatabaseService

@SpringBootTest
internal class DemoApplicationTests {

    @Autowired
    lateinit var openAI: IEmbeddingService
    @Autowired
    lateinit var embeddingService: IVectorDatabaseService

    @Test
    fun testDistance() = runBlocking {
        val embToFind = openAI.getEmbedding("house animal")

        //val embToFind = EmbVector(listOf(1.3, 1.5, 1.7))

        val distance = embeddingService.getDistance(embToFind, DistanceType.EUCLIDEAN)
        println("Odległości:$distance")
    }

    @Test
    fun testIndex() {
        embeddingService.createIndex(200, DistanceType.COSINE)
    }
}
