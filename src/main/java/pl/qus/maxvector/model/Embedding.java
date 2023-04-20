package pl.qus.maxvector.model;


import org.hibernate.annotations.Type;
import pl.qus.maxvector.hibernate.customtypes.EVector;

import javax.persistence.*;


// to jest po prostu model danych w bazie, potrafi sam zrobić tabele, jakby co

@Entity
@Table(name = "items")public class Embedding {
    //CREATE TABLE items (id bigserial PRIMARY KEY, embedding vector(3));
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Type(type = "pl.qus.maxvector.hibernate.customtypes.EVectorDatatype")
    private EVector embedding;


    public Embedding() {
    }

    public Embedding(Long id, EVector embedding) {
        this.id = id;
        this.embedding = embedding;
    }

    public Long getId() {
        return id;
    }
    public void setId(Integer Id) { this.id = id;}

    public EVector getEmbedding() {
        return embedding;
    }

    public void setEmbedding(EVector v) {
        embedding = v;
    }
}
