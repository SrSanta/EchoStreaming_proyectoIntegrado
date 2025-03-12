package com.echo.echoback.service;

import com.echo.echoback.domain.Genero;
import com.echo.echoback.domain.Cancion;
import com.echo.echoback.repository.GeneroRepository;
import com.echo.echoback.repository.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private CancionRepository cancionRepository;

    // Agregar un género
    public Genero agregarGenero(Genero genero) {
        return generoRepository.save(genero);
    }

    // Obtener todos los géneros
    public List<Genero> obtenerTodosLosGeneros() {
        return generoRepository.findAll();
    }

    // Asignar géneros a una canción
    public void asignarGenerosACancion(Long cancionId, List<Long> generoIds) {
        Cancion cancion = cancionRepository.findById(cancionId)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada"));

        List<Genero> generos = generoRepository.findAllById(generoIds);
        cancion.setGeneros(generos);
        cancionRepository.save(cancion);
    }

    // Eliminar un género por ID
    public void eliminarGenero(Long id) {
        generoRepository.deleteById(id);
    }
}
