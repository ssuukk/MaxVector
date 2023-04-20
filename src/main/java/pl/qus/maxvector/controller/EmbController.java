package pl.qus.maxvector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.qus.maxvector.hibernate.customtypes.EVector;
import pl.qus.maxvector.model.Embedding;
import pl.qus.maxvector.service.IEmbeddingService;

import java.util.List;

@Controller
public class EmbController {

    @Autowired
    private IEmbeddingService embeddingService;

    @GetMapping("/emb")
    public String findEmbeddings(Model model) {

        var embeddings = (List<Embedding>) embeddingService.findAll();

        model.addAttribute("embeddings", embeddings); // atrybut na stronie html

        return "showEmbedding"; // to wkazuje nazwę pliku, w którym należy to osadzić
    }

    @GetMapping("/closest")
    public String findClosest(Model model) {

        var embeddings = (List<Embedding>) embeddingService.findClosest();

        model.addAttribute("embeddings", embeddings); // atrybut na stronie html

        return "showEmbedding"; // to wkazuje nazwę pliku, w którym należy to osadzić
    }



}