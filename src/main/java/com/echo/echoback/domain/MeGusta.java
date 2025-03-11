package com.echo.echoback.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "megusta")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeGusta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_megusta")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_cancion", nullable = true)
    private Cancion cancion;

    @ManyToOne
    @JoinColumn(name = "id_video", nullable = true)
    private Video video;
}
