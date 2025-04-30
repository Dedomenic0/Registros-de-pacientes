package Dedomenic0.registroPacientes.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "amostras_hemostasia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class AmostraHemostasia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoAmostra;
    private LocalDate data;
    private String localColeta;

    @Enumerated(EnumType.STRING)
    private Motivo motivo;

    public AmostraHemostasia(AmostraHemostasia amostra) {
        this.data = LocalDate.now();
        this.codigoAmostra = amostra.codigoAmostra;
        this.localColeta = amostra.localColeta;
        this.motivo = amostra.motivo;
    }
}
