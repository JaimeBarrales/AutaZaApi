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

    // 4) CREAR CON IMAGEN
    @PostMapping(
            value = "/con-imagen",
            consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> createWithImage(
            @RequestParam("image") org.springframework.web.multipart.MultipartFile image,
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") String precioStr
    ) {
        if (image == null || image.isEmpty()) {
            return ResponseEntity.badRequest().body("Falta la imagen");
        }
        if (titulo == null || titulo.isBlank()) {
            return ResponseEntity.badRequest().body("Falta el título");
        }
        if (descripcion == null || descripcion.isBlank()) {
            return ResponseEntity.badRequest().body("Falta la descripción");
        }
        if (precioStr == null || precioStr.isBlank()) {
            return ResponseEntity.badRequest().body("Falta el precio");
        }

        Integer precio;
        try {
            precio = Integer.valueOf(precioStr.trim());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Precio inválido");
        }

        // ⭐ CONVERTIR IMAGEN A BASE64
        String urlImg;
        try {
            byte[] bytes = image.getBytes();
            String base64 = java.util.Base64.getEncoder().encodeToString(bytes);
            urlImg = "data:image/jpeg;base64," + base64;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar imagen");
        }

        Publicacion nueva = new Publicacion(null, titulo, descripcion, precio, urlImg);
        Publicacion creada = service.create(nueva);
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
}