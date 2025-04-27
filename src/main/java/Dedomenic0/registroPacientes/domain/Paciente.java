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
    private String diagnosticoFinal;
    private String imagem;

    public void deletarPaciente() {
        this.deletado = LocalDate.now();
    }

    public void reverterDelecao() {
        this.deletado = null;
    }

    public void atualizarPaciente(PacienteDto pacienteDto) {
        this.data = pacienteDto.data();
        this.nome = pacienteDto.nome();
        this.revisor = pacienteDto.revisor();
        this.achados = pacienteDto.achados();
        this.diagnosticoFinal = pacienteDto.diagnosticoFinal();
    }

    public Paciente(PacienteDto paciente) {
        this.data = paciente.data();
        this.nome = paciente.nome();
        this.revisor = paciente.revisor();
        this.achados = paciente.achados();
        this.diagnosticoFinal = paciente.diagnosticoFinal();
    }
    public void salvarImagem(String imagemCaminho) {
        this.imagem = imagemCaminho;
    }
}
