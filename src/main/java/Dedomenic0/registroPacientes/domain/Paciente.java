package Dedomenic0.registroPacientes.domain;

import Dedomenic0.registroPacientes.dto.PacienteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    private String nome;
    private String revisor;
    private String achados;
    private LocalDate deletado;

    public void deletarPaciente() {
        this.deletado = LocalDate.now();
    }

    public void atualizarPaciente(PacienteDto pacienteDto) {
        this.nome = pacienteDto.nome();
        this.revisor = pacienteDto.revisor();
        this.achados = pacienteDto.achados();
    }
}
