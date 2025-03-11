package com.echo.echoback.service;

import com.echo.echoback.domain.Album;
import com.echo.echoback.domain.Artista;
import com.echo.echoback.repository.AlbumRepository;
import com.echo.echoback.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    // Registrar un álbum
    public Album registrarAlbum(Album album) {
        // Verificar si el artista existe
        Optional<Artista> artistaOptional = artistaRepository.findById(album.getArtista().getId());
        if (artistaOptional.isEmpty()) {
            throw new RuntimeException("El artista no existe");
        }

        return albumRepository.save(album);
    }

    // Obtener un álbum por ID
    public Album obtenerAlbumPorId(Long id) {
        return albumRepository.findById(id).orElseThrow(() -> new RuntimeException("Álbum no encontrado"));
    }

    // Obtener todos los álbumes
    public List<Album> obtenerTodosLosAlbumes() {
        return albumRepository.findAll();
    }

    // Eliminar un álbum por ID
    public void eliminarAlbum(Long id) {
        albumRepository.deleteById(id);
    }
}
