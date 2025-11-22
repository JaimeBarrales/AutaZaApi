package TiendaZa.backend.model;
import jakarta.persistence.*;

@Entity
@Table(name = "publicaciones")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 2000)
    private String descripcion;

    private Integer precio;

    private String urlImg;

    public Publicacion() {}

    public Publicacion(String titulo, String descripcion, Integer precio, String urlImg) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.urlImg = urlImg;
    }

    // Getters y setters
}
