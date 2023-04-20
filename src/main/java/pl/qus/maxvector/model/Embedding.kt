package pl.qus.maxvector.model

import org.hibernate.annotations.Type
import pl.qus.maxvector.hibernate.customtypes.EVector
import javax.persistence.*

///////////////////////////////////////////////////////////////////////////
// MODEL - to jest po prostu model danych w bazie, potrafi sam zrobić tabele, jakby co
///////////////////////////////////////////////////////////////////////////

@Entity
@Table(name = "items")
class Embedding {
    //CREATE TABLE items (id bigserial PRIMARY KEY, embedding vector(3));
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
        private set

    @Column
    @Type(type = "pl.qus.maxvector.hibernate.customtypes.EVectorDatatype")
    var embedding: EVector? = null

    constructor()
    constructor(id: Long?, embedding: EVector?) {
        this.id = id
        this.embedding = embedding
    }

    fun setId(Id: Int?) {
        id = id
    }
}
