package com.echo.echoback.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "historial_reproduccion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistorialReproduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long id;

    private Date fechaReproduccion;

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
