package TiendaZa.backend.controller;

import TiendaZa.backend.model.Publicacion;
import TiendaZa.backend.service.PublicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
@CrossOrigin(origins = "*")
public class PublicacionController {

    private final PublicacionService service;

    public PublicacionController(PublicacionService service) {
        this.service = service;
    }

    // 1) LISTAR TODAS
    @GetMapping
    public List<Publicacion> getAll() {
        return service.getAll();
    }

    // 2) OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Publicacion> getById(@PathVariable Long id) {
        Publicacion pub = service.getById(id);
        if (pub == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pub);
    }

    // 3) BUSCAR POR TITULO (query ?q=texto)
    @GetMapping("/search")
    public List<Publicacion> search(@RequestParam(name = "query", required = false) String query) {
        return service.search(query);
    }

    // 4) CREAR
    @PostMapping
    public ResponseEntity<Publicacion> create(@RequestBody Publicacion publicacion) {
        Publicacion creada = service.create(publicacion);
        return ResponseEntity.ok(creada);
    }

    // 5) ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> update(
            @PathVariable Long id,
            @RequestBody Publicacion data) {

        Publicacion actualizada = service.update(id, data);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    // 6) ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = service.delete(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/con-imagen",
            consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Publicacion> createWithImage(
            @RequestPart("image") org.springframework.web.multipart.MultipartFile image,
            @RequestPart("titulo") String titulo,
            @RequestPart("descripcion") String descripcion,
            @RequestPart("precio") Integer precio
    ) {
        String urlImg = image.getOriginalFilename();

        Publicacion nueva = new Publicacion(
                null,
                titulo,
                descripcion,
                precio,
                urlImg
        );

        Publicacion creada = service.create(nueva);
        return ResponseEntity.ok(creada);
    }

}


