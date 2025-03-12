package com.echo.echoback.service;

import com.echo.echoback.domain.Cancion;
import com.echo.echoback.domain.Artista;
import com.echo.echoback.repository.CancionRepository;
import com.echo.echoback.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CancionService {
    private static final String UPLOAD_DIR = "src/main/resources/uploads/"; // Carpeta donde se guardarán los MP3

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    // Guardar una canción con archivo MP3
    public Cancion guardarCancion(String titulo, Long artistaId, MultipartFile archivoMp3) throws IOException {
        // Verificar si el artista existe
        Optional<Artista> artistaOptional = artistaRepository.findById(artistaId);
        if (artistaOptional.isEmpty()) {
            throw new RuntimeException("El artista no existe");
        }

        // Guardar archivo MP3 en el servidor
        String fileName = System.currentTimeMillis() + "_" + archivoMp3.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(archivoMp3.getInputStream(), filePath);

        // Guardar información en la base de datos
        Cancion nuevaCancion = new Cancion();
        nuevaCancion.setTitulo(titulo);
        nuevaCancion.setArtista(artistaOptional.get());
        nuevaCancion.setArchivoMp3(fileName);
        return cancionRepository.save(nuevaCancion);
    }

    // Obtener una canción por ID
    public Cancion obtenerCancionPorId(Long id) {
        return cancionRepository.findById(id).orElseThrow(() -> new RuntimeException("Canción no encontrada"));
    }

    // Obtener todas las canciones
    public List<Cancion> obtenerTodasLasCanciones() {
        return cancionRepository.findAll();
    }

    // Eliminar una canción por ID
    public void eliminarCancion(Long id) {
        cancionRepository.deleteById(id);
    }
}
