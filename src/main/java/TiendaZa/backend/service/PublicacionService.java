package TiendaZa.backend.service;

import TiendaZa.backend.model.Publicacion;
import TiendaZa.backend.repository.PublicacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PublicacionService {

    private final PublicacionRepository repository;

    public PublicacionService(PublicacionRepository repository) {
        this.repository = repository;
    }

    public List<Publicacion> getAll() {
        return repository.findAll();
    }

    public List<Publicacion> search(String query) {
        if (query == null || query.isBlank()) {
            return repository.findAll();
        }
        return repository.findByTituloStartingWithIgnoreCase(query);
    }

    public Publicacion create(Publicacion publicacion) {
        return repository.save(publicacion);
    }

    public Publicacion getById(Long id) {
        return repository.findById(id).orElse(null);
    }
}

