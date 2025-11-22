package TiendaZa.backend.controller;

import TiendaZa.backend.model.Publicacion;
import TiendaZa.backend.service.PublicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
@CrossOrigin(origins = "*") // para permitir peticiones desde Android
public class PublicacionController {

    private final PublicacionService service;

    public PublicacionController(PublicacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Publicacion> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<Publicacion> search(@RequestParam(required = false) String query) {
        return service.search(query);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publicacion> getById(@PathVariable Long id) {
        Publicacion p = service.getById(id);
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    @PostMapping
    public ResponseEntity<Publicacion> create(@RequestBody Publicacion publicacion) {
        Publicacion created = service.create(publicacion);
        return ResponseEntity.ok(created);
    }
}
