package com.echo.echoback.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cancion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "titulo")
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancion")
    private Long id;

    private String titulo;
    private int duracion; // en segundos
    private String archivoMp3;

    @ManyToOne
    @JoinColumn(name = "id_album")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "id_artista")
    private Artista artista;

    @OneToOne(mappedBy = "cancion", cascade = CascadeType.ALL)
    private Video video;

    @ManyToMany
    @JoinTable(
            name = "cancion_genero",
            joinColumns = @JoinColumn(name = "id_cancion"),
            inverseJoinColumns = @JoinColumn(name = "id_genero")
    )
    private List<Genero> generos = new ArrayList<>();
}
