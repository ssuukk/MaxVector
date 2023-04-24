package pl.qus.maxvector.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import pl.qus.maxvector.model.DistanceType
import pl.qus.maxvector.service.IVectorDatabaseService
import pl.qus.maxvector.service.IEmbeddingService

///////////////////////////////////////////////////////////////////////////
// KONTROLER - ten wyświetla strony WWW
///////////////////////////////////////////////////////////////////////////

@Controller
class EmbeddingWebController {

    @Autowired
    lateinit var openAI: IEmbeddingService
    @Autowired
    lateinit var embeddingService: IVectorDatabaseService

    @GetMapping("/emb") // w jakiej ścieżce ukaze się stona
    fun findEmbeddings(model: Model): String {
        model.addAttribute("embeddings", embeddingService.findAll()) // atrybut na stronie html
        return "showEmbedding" // to wkazuje nazwę pliku templeju, w którym należy to osadzić
    }

    @GetMapping("/closest") // w jakiej ścieżce ukaze się stona
    suspend fun findClosest(model: Model): String {

        val embToFind = openAI.getEmbedding("house animal")

        val distance = embeddingService.getDistance(embToFind, DistanceType.EUCLIDEAN)

        model.addAttribute(
            "embeddings", // atrybut na stronie html
            embeddingService.findClosest(embToFind,5, DistanceType.EUCLIDEAN)
        )
        return "showEmbedding" // to wkazuje nazwę pliku templejtu, w którym należy to osadzić
    }
}