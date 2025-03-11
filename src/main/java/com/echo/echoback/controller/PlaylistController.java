package com.echo.echoback.controller;

import com.echo.echoback.domain.Playlist;
import com.echo.echoback.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins = "http://localhost:4200")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    // Crear una playlist
    @PostMapping("/create")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
        Playlist nuevaPlaylist = playlistService.crearPlaylist(playlist);
        return ResponseEntity.ok(nuevaPlaylist);
    }

    // Obtener una playlist por ID
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.obtenerPlaylistPorId(id));
    }

    // Listar todas las playlists de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Playlist>> getUserPlaylists(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(playlistService.obtenerPlaylistsDeUsuario(usuarioId));
    }

    // Agregar una canción a la playlist
    @PostMapping("/{playlistId}/add/{cancionId}")
    public ResponseEntity<String> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long cancionId) {
        playlistService.agregarCancionAPlaylist(playlistId, cancionId);
        return ResponseEntity.ok("Canción agregada correctamente a la playlist");
    }

    // Eliminar una playlist por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {
        playlistService.eliminarPlaylist(id);
        return ResponseEntity.ok("Playlist eliminada correctamente");
    }
}
