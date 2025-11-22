package TiendaZa.backend.service;

import TiendaZa.backend.model.Publicacion;
import TiendaZa.backend.repository.PublicacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicacionService {

    private final PublicacionRepository repository;

    public PublicacionService(PublicacionRepository repository) {
        this.repository = repository;
    }

    // LISTAR TODAS
    public List<Publicacion> getAll() {
        return repository.findAll();
    }

    // OBTENER POR ID
    public Publicacion getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // BUSCAR POR TITULO (que empiece con)
    public List<Publicacion> search(String query) {
        if (query == null || query.isBlank()) {
            return repository.findAll();
        }
        return repository.findByTituloStartingWithIgnoreCase(query);
    }

    // CREAR
    public Publicacion create(Publicacion publicacion) {
        publicacion.setId(null); // que lo genere la BD
        return repository.save(publicacion);
    }

    // ACTUALIZAR
    public Publicacion update(Long id, Publicacion data) {
        Optional<Publicacion> opt = repository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        Publicacion existing = opt.get();
        existing.setTitulo(data.getTitulo());
        existing.setDescripcion(data.getDescripcion());
        existing.setPrecio(data.getPrecio());
        existing.setUrlImg(data.getUrlImg());
        return repository.save(existing);
    }

    // ELIMINAR
    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
