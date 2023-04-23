package pl.qus.maxvector.model

import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import pl.qus.maxvector.hibernate.customtypes.PostgresVectorCol2Type
import javax.persistence.*

///////////////////////////////////////////////////////////////////////////
// MODEL - to jest po prostu model danych w bazie, potrafi sam zrobić tabele, jakby co
///////////////////////////////////////////////////////////////////////////


@TypeDef(
    name = "vector",
    defaultForType = EmbVector::class,
    typeClass = PostgresVectorCol2Type::class
)
@Entity
@Table(name = "items")
class EmbeddingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    @Type(type = "vector")
    lateinit var embedding: EmbVector

    @Column
    lateinit var label: String

//    @Type(type = "jsonb")
//    @Column(columnDefinition = "jsonb")
//    lateinit var metadata: Any
}
