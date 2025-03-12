package com.echo.echoback.service;

import com.echo.echoback.domain.Playlist;
import com.echo.echoback.domain.Cancion;
import com.echo.echoback.domain.Usuario;
import com.echo.echoback.repository.PlaylistRepository;
import com.echo.echoback.repository.CancionRepository;
import com.echo.echoback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CancionRepository cancionRepository;

    // Crear una playlist
    public Playlist crearPlaylist(Playlist playlist) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(playlist.getUsuario().getId());
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }
        return playlistRepository.save(playlist);
    }

    // Obtener una playlist por ID
    public Playlist obtenerPlaylistPorId(Long id) {
        return playlistRepository.findById(id).orElseThrow(() -> new RuntimeException("Playlist no encontrada"));
    }

    // Obtener todas las playlists de un usuario
    public List<Playlist> obtenerPlaylistsDeUsuario(Long usuarioId) {
        return playlistRepository.findByUsuarioId(usuarioId);
    }

    // Agregar una canción a la playlist
    public void agregarCancionAPlaylist(Long playlistId, Long cancionId) {
        Playlist playlist = obtenerPlaylistPorId(playlistId);
        Cancion cancion = cancionRepository.findById(cancionId)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada"));

        if (playlist.getCanciones() == null) {
            playlist.setCanciones(new ArrayList<>()); // Evitar null en la lista
        }

        playlist.getCanciones().add(cancion);
        playlistRepository.save(playlist);
    }

    // Eliminar una playlist por ID
    @PreAuthorize("hasAuthority('ADMIN') or @playlistRepository.findById(#id).get().usuario.id == authentication.principal.id")
    public void eliminarPlaylist(Long id) {
        playlistRepository.deleteById(id);
    }
}
