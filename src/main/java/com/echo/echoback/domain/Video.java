package com.echo.echoback.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "video")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "titulo")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_video")

    private Long id;

    private String titulo;
    private String archivoVideo;

    @OneToOne
    @JoinColumn(name = "id_cancion")
    private Cancion cancion;
}
