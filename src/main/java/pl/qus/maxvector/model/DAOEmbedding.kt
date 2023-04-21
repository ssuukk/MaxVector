package pl.qus.maxvector.model

import org.hibernate.annotations.Type
import pl.qus.maxvector.hibernate.customtypes.PostgresVector
import javax.persistence.*

///////////////////////////////////////////////////////////////////////////
// MODEL - to jest po prostu model danych w bazie, potrafi sam zrobić tabele, jakby co
///////////////////////////////////////////////////////////////////////////

@Entity
@Table(name = "items")
class DAOEmbedding {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    @Type(type = "pl.qus.maxvector.hibernate.customtypes.PostgresVectorDatatype")
    lateinit var embedding: PostgresVector

    @Column
    lateinit var label: String
}
