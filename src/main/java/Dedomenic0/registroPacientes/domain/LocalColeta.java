package Dedomenic0.registroPacientes.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "locais_coleta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalColeta {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String local;
}
