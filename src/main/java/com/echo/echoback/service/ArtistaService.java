package com.echo.echoback.service;


import com.echo.echoback.domain.Artista;
import com.echo.echoback.domain.Usuario;
import com.echo.echoback.impl.Rol;
import com.echo.echoback.repository.ArtistaRepository;
import com.echo.echoback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Verificar si el usuario es un artista
    private void verificarSiEsArtista(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ARTISTA) {
            throw new RuntimeException("Acceso denegado: No eres un artista");
        }
    }

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
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Artista> obtenerTodosLosArtistas() {
        return artistaRepository.findAll();
    }

    // Eliminar un artista por ID
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminarArtista(Long id) {
        artistaRepository.deleteById(id);
    }
}
