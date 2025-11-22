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

    // Constructor vacío (OBLIGATORIO para JPA)
    public Publicacion() {
    }

    // Constructor con parámetros sin id (útil para crear nuevas)
    public Publicacion(String titulo, String descripcion, Integer precio, String urlImg) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.urlImg = urlImg;
    }

    // Constructor completo con id (por si lo necesitas)
    public Publicacion(Long id, String titulo, String descripcion, Integer precio, String urlImg) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.urlImg = urlImg;
    }

    // ========= GETTERS Y SETTERS =========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
