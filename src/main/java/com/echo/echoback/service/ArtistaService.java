package com.echo.echoback.service;


import com.echo.echoback.domain.Artista;
import com.echo.echoback.domain.Usuario;
import com.echo.echoback.repository.ArtistaRepository;
import com.echo.echoback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registrar un artista
    public Artista registrarArtista(Artista artista) {
        // Verificar si el usuario asociado existe
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(artista.getUsuario().getId());
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        return artistaRepository.save(artista);
    }

    // Obtener un artista por ID
    public Artista obtenerArtistaPorId(Long id) {
        return artistaRepository.findById(id).orElseThrow(() -> new RuntimeException("Artista no encontrado"));
    }

    // Obtener todos los artistas
    public List<Artista> obtenerTodosLosArtistas() {
        return artistaRepository.findAll();
    }

    // Eliminar un artista por ID
    public void eliminarArtista(Long id) {
        artistaRepository.deleteById(id);
    }
}
