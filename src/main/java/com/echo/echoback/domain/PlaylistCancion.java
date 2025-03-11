package com.echo.echoback.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "playlist_cancion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistCancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_playlist_cancion")
    private Long id;

    private int orden;

    @ManyToOne
    @JoinColumn(name = "id_playlist")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "id_cancion")
    private Cancion cancion;
}
