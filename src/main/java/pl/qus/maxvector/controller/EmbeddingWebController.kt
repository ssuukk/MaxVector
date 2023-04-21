package pl.qus.maxvector.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import pl.qus.maxvector.service.IEmbeddingService

///////////////////////////////////////////////////////////////////////////
// KONTROLER - ten wyświetla strony WWW
///////////////////////////////////////////////////////////////////////////

@Controller
class EmbeddingWebController {
    @Autowired
    lateinit var embeddingService: IEmbeddingService
    @GetMapping("/emb") // w jakiej ścieżce ukaze się stona
    fun findEmbeddings(model: Model): String {
        model.addAttribute("embeddings", embeddingService.findAll()) // atrybut na stronie html
        return "showEmbedding" // to wkazuje nazwę pliku templeju, w którym należy to osadzić
    }

    @GetMapping("/closest") // w jakiej ścieżce ukaze się stona
    fun findClosest(model: Model): String {
        model.addAttribute(
            "embeddings", // atrybut na stronie html
            embeddingService.findClosest(PostgresVector(mutableListOf(5.0, 6.0, 7.0)),5)
        )
        return "showEmbedding" // to wkazuje nazwę pliku templejtu, w którym należy to osadzić
    }
}