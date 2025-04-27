package Dedomenic0.registroPacientes.domain;

import Dedomenic0.registroPacientes.dto.AmostraHematoDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "amostras_hemato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class AmostraHemato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoAmostra;
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private Motivo motivo;

    private String localColeta;

    public AmostraHemato(AmostraHemato amostra) {
        this.data = LocalDate.now();
        this.codigoAmostra = amostra.codigoAmostra;
        this.localColeta = amostra.localColeta;
        this.motivo = amostra.motivo;
    }
}
